package edu.grupo2.desarrollo.tecnobedeliasapp.api;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import edu.grupo2.desarrollo.tecnobedeliasapp.ConfigSingletton;
import edu.grupo2.desarrollo.tecnobedeliasapp.modelos.Carrera;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ApiProvider {
    private final static String ETIQUETA="apiProvider";
    private ArrayList<Carrera> listacarrera;

    private static ApiProvider ourInstance= new ApiProvider();

    public ApiProvider() {
       }

    public ArrayList<Carrera> getListacarrera() {

        return obtenerListaTodaCarrera();
    }

    public static ApiProvider getInstance() {
        return ourInstance;
    }


    /*
            private void intentoLogin(Context contexto, String username, String pass) {

                Log.e(ETIQUETA, "intento logear con usr y cpass: "+username+" "+pass );
                final Context cont=contexto;
                final Retrofit retro = ConfigSingletton.getInstance().getRetro();
                //armo los datos de usuario para mandar
                JsonObject datosjson= new JsonObject();
                datosjson.addProperty("username",username);
                datosjson.addProperty("password",pass);
                //creo y llamo a la api
                final ApiServiceInterface interfaz= retro.create(edu.grupo2.desarrollo.tecnobedeliasapp.api.ApiServiceInterface.class);
                Log.e(ETIQUETA, "intento logear con este json: "+datosjson.toString());
                Call<RespuestaApiLogin> respuestacall=interfaz.obtenerrespuesta(datosjson);
                //al ponerse enqueue se realiza asyncronicamente.
                respuestacall.enqueue(new Callback<RespuestaApiLogin>() {
                    @Override
                    public void onResponse(Call<RespuestaApiLogin> call, Response<RespuestaApiLogin> response) {
                        if (response.isSuccessful()){
                            RespuestaApiLogin r=response.body();
                            boolean resultado=r.getAutorizado();
                            Log.e(ETIQUETA, "en respuesta RESULTADOfin: " + resultado);
                            if (resultado){
                                ConfigSingletton.getInstance().setUsernameUsuarioLogueado(response.body().getUsername());
                                ConfigSingletton.getInstance().setTokenUsuarioLogueado(response.headers().get("Authorization"));
                               // showProgress(false);

                                Log.e(ETIQUETA, "en respuesta entro: " + resultado);

                                Log.e(ETIQUETA, "en respuesta nomb usr res: " + response.body().getUsername());
                                Log.e(ETIQUETA, "en respuesta token res: " + response.headers().get("Authorization"));
                                traerUsuarioLogueado(retro,interfaz);
                                /*while (usrlg==null){
                                    try {
                                        Thread.sleep(3000);
                                        usrlg=traerUsuarioLogueado();
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }}
                                //Log.e(ETIQUETA, "apellido del usuario: " + logueado.getApellido());
                                if(logueado== null){
                                    Log.e(ETIQUETA, "sige siendo null: " );;
                                }

                            }
                            else{
                               // showProgress(false);
                                View focusView = null;
                                mPasswordView.setError(getString(R.string.error_incorrect_password));
                                focusView = mPasswordView;
                                focusView.requestFocus();
                                Log.e(ETIQUETA, "en respuesta nomb usr res: " + response.body().getUsername());;
                            }

                        }
                        else {
                            if (response.code()==401){
                                Toast.makeText(getApplicationContext(), "usuario o contraseña erronea", Toast.LENGTH_SHORT).show();
                                Log.e(ETIQUETA, "en respuesta error: " + response.errorBody());

                            }

                        }
                    }

                    @Override
                    public void onFailure(Call<RespuestaApiLogin> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "error de comunicacion", Toast.LENGTH_SHORT).show();
                        showProgress(false);
                        Log.e(ETIQUETA, "en falla:" + t.getMessage());
                    }
                });

            }
        */
    private ArrayList<Carrera> obtenerListaTodaCarrera() {
       // Log.e(ETIQUETA, "intento logear con usr y cpass: "+username+" "+pass );
        //final Context conte=cont;

        Retrofit retro = ConfigSingletton.getInstance().getRetro();



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
                    listacarrera=(ArrayList<Carrera>)r;
                    Log.e(ETIQUETA, "en respuesta lista carrera: " + r.size());
                    ConfigSingletton.getInstance().setCarreras(listacarrera);

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

        return listacarrera;

    }




}
