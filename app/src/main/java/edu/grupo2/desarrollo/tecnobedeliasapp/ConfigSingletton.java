package edu.grupo2.desarrollo.tecnobedeliasapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;

import edu.grupo2.desarrollo.tecnobedeliasapp.api.ApiProvider;
import edu.grupo2.desarrollo.tecnobedeliasapp.api.ApiServiceInterface;
import edu.grupo2.desarrollo.tecnobedeliasapp.dbSQLite.Constantes;
import edu.grupo2.desarrollo.tecnobedeliasapp.modelos.Asignatura;
import edu.grupo2.desarrollo.tecnobedeliasapp.modelos.AsignaturaCarrera;
import edu.grupo2.desarrollo.tecnobedeliasapp.modelos.Carrera;
import edu.grupo2.desarrollo.tecnobedeliasapp.modelos.Curso;
import edu.grupo2.desarrollo.tecnobedeliasapp.modelos.EstudianteExamen;
import edu.grupo2.desarrollo.tecnobedeliasapp.modelos.Usuario;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;

//import modelos.Usuario;


public class ConfigSingletton {

/**
 * Created by HP Usuario on 15/6/2018.
 */


    private static ConfigSingletton ourInstance; //= new ConfigSingletton();
    //endpoint yama
    //private static final String urlbase="http://192.168.1.114:8080/tecnobedelias/";
    //endpoint casa
    //private static final String urlbase="http://192.168.1.8:8080/tecnobedelias/";
    //Endpoint aws
    private static final String urlbase="http://ec2-3-16-143-114.us-east-2.compute.amazonaws.com:8080/tecnobedelias/";
    private static Usuario usuarioLogueado;
    private  String usernameUsuarioLogueado;
    private  String tokenUsuarioLogueado;
    private static Retrofit retro=null;
    private ArrayList<Carrera> carreras;
    private final String  ETIQUETA="ConfigSingletton";
    private ArrayList<Asignatura> asignaturas;

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

