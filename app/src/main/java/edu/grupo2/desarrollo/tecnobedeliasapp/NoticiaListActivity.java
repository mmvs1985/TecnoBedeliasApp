package edu.grupo2.desarrollo.tecnobedeliasapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;

import edu.grupo2.desarrollo.tecnobedeliasapp.api.ApiServiceInterface;
import edu.grupo2.desarrollo.tecnobedeliasapp.modelos.Carrera;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.util.ArrayList;
import java.util.List;

/**
 * An activity representing a list of Noticias. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link NoticiaDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class NoticiaListActivity extends AppCompatActivity {
    SimpleItemRecyclerViewAdapter adaptador;
    ArrayList<Carrera> listacarrera;
    private static final String ETIQUETA = "noticialista";

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noticia_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());


        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        if (findViewById(R.id.noticia_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

        View recyclerView = findViewById(R.id.noticia_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. Use NavUtils to allow users
            // to navigate up one level in the application structure. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        listacarrera=new ArrayList<>();
        obtenerListaTodaCarrera();
        adaptador=new SimpleItemRecyclerViewAdapter(this, listacarrera, mTwoPane);
        recyclerView.setAdapter(adaptador);
    }

    public static class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final NoticiaListActivity mParentActivity;
        private final ArrayList<Carrera> mValues;
        private final boolean mTwoPane;
        private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Carrera item = (Carrera) view.getTag();
                if (mTwoPane) {
                    Bundle arguments = new Bundle();
                    //arguments.putString(NoticiaDetailFragment.ARG_ITEM_ID, item.getNombre());
                    arguments.putSerializable(NoticiaDetailFragment.ARG_ITEM_ID,item);
                    NoticiaDetailFragment fragment = new NoticiaDetailFragment();
                    fragment.setArguments(arguments);
                    mParentActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.noticia_detail_container, fragment)
                            .commit();
                } else {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, NoticiaDetailActivity.class);
                    //intent.putExtra(NoticiaDetailFragment.ARG_ITEM_ID, item.getNombre());
                    intent.putExtra(NoticiaDetailFragment.ARG_ITEM_ID,item);

                    context.startActivity(intent);
                }
            }
        };

        SimpleItemRecyclerViewAdapter(NoticiaListActivity parent,
                                      ArrayList<Carrera> items,
                                      boolean twoPane) {
            mValues = items;
            mParentActivity = parent;
            mTwoPane = twoPane;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.noticia_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mIdView.setText(mValues.get(position).getNombre());
            holder.mContentView.setText(String.valueOf(mValues.get(position).getCreditosMinimos()));

            holder.itemView.setTag(mValues.get(position));
            holder.itemView.setOnClickListener(mOnClickListener);
        }

        @Override
        public int getItemCount() {
            if( mValues!=null)
            return mValues.size();
            else
                return 0;
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            final TextView mIdView;
            final TextView mContentView;

            ViewHolder(View view) {
                super(view);
                mIdView = (TextView) view.findViewById(R.id.FechaNoticia);
                mContentView = (TextView) view.findViewById(R.id.content);
            }
        }
    }
    private void obtenerListaTodaCarrera() {
        // Log.e(ETIQUETA, "intento logear con usr y cpass: "+username+" "+pass );
        //final Context conte=cont;

        Retrofit retro = ConfigSingletton.getInstance().getRetro();

        ArrayList<Carrera> resultado;

        //creo y llamo a la api
        ApiServiceInterface interfaz= retro.create(edu.grupo2.desarrollo.tecnobedeliasapp.api.ApiServiceInterface.class);
        String token=ConfigSingletton.getInstance().getTokenUsuarioLogueado();
        Log.e(ETIQUETA, "token del usuario logueado: "+token);

        Call<List<Carrera>> respuestacall=interfaz.obtenerListacarrera(token);
        //al ponerse enqueue se realiza asyncronicamente.
        respuestacall.enqueue(new Callback<List<Carrera>>() {
            @Override
            public void onResponse(Call<List<Carrera>> call, Response<List<Carrera>> response) {

                if (response.isSuccessful()){
                    List<Carrera> r=response.body();


                    listacarrera.addAll(r);
                    Log.e(ETIQUETA, "en respuesta lista carrera: " + r.size());
                    for(Carrera c:listacarrera){
                        Log.e(ETIQUETA, "lista carrera: " + c.getNombre()+" desc: "+c.getDescripcion()+" creditos: "+c.getCreditosMinimos());
                    }
                    ConfigSingletton.getInstance().setCarreras(listacarrera);
                    adaptador.notifyDataSetChanged();
                }
                else{

                    Log.e(ETIQUETA, "en respuesta lista Carrera no exitosa ");
                }
            }

            @Override
            public void onFailure(Call<List<Carrera>> call, Throwable t) {
                //Toast.makeText(conte.getApplicationContext(), "error de comunicacion", Toast.LENGTH_SHORT).show();
                // showProgress(false);
                Log.e(ETIQUETA, "en falla:" + t.getMessage());
            }
        });

        // return listacarrera;

    }
}
