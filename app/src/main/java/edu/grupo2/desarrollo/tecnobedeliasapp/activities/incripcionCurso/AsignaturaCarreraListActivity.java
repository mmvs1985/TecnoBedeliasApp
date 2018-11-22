package edu.grupo2.desarrollo.tecnobedeliasapp.activities.incripcionCurso;

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
import edu.grupo2.desarrollo.tecnobedeliasapp.activities.listarCarreras.CarreraDetailFragment;
import edu.grupo2.desarrollo.tecnobedeliasapp.api.ApiServiceInterface;
import edu.grupo2.desarrollo.tecnobedeliasapp.modelos.Asignatura;
import edu.grupo2.desarrollo.tecnobedeliasapp.modelos.AsignaturaCarrera;
import edu.grupo2.desarrollo.tecnobedeliasapp.modelos.Carrera;
import edu.grupo2.desarrollo.tecnobedeliasapp.modelos.Curso;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * An activity representing a list of AsignaturasCarrera. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link AsignaturaCarreraDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class AsignaturaCarreraListActivity extends AppCompatActivity {


    AsignaturaCarreraListActivity.SimpleItemRecyclerViewAdapter adaptador;
    ArrayList<Asignatura> listaasignatura;
    private ArrayList<Curso> listacurso;
//    ArrayList<Examen> listaExamen;

    //Carrera carreraSeleccionada;
    String eleccion;
    private static final String ETIQUETA = "AsignaturaCarrera";

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asignaturacarrera_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Elija su carrera...");

        //carreraSeleccionada=(Carrera) getIntent().getSerializableExtra(CarreraDetailFragment.ARG_ITEM_ID);
        eleccion=getIntent().getStringExtra("eleccion");

        //Log.e(ETIQUETA,"Carrera seleccionada"+carreraSeleccionada.getNombre());


        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        if (findViewById(R.id.asignaturacarrera_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

        View recyclerView = findViewById(R.id.asignaturacarrera_list);
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
        listacurso=new ArrayList<>();
        //obtenerAsignaturasdeCarrera(carreraSeleccionada);
        String pedido=getIntent().getStringExtra("eleccion");
        if(pedido!=null){
            if (pedido.equals("inscribirCurso")){
                obtenerCursosDisponibles();
            }
            else{
                obtenerCursosDelUsuario();
            }
        }

        adaptador=new SimpleItemRecyclerViewAdapter(this, listacurso, mTwoPane,eleccion);
        recyclerView.setAdapter(adaptador);
        //con esto pongo la linea divisoria entre los items
        LinearLayoutManager lmgr=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(lmgr);
        recyclerView.addItemDecoration(new DividerItemDecoration(this,lmgr.getOrientation()));
    }



    public static class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final AsignaturaCarreraListActivity mParentActivity;
        private final ArrayList<Curso> mValues;
        private final boolean mTwoPane;
        private final String eleccion;
        private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Curso item = (Curso) view.getTag();
                Log.e(ETIQUETA,"DATOS DEl curso: "+item.getId()+item.getNombreAsignatura()+item.getFechaInicio());
                if (mTwoPane) {
                    Bundle arguments = new Bundle();
                    arguments.putSerializable(CarreraDetailFragment.ARG_ITEM_ID, item);
                    arguments.putString("eleccion",eleccion);
                    AsignaturaCarreraDetailFragment fragment = new AsignaturaCarreraDetailFragment();
                    fragment.setArguments(arguments);
                    mParentActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.asignaturacarrera_detail_container, fragment)
                            .commit();
                } else {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, AsignaturaCarreraDetailActivity.class);
                    intent.putExtra(CarreraDetailFragment.ARG_ITEM_ID, item);
                    intent.putExtra("eleccion",eleccion);


                    context.startActivity(intent);
                }
            }
        };

        SimpleItemRecyclerViewAdapter(AsignaturaCarreraListActivity parent,
                                      ArrayList<Curso> items,
                                      boolean twoPane,String elecc) {
            mValues = items;
            mParentActivity = parent;
            mTwoPane = twoPane;
            eleccion=elecc;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.asignaturacarrera_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {

            holder.mIdView.setText(formatofecha(mValues.get(position).getFechaInicio()));
            holder.mContentView.setText(mValues.get(position).getNombreAsignatura());


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
    }

    private void obtenerCursosDisponibles(){


        Retrofit retro = ConfigSingletton.getInstance().getRetro();
        //creo y llamo a la api

        ApiServiceInterface interfaz= retro.create(edu.grupo2.desarrollo.tecnobedeliasapp.api.ApiServiceInterface.class);

        String token=ConfigSingletton.getInstance().getTokenUsuarioLogueado();
        Log.e(ETIQUETA, "token del usuario logueado: "+token);

        Call<List<Curso>> respuestacall=interfaz.obtenerListaCursoDisponibles(token);
            //al ponerse enqueue se realiza asyncronicamente.
            respuestacall.enqueue(new Callback<List<Curso>>() {
                @Override
                public void onResponse(Call<List<Curso>> call, Response<List<Curso>> response) {

                    if (response.isSuccessful()){
                        List<Curso> r=response.body();


                        listacurso.addAll(r);
                        Log.e(ETIQUETA, "en respuesta lista Curso disponible: " + r.size());
                        for(Curso c:listacurso){
                            Log.e(ETIQUETA, "lista Curso: nombreAsig: " + c.getNombreAsignatura() + " fechaini: "+c.getFechaInicio()+" fechafin: "+c.getFechaFin());
                        }
                        //ConfigSingletton.getInstance().setCarreras(listacarrera);
                        adaptador.notifyDataSetChanged();
                    }
                    else{

                        Log.e(ETIQUETA, "en respuesta lista Curso disponible no exitosa "+response.body());
                    }
                }

                @Override
                public void onFailure(Call<List<Curso>> call, Throwable t) {
                    //Toast.makeText(conte.getApplicationContext(), "error de comunicacion", Toast.LENGTH_SHORT).show();
                    // showProgress(false);
                    Log.e(ETIQUETA, "en falla:" + t.getMessage());
                }
            });





    }

    private void obtenerCursosDelUsuario(){



        Retrofit retro = ConfigSingletton.getInstance().getRetro();
        //creo y llamo a la api

        ApiServiceInterface interfaz= retro.create(edu.grupo2.desarrollo.tecnobedeliasapp.api.ApiServiceInterface.class);

        String token=ConfigSingletton.getInstance().getTokenUsuarioLogueado();
        Log.e(ETIQUETA, "token del usuario logueado: "+token);

        Call<List<Curso>> respuestacall=interfaz.consultaCursos(token);
        //al ponerse enqueue se realiza asyncronicamente.
        respuestacall.enqueue(new Callback<List<Curso>>() {
            @Override
            public void onResponse(Call<List<Curso>> call, Response<List<Curso>> response) {

                if (response.isSuccessful()){
                    List<Curso> r=response.body();


                    listacurso.addAll(r);
                    Log.e(ETIQUETA, "en respuesta lista Curso del usuario: " + r.size());
                    for(Curso c:listacurso){
                        Log.e(ETIQUETA, "lista examen: nombreAsig: " + c.getNombreAsignatura() + " fechaini: "+c.getFechaInicio()+" fechafin: "+c.getFechaFin());
                    }
                    //ConfigSingletton.getInstance().setCarreras(listacarrera);
                    adaptador.notifyDataSetChanged();
                }
                else{

                    Log.e(ETIQUETA, "en respuesta lista Curso del usuario no exitosa "+response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Curso>> call, Throwable t) {
                //Toast.makeText(conte.getApplicationContext(), "error de comunicacion", Toast.LENGTH_SHORT).show();
                // showProgress(false);
                Log.e(ETIQUETA, "en falla:" + t.getMessage());
            }
        });





    }




    private void obtenerAsignaturasdeCarrera(Carrera carreraselecc) {

        ArrayList<AsignaturaCarrera> ac= (ArrayList<AsignaturaCarrera>) carreraselecc.getAsignaturaCarrera();
        for (AsignaturaCarrera a:ac
             ) {
            Log.e(ETIQUETA,"AGREGO LA ASIGNATURA: "+a.getAsignatura().getNombre());
            listaasignatura.add(a.getAsignatura());
        }

    }

}
