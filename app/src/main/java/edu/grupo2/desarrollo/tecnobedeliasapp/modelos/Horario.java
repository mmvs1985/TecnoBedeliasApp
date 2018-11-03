package edu.grupo2.desarrollo.tecnobedeliasapp.modelos;

import java.io.Serializable;

public class Horario implements Serializable {
    private String dia;
    private String horaFin;
    private String horaInicio;
    private Integer id;

    public Horario() {
    }

    public Horario(String dia, String horaFin, String horaInicio, Integer id) {
        this.dia = dia;
        this.horaFin = horaFin;
        this.horaInicio = horaInicio;
        this.id = id;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public String getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(String horaFin) {
        this.horaFin = horaFin;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
