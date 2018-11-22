package edu.grupo2.desarrollo.tecnobedeliasapp.activities.listarCarreras;

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

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import edu.grupo2.desarrollo.tecnobedeliasapp.ConfigSingletton;
import edu.grupo2.desarrollo.tecnobedeliasapp.R;
import edu.grupo2.desarrollo.tecnobedeliasapp.api.ApiServiceInterface;
import edu.grupo2.desarrollo.tecnobedeliasapp.api.ConexionManual;
import edu.grupo2.desarrollo.tecnobedeliasapp.modelos.Carrera;
import edu.grupo2.desarrollo.tecnobedeliasapp.modelos.RespuestaApiBooleana;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * An activity representing a single Carrera detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link CarreraListActivity}.
 */
public class CarreraDetailActivity extends AppCompatActivity {

    private static final String ETIQUETA = "carrera detailactivicty";
    private Button fab;
    boolean esdelusuario;
    String nombredecarreraelegida;
    boolean respuesta;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrera_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);
        respuesta=false;
        fab = (Button) findViewById(R.id.fab);
        fab.setText("inscribirse");
        ArrayList<Carrera> carrerasdelusuario=(ArrayList<Carrera>)ConfigSingletton.getInstance().getUsuarioLogueado().getCarreras();
        nombredecarreraelegida=((Carrera) getIntent().getSerializableExtra(CarreraDetailFragment.ARG_ITEM_ID)).getNombre();
        esdelusuario = false;
        ArrayList<String> nombrescarrerasusr=new ArrayList<String>();
        for( Carrera c:carrerasdelusuario){

            Log.e(ETIQUETA,"Carrera del usuario " +c.getNombre());
            Log.e(ETIQUETA,"Esta carrera es del usuario? " +String.valueOf(c.getNombre().equals( nombredecarreraelegida)));
            nombrescarrerasusr.add(c.getNombre());
           /* if(!esdelusuario) {
                esdelusuario = c.getNombre().equals(nombredecarreraelegida);
            }*/
        }
        Log.e(ETIQUETA,"La comparacion dio: "+String.valueOf(nombrescarrerasusr.contains(nombredecarreraelegida)));
        esdelusuario=nombrescarrerasusr.contains(nombredecarreraelegida);

        if(nombrescarrerasusr.contains(nombredecarreraelegida)){
            fab.setText("ya inscripto");
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own detail action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                Log.e(ETIQUETA,"esdelusuario: "+true);
                if (!esdelusuario){
                    if(inscribiracarrera()){
                        fab.setText("inscripto");
                        esdelusuario=true;
                        //savedInstanceState.clear();
                    }
                    else {
                        fab.setText("inscripto");
                    }


                }
                else{
                    //inscribiraCarrera2();
                    Toast.makeText(getApplicationContext(),"Usuario anteriormente inscripto ",Toast.LENGTH_LONG).show();
                    //fab.setText("inscribirse");
                    //Desistircarrera();
                }
            }
        });



        Log.e(ETIQUETA,"Esta carrera es la elegida: " +nombredecarreraelegida);
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
      //  if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putSerializable(CarreraDetailFragment.ARG_ITEM_ID,
                    getIntent().getSerializableExtra(CarreraDetailFragment.ARG_ITEM_ID));
            arguments.putBoolean("estaanotado",esdelusuario);
            /*if (esdelusuario){
                fab.setText("inscripto");
                //fab.setBackgroundColor(a);
            }*/
            CarreraDetailFragment fragment = new CarreraDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.carrera_detail_container, fragment)
                    .commit();
       // }
    }

   /* private void Desistircarrera() {

            // Log.e(ETIQUETA, "traer el usuario: "+usernameUsuarioLogueado +" token :"+getTokenUsuarioLogueado());
        Retrofit retro=ConfigSingletton.getInstance().getRetro();
        ApiServiceInterface interfaz= retro.create(edu.grupo2.desarrollo.tecnobedeliasapp.api.ApiServiceInterface.class);
        respuestacall=interfaz.obtenerUsuarioLogueado(ConfigSingletton.getInstance().getTokenUsuarioLogueado());
            //final Usuario[] logeado = {null};


        {
        try {
            usuarioLogueado = respuestacall.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        }
        setUsuarioLogueado(logeado);
            //al ponerse enqueue se realiza asyncronicamente.
            respuestacall.enqueue(new Callback<Usuario>() {
                @Override
                public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                    if (response.isSuccessful()){
                        connectordb= new SQLiteHelper(getApplicationContext(), Constantes.NOMBREBD,null,1);

                        Usuario r=response.body();
                        Log.e(ETIQUETA, "en respuesta RESULTADOfin: " + r.getEmail());
                        ConfigSingletton.getInstance().setUsuarioLogueado(r);
                        //r=ConfigSingletton.getInstance().getUsuarioLogueado();
                        logueado =r;
                        Log.e(ETIQUETA, "y el apellido es..: " + logueado.getApellido());
                        if(persisitirUsuario(r))
                            Toast.makeText(getApplicationContext(),"se guardo correctamente: ",Toast.LENGTH_LONG).show();
                        loguearUsuario(r);


                        Intent in=new Intent(LoginActivity.this,MenuPrincipal.class);
                        startActivity(in);

                    }
                    else {
                        if (response.code()==401){
                            //Toast.makeText(, "usuario o contraseña erronea", Toast.LENGTH_SHORT).show();
                            Log.e(ETIQUETA, "en respuesta error: " + response.errorBody());

                        }

                    }
                }

                @Override
                public void onFailure(Call<Usuario> call, Throwable t) {
                    //Toast.makeText(getApplicationContext(), "no se pudo obtener el usuario", Toast.LENGTH_SHORT).show();
                    Log.e(ETIQUETA, "en falla:" + t.getMessage());
                }
            });
            //return r;
    }

*/
   private boolean inscribiraCarrera2(){
       Log.e(ETIQUETA,"intento insctibir con estos datos: "+ ConfigSingletton.getInstance().getTokenUsuarioLogueado()+nombredecarreraelegida);
       ConexionManual conn=new ConexionManual();
       try {

           String resultado =conn.execute(ConfigSingletton.getInstance().getUrlbase()+"inscripcion/carrera"+"?carrera="+ URLEncoder.encode(nombredecarreraelegida, "UTF-8")).get();
           Log.e(ETIQUETA,"La respuesta es: "+ resultado);
           if (resultado=="true"){

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
       }


   }

    private boolean inscribiracarrera() {
        Log.e(ETIQUETA,"intento insctibircon estos datos: "+ ConfigSingletton.getInstance().getTokenUsuarioLogueado()+nombredecarreraelegida);

        Retrofit retro=ConfigSingletton.getInstance().getRetro();
        ApiServiceInterface interfaz= retro.create(edu.grupo2.desarrollo.tecnobedeliasapp.api.ApiServiceInterface.class);

        Call<RespuestaApiBooleana> respuestacall=interfaz.inscribirseACarrera(ConfigSingletton.getInstance().getTokenUsuarioLogueado(),nombredecarreraelegida);
        respuestacall.enqueue(new Callback<RespuestaApiBooleana>() {
            @Override
            public void onResponse(Call<RespuestaApiBooleana> call, Response<RespuestaApiBooleana> response) {
                if(response.isSuccessful()) {
                    respuesta = response.body().getEstado();
                    if(respuesta){
                        Toast.makeText(getApplicationContext(),"Usuario inscripto "+response.body().getMensaje(),Toast.LENGTH_LONG).show();
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"Usuario anteriormente inscripto ",Toast.LENGTH_LONG).show();
                    }

                }
            }

            @Override
            public void onFailure(Call<RespuestaApiBooleana> call, Throwable t) {

            }
        });
        return respuesta;
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
            navigateUpTo(new Intent(this, CarreraListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
