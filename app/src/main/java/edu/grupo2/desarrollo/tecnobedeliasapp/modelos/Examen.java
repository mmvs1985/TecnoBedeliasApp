package edu.grupo2.desarrollo.tecnobedeliasapp.modelos;

import java.io.Serializable;
import java.util.List;

public class Examen  implements Serializable {
    public List<EstudianteExamen> estudianteExamen;
    public String fecha;
    public String hora;
    public Integer id;
    public String nombreAsignatura;


    public List<EstudianteExamen> getEstudianteExamen() {
        return estudianteExamen;
    }

    public void setEstudianteExamen(List<EstudianteExamen> estudianteExamen) {
        this.estudianteExamen = estudianteExamen;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombreAsignatura() {
        return nombreAsignatura;
    }

    public void setNombreAsignatura(String nombreAsignatura) {
        this.nombreAsignatura = nombreAsignatura;
    }
}
