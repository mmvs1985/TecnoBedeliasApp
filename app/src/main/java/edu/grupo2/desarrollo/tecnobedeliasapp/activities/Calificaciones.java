package edu.grupo2.desarrollo.tecnobedeliasapp.activities;

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
import edu.grupo2.desarrollo.tecnobedeliasapp.modelos.Actividad;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class Calificaciones extends AppCompatActivity {

    Calificaciones.SimpleItemRecyclerViewAdapter adaptador;
    ArrayList<Actividad> listaActividades;
    private static final String ETIQUETA = "calificacioneslista";
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calificaciones);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        View recyclerView = findViewById(R.id.recycledCalificaciones);
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
        listaActividades = new ArrayList<>();
        obtenerListaNotas();
        adaptador = new SimpleItemRecyclerViewAdapter(this, listaActividades, mTwoPane);
        recyclerView.setAdapter(adaptador);

        //con esto pongo la linea divisoria entre los items
        LinearLayoutManager lmgr = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(lmgr);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, lmgr.getOrientation()));
    }

    public static class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final Calificaciones mParentActivity;
        private final ArrayList<Actividad> mValues;
        private final boolean mTwoPane;

        SimpleItemRecyclerViewAdapter(Calificaciones parent,
                                      ArrayList<Actividad> items,
                                      boolean twoPane) {
            mValues = items;
            mParentActivity = parent;
            mTwoPane = twoPane;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.calificaciones_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.mnota.setText(mValues.get(position).getNota().toString());
            holder.mnombreAsignatura.setText(mValues.get(position).getAsignatura());
            holder.mtipoActividad.setText(mValues.get(position).getTipo());
            holder.itemView.setTag(mValues.get(position));
        }


        @Override
        public int getItemCount() {
            return mValues.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            final TextView mtipoActividad;
            final TextView mnombreAsignatura;
            final TextView mnota;

            ViewHolder(View view) {
                super(view);
                mtipoActividad = (TextView) view.findViewById(R.id.tipoActividad);
                mnombreAsignatura = (TextView) view.findViewById(R.id.nombreAsignatura);
                mnota = (TextView) view.findViewById(R.id.notatv);
            }
        }

    }

    private void obtenerListaNotas() {

        Retrofit retro = ConfigSingletton.getInstance().getRetro();
        //creo y llamo a la api

        ApiServiceInterface interfaz= retro.create(edu.grupo2.desarrollo.tecnobedeliasapp.api.ApiServiceInterface.class);

        String token=ConfigSingletton.getInstance().getTokenUsuarioLogueado();
        Log.e(ETIQUETA, "token del usuario logueado: "+token);

        Call<List<Actividad>> respuestacall=interfaz.obtenerNotas(token, ConfigSingletton.getInstance().getUsuarioLogueado().getCedula());
        //al ponerse enqueue se realiza asyncronicamente.
        respuestacall.enqueue(new Callback<List<Actividad>>() {
            @Override
            public void onResponse(Call<List<Actividad>> call, Response<List<Actividad>> response) {

                if (response.isSuccessful()){
                    List<Actividad> r=response.body();

                    for(Actividad e:r){

                        if(!e.getNota().equals(-1)){

                            Log.e(ETIQUETA, "lista notas: nombreAsig: " +" Estado: "+e.getEstado()+" Asignatura: "
                                    + e.getAsignatura() + " nota: "+e.getNota()+" hora: "+e.getTipo());
                            listaActividades.add(e);


                        }
                        else {

                            Log.e(ETIQUETA, "nota no ingresada: nombreAsig: " +" Estado: "+e.getEstado()
                                    + e.getAsignatura() + " nota: "+e.getNota()+" hora: "+e.getTipo());


                        }

                    }



                    //listaActividades.addAll(r);
                    Log.e(ETIQUETA, "en respuesta lista notas: " + r.size());
                    /*for(Actividad e:listaActividades){
                        Log.e(ETIQUETA, "lista notas: nombreAsig: " +" Estado: "+e.getEstado()
                                + e.getAsignatura() + " nota: "+e.getNota()+" hora: "+e.getTipo());
                    }*/
                    //ConfigSingletton.getInstance().setCarreras(listacarrera);
                    adaptador.notifyDataSetChanged();
                }
                else{

                    Log.e(ETIQUETA, "en respuesta lista notas no exitosa "+response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Actividad>> call, Throwable t) {
                //Toast.makeText(conte.getApplicationContext(), "error de comunicacion", Toast.LENGTH_SHORT).show();
                // showProgress(false);
                Log.e(ETIQUETA, "en falla:" + t.getMessage());
            }
        });


    }
}
