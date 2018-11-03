package edu.grupo2.desarrollo.tecnobedeliasapp.modelos;

import java.io.Serializable;

public class CursoEstudiante implements Serializable {

    private String apellido;
    private Integer id;
    private Integer nota;
    private String nombre;
    private String estado;


    public String getApellido() { return apellido; }

    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getNombre() { return nombre; }

    public void setNombre(String nombre) { this.nombre = nombre; }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNota() {
        return nota;
    }

    public void setNota(Integer nota) {
        this.nota = nota;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

}