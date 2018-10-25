package edu.grupo2.desarrollo.tecnobedeliasapp.modelos;

public class RespuestaApiLogin {

    private Boolean autorizado;
    private String username;

    public Boolean getAutorizado() {
        return autorizado;
    }

    public void setAutorizado(Boolean autorizado) {
        this.autorizado = autorizado;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
