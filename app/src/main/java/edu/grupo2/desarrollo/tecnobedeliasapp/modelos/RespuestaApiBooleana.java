package edu.grupo2.desarrollo.tecnobedeliasapp.modelos;

public class RespuestaApiBooleana {

    private boolean estado;
    private String mensaje;

    public boolean getEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public RespuestaApiBooleana(boolean estado, String mensaje) {

        this.estado = estado;
        this.mensaje = mensaje;
    }

    public RespuestaApiBooleana() {

    }
}
