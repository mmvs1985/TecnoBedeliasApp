package edu.grupo2.desarrollo.tecnobedeliasapp.api;

import com.google.gson.JsonObject;

import edu.grupo2.desarrollo.tecnobedeliasapp.modelos.RespuestaApiLogin;
import edu.grupo2.desarrollo.tecnobedeliasapp.modelos.Usuario;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiServiceInterface {

    @POST("login")
    Call<RespuestaApiLogin> obtenerrespuesta(@Body JsonObject datosUsuario);

    @GET("usuario/estudiante")
    Call<Usuario>obtenerUsuarioLogueado(@Header("Authorization") String token);

}

