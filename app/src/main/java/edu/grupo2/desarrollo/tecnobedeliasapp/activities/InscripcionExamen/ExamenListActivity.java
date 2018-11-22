package edu.grupo2.desarrollo.tecnobedeliasapp.activities.InscripcionExamen;

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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.grupo2.desarrollo.tecnobedeliasapp.ConfigSingletton;
import edu.grupo2.desarrollo.tecnobedeliasapp.R;
import edu.grupo2.desarrollo.tecnobedeliasapp.api.ApiServiceInterface;
import edu.grupo2.desarrollo.tecnobedeliasapp.modelos.Examen;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * An activity representing a list of Examenes. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link ExamenDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class ExamenListActivity extends AppCompatActivity {

    ExamenListActivity.SimpleItemRecyclerViewAdapter adaptador;
    ArrayList<Examen> listaexamen;
    private static final String ETIQUETA = "examenlist";

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_examen_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        if (findViewById(R.id.examen_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }


        View recyclerView = findViewById(R.id.examen_list);
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
        listaexamen = new ArrayList<>();

        String pedido=getIntent().getStringExtra("eleccion");
        if(pedido!=null){
            if (pedido.equals("inscribirExamen")){
                obtenerListaExamenes();
            }
            else{
                obtenerListaExamenesYaanotado();
            }
        }


        adaptador=new SimpleItemRecyclerViewAdapter(this, listaexamen, mTwoPane,getIntent().getStringExtra("eleccion"));
        recyclerView.setAdapter(adaptador);
        //con esto pongo la linea divisoria entre los items
        LinearLayoutManager lmgr=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(lmgr);
        recyclerView.addItemDecoration(new DividerItemDecoration(this,lmgr.getOrientation()));
    }

    public static class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final ExamenListActivity mParentActivity;
        private final List<Examen> mValues;
        private final boolean mTwoPane;
        private String eleccion;
        private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Examen item = (Examen) view.getTag();
                if (mTwoPane) {
                    Bundle arguments = new Bundle();
                    arguments.putSerializable(ExamenDetailFragment.ARG_ITEM_ID, item);
                    arguments.putString("eleccion",eleccion);
                    ExamenDetailFragment fragment = new ExamenDetailFragment();
                    fragment.setArguments(arguments);
                    mParentActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.examen_detail_container, fragment)
                            .commit();
                } else {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, ExamenDetailActivity.class);
                    intent.putExtra(ExamenDetailFragment.ARG_ITEM_ID, item);
                    intent.putExtra("eleccion",eleccion);

                    context.startActivity(intent);
                }
            }
        };

        SimpleItemRecyclerViewAdapter(ExamenListActivity parent,
                                      List<Examen> items,
                                      boolean twoPane,
                                      String elecc) {
            mValues = items;
            mParentActivity = parent;
            mTwoPane = twoPane;
            eleccion = elecc;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.examen_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mIdView.setText(mValues.get(position).nombreAsignatura);
            holder.mContentView.setText(formatofecha(mValues.get(position).fecha));

            holder.itemView.setTag(mValues.get(position));
            holder.itemView.setOnClickListener(mOnClickListener);
        }
        private  String formatofecha(String fsinformato){

            Date fechaformateada= new Date();
            //el que lee
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
            //el que le da el formato
            SimpleDateFormat formateador=new SimpleDateFormat("dd-MM-yyyy");
            String retorno="";

            try {
                //fechaformateada=sdf.parse(fsinformato);
                fechaformateada=sdf.parse(fsinformato);
                retorno= formateador.format(fechaformateada);

                Log.e(ETIQUETA,"fecha formateada: "+fechaformateada.toString());
                // Date hoy= new Date();
            } catch (ParseException e) {
                e.printStackTrace();
            }




            return retorno;//fechaformateada.toString();
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
    private void obtenerListaExamenes() {

        Retrofit retro = ConfigSingletton.getInstance().getRetro();
        //creo y llamo a la api

        ApiServiceInterface interfaz= retro.create(edu.grupo2.desarrollo.tecnobedeliasapp.api.ApiServiceInterface.class);

        String token=ConfigSingletton.getInstance().getTokenUsuarioLogueado();
        Log.e(ETIQUETA, "token del usuario logueado: "+token);

        Call<List<Examen>> respuestacall=interfaz.obtenerListaExamenDisponibles(token);
        //al ponerse enqueue se realiza asyncronicamente.
        respuestacall.enqueue(new Callback<List<Examen>>() {
            @Override
            public void onResponse(Call<List<Examen>> call, Response<List<Examen>> response) {

                if (response.isSuccessful()){
                    List<Examen> r=response.body();


                    listaexamen.addAll(r);
                    Log.e(ETIQUETA, "en respuesta lista examenes: " + r.size());
                    for(Examen e:listaexamen){
                        Log.e(ETIQUETA, "lista examen: nombreAsig: " + e.nombreAsignatura + " fecha: "+e.fecha+" hora: "+e.hora);
                    }
                    //ConfigSingletton.getInstance().setCarreras(listacarrera);
                    adaptador.notifyDataSetChanged();
                }
                else{

                    Log.e(ETIQUETA, "en respuesta lista examen no exitosa "+response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Examen>> call, Throwable t) {
                //Toast.makeText(conte.getApplicationContext(), "error de comunicacion", Toast.LENGTH_SHORT).show();
                // showProgress(false);
                Log.e(ETIQUETA, "en falla:" + t.getMessage());
            }
        });


    }

    private void obtenerListaExamenesYaanotado() {

        Retrofit retro = ConfigSingletton.getInstance().getRetro();
        //creo y llamo a la api

        ApiServiceInterface interfaz= retro.create(edu.grupo2.desarrollo.tecnobedeliasapp.api.ApiServiceInterface.class);

        String token=ConfigSingletton.getInstance().getTokenUsuarioLogueado();
        Log.e(ETIQUETA, "token del usuario logueado: "+token);

        Call<List<Examen>> respuestacall=interfaz.consultaExamenes(token);
        //al ponerse enqueue se realiza asyncronicamente.
        respuestacall.enqueue(new Callback<List<Examen>>() {
            @Override
            public void onResponse(Call<List<Examen>> call, Response<List<Examen>> response) {

                if (response.isSuccessful()){
                    List<Examen> r=response.body();


                    listaexamen.addAll(r);
                    Log.e(ETIQUETA, "en respuesta lista carrera: " + r.size());
                    for(Examen e:listaexamen){
                        Log.e(ETIQUETA, "lista examen: nombreAsig: " + e.nombreAsignatura + " fecha: "+e.fecha+" hora: "+e.hora);
                    }
                    //ConfigSingletton.getInstance().setCarreras(listacarrera);
                    adaptador.notifyDataSetChanged();
                }
                else{

                    Log.e(ETIQUETA, "en respuesta lista examen no exitosa "+response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Examen>> call, Throwable t) {
                //Toast.makeText(conte.getApplicationContext(), "error de comunicacion", Toast.LENGTH_SHORT).show();
                // showProgress(false);
                Log.e(ETIQUETA, "en falla:" + t.getMessage());
            }
        });


    }

}