    public void traerUsuarioLogueado(final Context context) {
         Log.e(ETIQUETA, "traer el usuario: "+usernameUsuarioLogueado +" token :"+getTokenUsuarioLogueado());
        ApiServiceInterface interfaz= getRetro().create(edu.grupo2.desarrollo.tecnobedeliasapp.api.ApiServiceInterface.class);
        Call<Usuario> respuestacall=interfaz.obtenerUsuarioLogueado(getTokenUsuarioLogueado());
        //al ponerse enqueue se realiza asyncronicamente.
        respuestacall.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if (response.isSuccessful()){
                    Usuario r=response.body();
                    Log.e(ETIQUETA, "en respuesta RESULTADO fin: " + r.getEmail());
                    setUsuarioLogueado(r);
                    //r=ConfigSingletton.getInstance().getUsuarioLogueado();

                    Log.e(ETIQUETA, "y el apellido es..: " + r.getApellido());

                    ArrayList<EstudianteExamen> ee= (ArrayList<EstudianteExamen>) r.getEstudianteExamen();
                    for (EstudianteExamen e:ee){
                        Log.e(ETIQUETA,e.getEstado()+e.getId());

                    }
                    /*if(persisitirUsuario(r))
                        Toast.makeText(getApplicationContext(),"se guardo correctamente: ",Toast.LENGTH_LONG).show();*/
                    loguearUsuario(r,context);

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
    private void loguearUsuario(Usuario usr, Context conte){
        SharedPreferences sp=conte.getSharedPreferences("usuarioLogueado",MODE_PRIVATE);
        SharedPreferences.Editor spEditor = sp.edit();
        spEditor.putInt(Constantes.CAMPOID,usr.getId());
        spEditor.putString(Constantes.CAMPOAPELLIDO,usr.getApellido());
        spEditor.putString(Constantes.CAMPOAPPTOKEN,usr.getAppToken());
        spEditor.putString(Constantes.CAMPOCEDULA,usr.getCedula());
        spEditor.putString(Constantes.CAMPOEMAIL,usr.getEmail());
        spEditor.putString(Constantes.CAMPONOMBRE,usr.getNombre());
        spEditor.putString(Constantes.CAMPOFNAC,usr.getFechaNacimiento());
        spEditor.putString(Constantes.CAMPOPASSWORD,usr.getPassword());
        spEditor.putString(Constantes.CAMPORESETTOKEN,usr.getResetToken());
        spEditor.putString(Constantes.CAMPOUSERNAME,usr.getUsername());
        spEditor.putString(Constantes.CAMPOFOTO,usr.getFoto());
        spEditor.putString("APIAUTH",ConfigSingletton.getInstance().getTokenUsuarioLogueado());


        spEditor.commit();
        ArrayList<Carrera> carrerasUsr= (ArrayList<Carrera>)usr.getCarreras();//.get(0).getAsignaturaCarrera().get(1).getAsignatura().getCursos().get(0).getSemestre().toString();
        ArrayList<AsignaturaCarrera> asigcarrerasUsr;
        ArrayList<Curso> cursosUsr;
        String topico;
        for(Carrera c: carrerasUsr ){
            asigcarrerasUsr=(ArrayList<AsignaturaCarrera>)c.getAsignaturaCarrera();
            for(AsignaturaCarrera ac: asigcarrerasUsr ){
                cursosUsr=(ArrayList<Curso>)ac.getAsignatura().getCursos();
                for (Curso cu: cursosUsr){
                    topico=ac.getAsignatura().getNombre().replace(" ","_") +"-"+String.valueOf(cu.getSemestre()+"-"+String.valueOf(cu.getAnio()));
                    FirebaseMessaging.getInstance().subscribeToTopic(topico);
                    Log.d(ETIQUETA,"me inscribi al topico: "+topico);
                    //asignatura_semestre_año
                }
            }
        }
    }

    public ArrayList<Asignatura> getAsignaturas() {
        return asignaturas;
    }

    public void setAsignaturas(ArrayList<Asignatura> asignaturas) {
        this.asignaturas = asignaturas;
    }



  /*  private boolean ConsultarExamenesUsuario(String idExamen) {

        //TODO: LLAMAR A LA API PARA QUE SE INSCRIBA AL CURSO
        Log.e(ETIQUETA,"intento consulto examen con estos datos: "+ ConfigSingletton.getInstance().getTokenUsuarioLogueado()+" idExamen: "+idExamen);


        final Retrofit retro = ConfigSingletton.getInstance().getRetro();

        retorno=false;

        //creo y llamo a la api
        final ApiServiceInterface interfaz= retro.create(edu.grupo2.desarrollo.tecnobedeliasapp.api.ApiServiceInterface.class);
        String token=ConfigSingletton.getInstance().getTokenUsuarioLogueado();
        Log.e(ETIQUETA, "token del usuario logueado: "+token);

        Call<List<Examen>> respuestacall=interfaz.consultaExamenes(token);
        //al ponerse enqueue se realiza asyncronicamente.
        respuestacall.enqueue(new Callback<List<Examen>>() {
            @Override
            public void onResponse(Call<List<Examen>> call, Response<List<Examen>> response) {

                if (response.isSuccessful()) {
                    List<Examen> r = response.body();
                    Log.e(ETIQUETA,"el contiene da: "+r.contains(examenSeleccionado));

                   // /*for (Examen e:r) {
                        e.getId()

                   // }*//*
                    Toast.makeText(getApplicationContext(), "listo", Toast.LENGTH_LONG).show();
                    retorno = true;//r.getEstado();
                    if (retorno){
                        //fab.setText("desistir");
                        /*LoginActivity loginActivity=new LoginActivity();
                        loginActivity.traerUsuarioLogueado(retro,interfaz);*/
                      /*  ConfigSingletton.getInstance().traerUsuarioLogueado(getApplicationContext());
                    }

                    //Log.e(ETIQUETA, "en respuesta incribirse a curso: " + r.getEstado() + " MSg: " + r.getMensaje());
                }
                else{

                    Log.e(ETIQUETA, "en respuesta  consulta examen no exitosa ");
                }
            }

            @Override
            public void onFailure(Call<List<Examen>> call, Throwable t) {
                //Toast.makeText(conte.getApplicationContext(), "error de comunicacion", Toast.LENGTH_SHORT).show();
                // showProgress(false);
                Log.e(ETIQUETA, "en falla:" + t.getMessage());
            }
        });

        return true;//retorno;

    }*/


}
