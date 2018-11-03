
package edu.grupo2.desarrollo.tecnobedeliasapp.modelos;

import java.io.Serializable;
import java.util.List;

public class Carrera implements Serializable{

    //private Boolean activa;
    private List<AsignaturaCarrera> asignaturaCarrera = null;
    private Integer creditosMinimos;
    private String descripcion;
    private Integer id;
    private String nombre;

    /*public Boolean getActiva() {
        return activa;
    }

    public void setActiva(Boolean activa) {
        this.activa = activa;
    }*/

    public List<AsignaturaCarrera> getAsignaturaCarrera() {
        return asignaturaCarrera;
    }

    public void setAsignaturaCarrera(List<AsignaturaCarrera> asignaturaCarrera) {
        this.asignaturaCarrera = asignaturaCarrera;
    }

    public Integer getCreditosMinimos() {
        return creditosMinimos;
    }

    public void setCreditosMinimos(Integer creditosMinimos) {
        this.creditosMinimos = creditosMinimos;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
