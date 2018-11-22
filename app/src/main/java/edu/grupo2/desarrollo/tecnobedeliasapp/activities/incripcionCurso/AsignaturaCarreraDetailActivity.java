package edu.grupo2.desarrollo.tecnobedeliasapp.activities.incripcionCurso;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import edu.grupo2.desarrollo.tecnobedeliasapp.ConfigSingletton;
import edu.grupo2.desarrollo.tecnobedeliasapp.R;
import edu.grupo2.desarrollo.tecnobedeliasapp.activities.listarCarreras.CarreraDetailFragment;
import edu.grupo2.desarrollo.tecnobedeliasapp.api.ApiServiceInterface;
import edu.grupo2.desarrollo.tecnobedeliasapp.modelos.Asignatura;
import edu.grupo2.desarrollo.tecnobedeliasapp.modelos.Curso;
import edu.grupo2.desarrollo.tecnobedeliasapp.modelos.RespuestaApiBooleana;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * An activity representing a single AsignaturaCarrera detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link AsignaturaCarreraListActivity}.
 */
public class AsignaturaCarreraDetailActivity extends AppCompatActivity {

    private static final String ETIQUETA ="asigdetActivity" ;
    //Asignatura asignaturaElegida;
    Curso cursoElegido;
    Button fab;
    private String eleccion;

    boolean retorno;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asignaturacarrera_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        fab = (Button) findViewById(R.id.fab);
        //fab.setText("");
        eleccion=getIntent().getStringExtra("eleccion");
        cursoElegido=(Curso) getIntent().getSerializableExtra(CarreraDetailFragment.ARG_ITEM_ID);
        Log.e(ETIQUETA,"EL EXTRA ES "+eleccion);
        /*if (getIntent().getStringExtra("eleccion").equals("curso")) {
            cursodis = cursoDisponible(asignaturaElegida);
            if (obtenerCursosDeAsignatura(asignaturaElegida))
                fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorAccent)));

            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (cursodis != null) {

                        if (intentoInscribiraCurso(String.valueOf(cursodis.getId()))) {
                            String topico = asignaturaElegida.getNombre().replace(" ", "_") + "-" + String.valueOf(cursodis.getSemestre() + "-" + String.valueOf(cursodis.getAnio()));
                            FirebaseMessaging.getInstance().subscribeToTopic(topico);
                            Log.d(ETIQUETA, "me inscribi al topico: " + topico);
                            retorno = false;
                            //Toast.makeText(getApplicationContext(),"inscripcion solicitada",Toast.LENGTH_LONG).show();
                        } else {
                            //Toast.makeText(getApplicationContext(),"inscripcion NO LOGRADA",Toast.LENGTH_LONG).show();
                        }


                    } else {
                        Toast.makeText(getApplicationContext(), "Esta materia aun  no tiene curso", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
        else{
            Toast.makeText(getApplicationContext(),"no se eligio curso",Toast.LENGTH_LONG).show();
        }*/

        if(eleccion!=null){
            if(eleccion.equals("inscribirCurso")){
                fab.setText("inscribirse");
                fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        intentoInscribiraCurso(cursoElegido);

                        fab.setText("");
                    }
                });
            }
            else{
                fab.setText("Desistir");

                fab = (Button) findViewById(R.id.fab);
                fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        intentoDesistiraCurso(cursoElegido);
                        fab.setText("");
                    }
                });

            }
        }

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
        }

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
           // Asignatura asignaturaelegida= (Asignatura) getIntent().getSerializableExtra(CarreraDetailFragment.ARG_ITEM_ID);
            

            Bundle arguments = new Bundle();
            arguments.putSerializable(CarreraDetailFragment.ARG_ITEM_ID,cursoElegido);
           // arguments.putSerializable("asignatura",asignaturaElegida);
            AsignaturaCarreraDetailFragment fragment = new AsignaturaCarreraDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.asignaturacarrera_detail_container, fragment)
                    .commit();
        }
    }

