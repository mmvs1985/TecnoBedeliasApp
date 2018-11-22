package edu.grupo2.desarrollo.tecnobedeliasapp.modelos;

public class Actividad {
    private String apellido;
    private String asignatura;
    private Integer creditos;
    private String estado;
    private String fecha;
    private Integer id;
    private String nombre;
    private Integer nota;
    private Integer notaMaxima;
    private String tipo;

    public Actividad() {
    }

    public Actividad(String apellido, String asignatura, Integer creditos, String estado, String fecha, Integer id, String nombre, Integer nota, Integer notaMaxima, String tipo) {
        this.apellido = apellido;
        this.asignatura = asignatura;
        this.creditos = creditos;
        this.estado = estado;
        this.fecha = fecha;
        this.id = id;
        this.nombre = nombre;
        this.nota = nota;
        this.notaMaxima = notaMaxima;
        this.tipo = tipo;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getAsignatura() {
        return asignatura;
    }

    public void setAsignatura(String asignatura) {
        this.asignatura = asignatura;
    }

    public Integer getCreditos() {
        return creditos;
    }

    public void setCreditos(Integer creditos) {
        this.creditos = creditos;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
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

    public Integer getNota() {
        return nota;
    }

    public void setNota(Integer nota) {
        this.nota = nota;
    }

    public Integer getNotaMaxima() {
        return notaMaxima;
    }

    public void setNotaMaxima(Integer notaMaxima) {
        this.notaMaxima = notaMaxima;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
