package edu.grupo2.desarrollo.tecnobedeliasapp.modelos;

import java.io.Serializable;
import java.util.List;

public class Curso  implements Serializable {


    private Integer anio;
    private List<CursoEstudiante> cursoEstudiante = null;
    private String fechaFin;
    private String fechaInicio;
    private List<Horario> horarios=null;
    private Integer id;
    private String nombreAsignatura;
    private Integer semestre;


    public String getNombreAsignatura() {
        return nombreAsignatura;
    }

    public void setNombreAsignatura(String nombreAsignatura) {
        this.nombreAsignatura = nombreAsignatura;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public Integer getSemestre() {
        return semestre;
    }

    public void setSemestre(Integer semestre) {
        this.semestre = semestre;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public List<CursoEstudiante> getCursoEstudiante() {
        return cursoEstudiante;
    }

    public void setCursoEstudiante(List<CursoEstudiante> cursoEstudiante) {
        this.cursoEstudiante = cursoEstudiante;
    }

    public List<Horario> getHorarios() {
        return horarios;
    }

    public void setHorarios(List<Horario> horarios) {
        this.horarios = horarios;
    }

}
