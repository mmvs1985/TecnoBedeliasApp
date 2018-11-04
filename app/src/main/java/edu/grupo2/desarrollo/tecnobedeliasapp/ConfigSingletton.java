package edu.grupo2.desarrollo.tecnobedeliasapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
//import modelos.Usuario;
import java.io.IOException;
import java.util.ArrayList;

import android.content.Context;

import edu.grupo2.desarrollo.tecnobedeliasapp.api.ApiProvider;
import edu.grupo2.desarrollo.tecnobedeliasapp.api.ApiServiceInterface;
import edu.grupo2.desarrollo.tecnobedeliasapp.dbSQLite.Constantes;
import edu.grupo2.desarrollo.tecnobedeliasapp.modelos.Carrera;
import edu.grupo2.desarrollo.tecnobedeliasapp.modelos.RespuestaApiLogin;
import edu.grupo2.desarrollo.tecnobedeliasapp.modelos.Usuario;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;


public class ConfigSingletton {

/**
 * Created by HP Usuario on 15/6/2018.
 */


    private static ConfigSingletton ourInstance; //= new ConfigSingletton();
    private static final String urlbase="http://192.168.1.8:8080/tecnobedelias-1.0.0-SNAPSHOT/";
    private static Usuario usuarioLogueado;
    private  String usernameUsuarioLogueado;
    private  String tokenUsuarioLogueado;
    private static Retrofit retro=null;
    private ArrayList<Carrera> carreras;
    private final String  ETIQUETA="ConfigSingletton";

    /*public synchronized static ConfigSingletton getInstance() {
        return ourInstance;
    }*/
    private ConfigSingletton() {
            }

    public static ConfigSingletton getInstance() {
        if (ourInstance == null){
            ourInstance = new ConfigSingletton();
        }
        else{
            Log.e("singletonconfig","No se puede crear el objeto "+ " porque ya existe un objeto de la clase SoyUnico");
        }

        return ourInstance;
    }

    public Retrofit getRetro() {
        if (ConfigSingletton.retro == null) {
            ConfigSingletton.retro = new Retrofit.Builder()
                    .baseUrl(ConfigSingletton.urlbase)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            Log.e(ETIQUETA,"retrofit creado");
            return ConfigSingletton.retro;
        }
        else {
            Log.e(ETIQUETA,"retrofit ya creado");
            return ConfigSingletton.retro;
        }
    }


    public String getUrlbase() {
        return urlbase;
    }

    public String getUsernameUsuarioLogueado() {
        return usernameUsuarioLogueado;
    }

    public void setUsernameUsuarioLogueado(String usernameUsuarioLogueado) {
        this.usernameUsuarioLogueado = usernameUsuarioLogueado;
        Log.e(ETIQUETA, "seteado el usuario: "+usernameUsuarioLogueado );

    }

    public Usuario traerUsuarioLogueado() {
       // Log.e(ETIQUETA, "traer el usuario: "+usernameUsuarioLogueado +" token :"+getTokenUsuarioLogueado());
        Retrofit retro=getRetro();
        ApiServiceInterface interfaz= retro.create(edu.grupo2.desarrollo.tecnobedeliasapp.api.ApiServiceInterface.class);
        Call<Usuario> respuestacall=interfaz.obtenerUsuarioLogueado(getTokenUsuarioLogueado());
        Usuario logeado= null;

/*
        {
        try {
            usuarioLogueado = respuestacall.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        }
        setUsuarioLogueado(logeado);*/
        //al ponerse enqueue se realiza asyncronicamente.
        respuestacall.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if (response.isSuccessful()){

                    Usuario r=response.body();
                    Log.e(ETIQUETA, "en respuesta RESULTADOfin: " + r.getEmail());
                    ConfigSingletton.getInstance().setUsuarioLogueado(r);
                    r=ConfigSingletton.getInstance().getUsuarioLogueado();
                    Log.e(ETIQUETA, "y el apellido es..: " + r.getApellido());

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
        return getUsuarioLogueado();

    }

    public String getTokenUsuarioLogueado() {
        return tokenUsuarioLogueado;
    }

    public void setTokenUsuarioLogueado(String tokenUsuarioLogueado) {
        this.tokenUsuarioLogueado = tokenUsuarioLogueado;
        ApiProvider.getInstance().getListacarrera();
        Log.e(ETIQUETA, "seteado el token: "+tokenUsuarioLogueado );
        //Log.e(ETIQUETA, "seteadas "+carreras.size()+" carreras" );
        //traerUsuarioLogueado();
    }

    public ArrayList<Carrera> getCarreras() {
        return carreras;
    }

    public void setCarreras(ArrayList<Carrera> carreras) {
        this.carreras = carreras;
    }

    public Usuario getUsuarioLogueado() {

        return usuarioLogueado;
        //return this.usuarioLogueado;
    }

    public void setUsuarioLogueado(Usuario usuarioLogueado) {
        ConfigSingletton.usuarioLogueado = usuarioLogueado;

    }
    public Usuario consultaUsuarioLogueado(Context cont){

        Usuario usr=new Usuario();

        SharedPreferences sp= cont.getSharedPreferences("usuarioLogueado",MODE_PRIVATE);

        usr.setId(sp.getInt(Constantes.CAMPOID,0));
        usr.setApellido(sp.getString(Constantes.CAMPOAPELLIDO,"no hay"));
        usr.setAppToken(sp.getString(Constantes.CAMPOAPPTOKEN,usr.getAppToken()));
        usr.setCedula(sp.getString(Constantes.CAMPOCEDULA,usr.getCedula()));
        usr.setEmail(sp.getString(Constantes.CAMPOEMAIL,usr.getEmail()));
        usr.setNombre(sp.getString(Constantes.CAMPONOMBRE,usr.getNombre()));
        usr.setFechaNacimiento(sp.getString(Constantes.CAMPOFNAC,usr.getFechaNacimiento()));
        usr.setPassword(sp.getString(Constantes.CAMPOPASSWORD,usr.getPassword()));
        usr.setResetToken(sp.getString(Constantes.CAMPORESETTOKEN,usr.getResetToken()));
        usr.setUsername(sp.getString(Constantes.CAMPOUSERNAME,usr.getUsername()));
        usr.setFoto(sp.getString(Constantes.CAMPOFOTO,usr.getFoto()));

        //getActivity().
        //Toast.makeText(getActivity().getAplicationContext(),"usuariocargado",Toast.LENGTH_LONG).show();
        Log.e(ETIQUETA,"usuarioConsultado");
        return usr;
    }





}
