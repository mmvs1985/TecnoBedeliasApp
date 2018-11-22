package edu.grupo2.desarrollo.tecnobedeliasapp.activities.listarAsignaturas;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import edu.grupo2.desarrollo.tecnobedeliasapp.ConfigSingletton;
import edu.grupo2.desarrollo.tecnobedeliasapp.R;
import edu.grupo2.desarrollo.tecnobedeliasapp.api.ApiServiceInterface;
import edu.grupo2.desarrollo.tecnobedeliasapp.modelos.Asignatura;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * An activity representing a list of Asignaturas. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link AsignaturaDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class AsignaturaListActivity extends AppCompatActivity {

    AsignaturaListActivity.SimpleItemRecyclerViewAdapter adaptador;
    ArrayList<Asignatura> listaasignaturas;
    private static final String ETIQUETA = "asignaturalista";
    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asignatura_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        if (findViewById(R.id.asignatura_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

        View recyclerView = findViewById(R.id.asignatura_list);
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
        listaasignaturas=new ArrayList<>();
        obtenerListaTodaAsignatura();
        adaptador=new SimpleItemRecyclerViewAdapter(this, listaasignaturas, mTwoPane);
        recyclerView.setAdapter(adaptador);

        //con esto pongo la linea divisoria entre los items
        LinearLayoutManager lmgr=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(lmgr);
        recyclerView.addItemDecoration(new DividerItemDecoration(this,lmgr.getOrientation()));
    }


    public static class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final AsignaturaListActivity mParentActivity;
        private final ArrayList<Asignatura> mValues;
        private final boolean mTwoPane;
        private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Asignatura item = (Asignatura) view.getTag();
                if (mTwoPane) {
                    Bundle arguments = new Bundle();
                    arguments.putSerializable(AsignaturaDetailFragment.ARG_ITEM_ID, item);
                    AsignaturaDetailFragment fragment = new AsignaturaDetailFragment();
                    fragment.setArguments(arguments);
                    mParentActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.asignatura_detail_container, fragment)
                            .commit();
                } else {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, AsignaturaDetailActivity.class);
                    intent.putExtra(AsignaturaDetailFragment.ARG_ITEM_ID, item);

                    context.startActivity(intent);
                }
            }
        };

        SimpleItemRecyclerViewAdapter(AsignaturaListActivity parent,
                                      ArrayList<Asignatura> items,
                                      boolean twoPane) {
            mValues = items;
            mParentActivity = parent;
            mTwoPane = twoPane;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.asignatura_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            //aca cargo la info en los camos
            holder.mIdView.setText(mValues.get(position).getId().toString());
            holder.mContentView.setText(mValues.get(position).getNombre());

            holder.itemView.setTag(mValues.get(position));
            holder.itemView.setOnClickListener(mOnClickListener);
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            final TextView mIdView;
            final TextView mContentView;

            ViewHolder(View view) {
                super(view);
                mIdView = (TextView) view.findViewById(R.id.id_text);
                mContentView = (TextView) view.findViewById(R.id.content);
            }
        }
    }

    private void obtenerListaTodaAsignatura() {

        Retrofit retro = ConfigSingletton.getInstance().getRetro();
        ApiServiceInterface interfaz= retro.create(edu.grupo2.desarrollo.tecnobedeliasapp.api.ApiServiceInterface.class);
        String token=ConfigSingletton.getInstance().getTokenUsuarioLogueado();
        Log.e(ETIQUETA, "token del usuario logueado: "+token);

        Call<List<Asignatura>> respuestacall=interfaz.obtenerListaAsignaturas(token);
        respuestacall.enqueue(new Callback<List<Asignatura>>() {
            @Override
            public void onResponse(Call<List<Asignatura>> call, Response<List<Asignatura>> response) {
                if(response.isSuccessful()){
                    listaasignaturas.addAll(response.body());
                    Log.e(ETIQUETA, "en respuesta lista carrera: " + response.body().size());
                    for (Asignatura a:listaasignaturas){
                        Log.e(ETIQUETA,"lista Asignatura"+a.getNombre());
                    }
                    ConfigSingletton.getInstance().setAsignaturas(listaasignaturas);
                    adaptador.notifyDataSetChanged();
                }
                else{
                    Log.e(ETIQUETA, "en respuesta lista Asignatura no exitosa ");
                }
            }

            @Override
            public void onFailure(Call<List<Asignatura>> call, Throwable t) {
                Log.e(ETIQUETA, "en falla:" + t.getMessage());

            }
        });
    }
}
