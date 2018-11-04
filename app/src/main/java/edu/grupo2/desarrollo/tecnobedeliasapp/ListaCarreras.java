package edu.grupo2.desarrollo.tecnobedeliasapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import edu.grupo2.desarrollo.tecnobedeliasapp.Fragments.CarrerasAdapter;
import edu.grupo2.desarrollo.tecnobedeliasapp.api.ApiServiceInterface;
import edu.grupo2.desarrollo.tecnobedeliasapp.modelos.Carrera;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ListaCarreras extends AppCompatActivity {

    private static String ETIQUETA="listaCarrera";
    private ArrayList<Carrera> listacarrera;
    private Retrofit retro;
    private RecyclerView vistalista;
    private CarrerasAdapter adaptador;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_carreras);
        retro = ConfigSingletton.getInstance().getRetro();
        obtenerListaTodaCarrera();
        vistalista = (RecyclerView) findViewById(R.id.rvlistacarrera);
        vistalista.setLayoutManager(new LinearLayoutManager(this));
        //creo el arreglo vacio
        listacarrera = new ArrayList<>();
        //y defino el adaptador, que se encarga de cargar la lista con los datos del array
        adaptador = new CarrerasAdapter(listacarrera);
        vistalista.setAdapter(adaptador);

    }


    private void obtenerListaTodaCarrera() {
        // Log.e(ETIQUETA, "intento logear con usr y cpass: "+username+" "+pass );
        //final Context conte=cont;

        Retrofit retro = ConfigSingletton.getInstance().getRetro();

        ArrayList<Carrera> resultado;

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

                    listacarrera.addAll(r);
                    Log.e(ETIQUETA, "en respuesta lista carrera: " + r.size());
                    for(Carrera c:listacarrera){
                        Log.e(ETIQUETA, "lista carrera: " + c.getNombre()+" desc: "+c.getDescripcion()+" creditos: "+c.getCreditosMinimos());
                    }
                    ConfigSingletton.getInstance().setCarreras(listacarrera);
                    adaptador.notifyDataSetChanged();
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

       // return listacarrera;

    }
}