//    private Curso cursoDisponible(Asignatura asignaturaelegida) {
//
//        if(asignaturaelegida !=null) {
//            ArrayList<Curso> cursos = (ArrayList<Curso>) asignaturaelegida.getCursos();
//            Curso cursodisponible = null;
//            for (Curso c : cursos) {
//                Log.e(ETIQUETA, c.getFechaFin());
//                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//                try {
//                    Date fechafin = sdf.parse(c.getFechaFin());
//
//                    Log.e(ETIQUETA, "fecha formateada: " + fechafin.toString());
//                    Date hoy = new Date();
//                    Log.e(ETIQUETA, "la fecha formateada es despues de hoy? : " + fechafin.after(hoy));
//                    if (fechafin.after(hoy)) {
//                        cursodisponible = c;
//                    }
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
//
//
//                ///cursos. if (c.getFechaFin())
//            }
//            return cursodisponible;
//        }
//        return  null;
//
//    }

    private boolean intentoInscribiraCurso(final Curso curso) {

    //TODO: LLAMAR A LA API PARA QUE SE INSCRIBA AL CURSO
        Log.e(ETIQUETA,"intento insctibir con estos datos: "+ ConfigSingletton.getInstance().getTokenUsuarioLogueado()+" idcurso: "+curso.getId());
       /* ConexionManual conn=new ConexionManual();
        try {

            String resultado =conn.execute(ConfigSingletton.getInstance().getUrlbase()+"inscripcion/curso"+"?curso="+ URLEncoder.encode(idcurso, "UTF-8")).get();
            Log.e(ETIQUETA,"La respuesta es: "+ resultado);
            if (resultado.equals("true")){

                ConfigSingletton.getInstance().traerUsuarioLogueado(getApplicationContext());
                return  true;
            }
            else {
                return false;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        } catch (ExecutionException e) {
            e.printStackTrace();
            return false;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return false;
        }*/

        Retrofit retro = ConfigSingletton.getInstance().getRetro();

        retorno=false;

        //creo y llamo a la api
        ApiServiceInterface interfaz= retro.create(edu.grupo2.desarrollo.tecnobedeliasapp.api.ApiServiceInterface.class);
        String token=ConfigSingletton.getInstance().getTokenUsuarioLogueado();
        Log.e(ETIQUETA, "token del usuario logueado: "+token);

        Call<RespuestaApiBooleana> respuestacall=interfaz.inscribirseACurso(token,String.valueOf(curso.getId().toString()));
        //al ponerse enqueue se realiza asyncronicamente.
        respuestacall.enqueue(new Callback<RespuestaApiBooleana>() {
            @Override
            public void onResponse(Call<RespuestaApiBooleana> call, Response<RespuestaApiBooleana> response) {

                if (response.isSuccessful()) {
                    RespuestaApiBooleana r = response.body();

                    Toast.makeText(getApplicationContext(), r.getMensaje(), Toast.LENGTH_LONG).show();
                    retorno = r.getEstado();
                    if (retorno){
                        String topico=curso.getNombreAsignatura().replace(" ","_") +"-"+String.valueOf(curso.getSemestre()+"-"+String.valueOf(curso.getAnio()));
                        FirebaseMessaging.getInstance().subscribeToTopic(topico);
                        Log.d(ETIQUETA,"me inscribi al topico: "+topico);

                        Log.e(ETIQUETA, "en respuesta incribirse a curso: " + r.getEstado() + " MSg: " + r.getMensaje());
                    }
                }
                else{

                    Log.e(ETIQUETA, "en respuesta  incribirse a curso no exitosa ");
                }
            }

            @Override
            public void onFailure(Call<RespuestaApiBooleana> call, Throwable t) {
                //Toast.makeText(conte.getApplicationContext(), "error de comunicacion", Toast.LENGTH_SHORT).show();
                // showProgress(false);
                Log.e(ETIQUETA, "en falla:" + t.getMessage());
            }
        });

    return retorno;

    }

    private boolean intentoDesistiraCurso(final Curso curso) {

        //TODO: LLAMAR A LA API PARA QUE SE INSCRIBA AL CURSO
        Log.e(ETIQUETA,"intento desistir con estos datos: "+ ConfigSingletton.getInstance().getTokenUsuarioLogueado()+" idcurso: "+curso.getId());


        Retrofit retro = ConfigSingletton.getInstance().getRetro();

        retorno=false;

        //creo y llamo a la api
        ApiServiceInterface interfaz= retro.create(edu.grupo2.desarrollo.tecnobedeliasapp.api.ApiServiceInterface.class);
        String token=ConfigSingletton.getInstance().getTokenUsuarioLogueado();
        Log.e(ETIQUETA, "token del usuario logueado: "+token);

        Call<RespuestaApiBooleana> respuestacall=interfaz.desistirACurso(token,String.valueOf(curso.getId()));
        //al ponerse enqueue se realiza asyncronicamente.
        respuestacall.enqueue(new Callback<RespuestaApiBooleana>() {
            @Override
            public void onResponse(Call<RespuestaApiBooleana> call, Response<RespuestaApiBooleana> response) {

                if (response.isSuccessful()) {
                    RespuestaApiBooleana r = response.body();

                    Toast.makeText(getApplicationContext(), r.getMensaje(), Toast.LENGTH_LONG).show();
                    retorno = r.getEstado();
                    if (retorno){
                        String topico=curso.getNombreAsignatura().replace(" ","_") +"-"+String.valueOf(curso.getSemestre()+"-"+String.valueOf(curso.getAnio()));
                        FirebaseMessaging.getInstance().unsubscribeFromTopic(topico);
                        Log.d(ETIQUETA,"me DESinscribi al topico: "+topico);
                    }

                    Log.e(ETIQUETA, "en respuesta incribirse a curso: " + r.getEstado() + " MSg: " + r.getMensaje());
                }
                else{

                    Log.e(ETIQUETA, "en respuesta  incribirse a curso no exitosa ");
                }
            }

            @Override
            public void onFailure(Call<RespuestaApiBooleana> call, Throwable t) {
                //Toast.makeText(conte.getApplicationContext(), "error de comunicacion", Toast.LENGTH_SHORT).show();
                // showProgress(false);
                Log.e(ETIQUETA, "en falla:" + t.getMessage());
            }
        });

        return retorno;

    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            navigateUpTo(new Intent(this, AsignaturaCarreraListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private boolean obtenerCursosDeAsignatura(Asignatura mItem){
        if (mItem != null) {
            ArrayList<Curso> cursos= (ArrayList<Curso>) mItem.getCursos();
            if(cursos==null){
                return false;
            }
            Curso cursodisponible=null;
            for (Curso c:cursos) {
                Log.e(ETIQUETA, c.getFechaFin());
                SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
                try {
                    Date fechafin=sdf.parse(c.getFechaFin());

                    Log.e(ETIQUETA,"fecha formateada: "+fechafin.toString());
                    Date hoy= new Date();
                    Log.e(ETIQUETA,"la fecha formateada es despues de hoy? : "+fechafin.after(hoy));
                    if(fechafin.after(hoy)){
                        cursodisponible=c;
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }


                ///cursos. if (c.getFechaFin())
            }
            if(cursodisponible!=null){

                String texto="id: "+cursodisponible.getId()+"Año: "+cursodisponible.getAnio()+"\nSemestre: "+cursodisponible.getSemestre()+"\nInicia: "+cursodisponible.getFechaInicio()+"\nFinaliza: "+cursodisponible.getFechaFin();

                return true;
            }
            else
                return false;

        }
        else
            return false;
    }
}
