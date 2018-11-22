package edu.grupo2.desarrollo.tecnobedeliasapp.api;

import com.google.gson.JsonObject;

import java.util.List;

import edu.grupo2.desarrollo.tecnobedeliasapp.modelos.Actividad;
import edu.grupo2.desarrollo.tecnobedeliasapp.modelos.Asignatura;
import edu.grupo2.desarrollo.tecnobedeliasapp.modelos.Carrera;
import edu.grupo2.desarrollo.tecnobedeliasapp.modelos.Curso;
import edu.grupo2.desarrollo.tecnobedeliasapp.modelos.Examen;
import edu.grupo2.desarrollo.tecnobedeliasapp.modelos.RespuestaApiBooleana;
import edu.grupo2.desarrollo.tecnobedeliasapp.modelos.RespuestaApiLogin;
import edu.grupo2.desarrollo.tecnobedeliasapp.modelos.Usuario;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiServiceInterface {

    @POST("login")
    Call<RespuestaApiLogin> obtenerrespuesta(@Body JsonObject datosUsuario);

    @GET("usuario/estudiante")
    Call<Usuario>obtenerUsuarioLogueado(@Header("Authorization") String token);
    //listas

    @GET("carrera/listar")
    Call<List<Carrera>>obtenerListacarrera(@Header("Authorization") String token);


    @GET("asignatura/listar")
    Call<List<Asignatura>> obtenerListaAsignaturas(@Header("Authorization") String token);

    @GET("usuario/escolaridad")
    Call<List<Actividad>> obtenerNotas(@Header("Authorization") String token,@Query("cedula")String cedula);


    //listar disponibles

    @GET("inscripcion/examen/disponibles")
    Call<List<Examen>>obtenerListaExamenDisponibles(@Header("Authorization") String token);

    @GET("inscripcion/curso/disponibles")
    Call<List<Curso>>obtenerListaCursoDisponibles(@Header("Authorization") String token);


    //consulta examenes de usuario

    @GET("inscripcion/examen/consulta")
    Call<List<Examen>>consultaExamenes(@Header("Authorization") String token);

    //consulta cursos de usuario

    @GET("inscripcion/curso/consulta")
    Call<List<Curso>>consultaCursos(@Header("Authorization") String token);

    //inscripciones

    @GET("inscripcion/carrera")
    Call<RespuestaApiBooleana>inscribirseACarrera(@Header("Authorization") String token, @Query("carrera")String nombreCarrera);

    @GET("inscripcion/curso")
    Call<RespuestaApiBooleana>inscribirseACurso(@Header("Authorization") String token, @Query("curso")String nombreCurso);

    @GET("inscripcion/examen")
    Call<RespuestaApiBooleana>inscribirseAExamen(@Header("Authorization") String token, @Query("examen")String idExamen);


    //desistir


    @GET("inscripcion/desistirexamen")
    Call<RespuestaApiBooleana>desistirAExamen(@Header("Authorization") String token, @Query("examen")String idExamen);

    @GET("inscripcion/desistircurso")
    Call<RespuestaApiBooleana>desistirACurso(@Header("Authorization") String token, @Query("curso")String idCurso);


}


