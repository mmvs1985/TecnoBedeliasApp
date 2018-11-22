package edu.grupo2.desarrollo.tecnobedeliasapp.activities.InscripcionExamen;

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

import edu.grupo2.desarrollo.tecnobedeliasapp.ConfigSingletton;
import edu.grupo2.desarrollo.tecnobedeliasapp.R;
import edu.grupo2.desarrollo.tecnobedeliasapp.api.ApiServiceInterface;
import edu.grupo2.desarrollo.tecnobedeliasapp.modelos.Examen;
import edu.grupo2.desarrollo.tecnobedeliasapp.modelos.RespuestaApiBooleana;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * An activity representing a single Examen detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link ExamenListActivity}.
 */
public class ExamenDetailActivity extends AppCompatActivity {

    private static final String ETIQUETA ="examen detail: " ;
    private Button fab;
    String nombreAsignaturaExamenElegido;
    private boolean respuesta;
    private boolean retorno;
    private Examen examenSeleccionado;
    private String eleccion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_examen_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);


        respuesta=false;
        fab = (Button) findViewById(R.id.fab);


        examenSeleccionado=((Examen) getIntent().getSerializableExtra(ExamenDetailFragment.ARG_ITEM_ID));
        eleccion=getIntent().getStringExtra("eleccion");
        nombreAsignaturaExamenElegido=examenSeleccionado.nombreAsignatura;
        if(eleccion!=null){
         if(eleccion.equals("inscribirExamen")){
             fab.setText("inscribirse");

             //fab = (Button) findViewById(R.id.fab);
             fab.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View view) {
                     intentoInscribiraExamen(examenSeleccionado);
                 }
             });
         }
         else{
             fab.setText("Desistir");

             fab = (Button) findViewById(R.id.fab);
             fab.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View view) {
                     intentoDesistiraExamen(examenSeleccionado);
                 }
             });

         }
        }

        //ConsultarExamenesUsuario(examenSeleccionado.id.toString());



        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
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
            Bundle arguments = new Bundle();
            arguments.putSerializable(ExamenDetailFragment.ARG_ITEM_ID,
                    getIntent().getSerializableExtra(ExamenDetailFragment.ARG_ITEM_ID));
            ExamenDetailFragment fragment = new ExamenDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.examen_detail_container, fragment)
                    .commit();
        }
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
            navigateUpTo(new Intent(this, ExamenListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private boolean intentoInscribiraExamen(final Examen examen) {

        //TODO: LLAMAR A LA API PARA QUE SE INSCRIBA AL CURSO
        Log.e(ETIQUETA,"intento insctibir con estos datos: "+ ConfigSingletton.getInstance().getTokenUsuarioLogueado()+" idExamen: "+examen.id);


        final Retrofit retro = ConfigSingletton.getInstance().getRetro();

        retorno=false;

        //creo y llamo a la api
        final ApiServiceInterface interfaz= retro.create(edu.grupo2.desarrollo.tecnobedeliasapp.api.ApiServiceInterface.class);
        String token=ConfigSingletton.getInstance().getTokenUsuarioLogueado();
        Log.e(ETIQUETA, "token del usuario logueado: "+token);

        Call<RespuestaApiBooleana> respuestacall=interfaz.inscribirseAExamen(token,examen.id.toString());
        //al ponerse enqueue se realiza asyncronicamente.
        respuestacall.enqueue(new Callback<RespuestaApiBooleana>() {
            @Override
            public void onResponse(Call<RespuestaApiBooleana> call, Response<RespuestaApiBooleana> response) {

                if (response.isSuccessful()) {
                    RespuestaApiBooleana r = response.body();

                    Toast.makeText(getApplicationContext(), r.getMensaje(), Toast.LENGTH_LONG).show();
                    retorno = r.getEstado();
                    if (retorno){
                        fab.setText("");

                        String topico = examen.getNombreAsignatura().replace(" ","_") +"-examen-"+examen.id;
                        FirebaseMessaging.getInstance().subscribeToTopic(topico);
                        Log.d(ETIQUETA,"me inscribi al topico: "+topico);
                        /*LoginActivity loginActivity=new LoginActivity();
                        loginActivity.traerUsuarioLogueado(retro,interfaz);*/
                        ConfigSingletton.getInstance().traerUsuarioLogueado(getApplicationContext());
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


    private boolean intentoDesistiraExamen(final Examen examen) {

        //TODO: LLAMAR A LA API PARA QUE SE INSCRIBA AL CURSO
        Log.e(ETIQUETA,"intento DESinsctibir con estos datos: "+ ConfigSingletton.getInstance().getTokenUsuarioLogueado()+" idExamen: "+examen.id);


        final Retrofit retro = ConfigSingletton.getInstance().getRetro();

        retorno=false;

        //creo y llamo a la api
        final ApiServiceInterface interfaz= retro.create(edu.grupo2.desarrollo.tecnobedeliasapp.api.ApiServiceInterface.class);
        String token=ConfigSingletton.getInstance().getTokenUsuarioLogueado();
        Log.e(ETIQUETA, "token del usuario logueado: "+token);

        Call<RespuestaApiBooleana> respuestacall=interfaz.desistirAExamen(token,examen.id.toString());
        //al ponerse enqueue se realiza asyncronicamente.
        respuestacall.enqueue(new Callback<RespuestaApiBooleana>() {
            @Override
            public void onResponse(Call<RespuestaApiBooleana> call, Response<RespuestaApiBooleana> response) {

                if (response.isSuccessful()) {
                    RespuestaApiBooleana r = response.body();

                    Toast.makeText(getApplicationContext(), r.getMensaje(), Toast.LENGTH_LONG).show();
                    retorno = r.getEstado();
                    if (retorno){
                        fab.setText("");
                        String topico = examen.getNombreAsignatura().replace(" ","_") +"-examen-"+examen.id;

                        FirebaseMessaging.getInstance().unsubscribeFromTopic(topico);
                        Log.d(ETIQUETA,"me DESinscribi al topico: "+topico);
                        /*LoginActivity loginActivity=new LoginActivity();
                        loginActivity.traerUsuarioLogueado(retro,interfaz);*/
                        ConfigSingletton.getInstance().traerUsuarioLogueado(getApplicationContext());
                    }

                    Log.e(ETIQUETA, "en respuesta DESincribirse a curso: " + r.getEstado() + " MSg: " + r.getMensaje());
                }
                else{

                    Log.e(ETIQUETA, "en respuesta  DESincribirse a curso no exitosa ");
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
}

