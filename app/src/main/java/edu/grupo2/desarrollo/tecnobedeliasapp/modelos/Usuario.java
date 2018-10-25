package edu.grupo2.desarrollo.tecnobedeliasapp.modelos;

import java.util.List;


    public class Usuario {

        private Boolean activa;
        private String apellido;
        private String appToken;
        private List<Carrera> carreras = null;
        private String cedula;
        private String email;
        private List<EstudianteExamen> estudianteExamen = null;
        private String fechaNacimiento;
        private Integer id;
        private String nombre;
        private String password;
        private String resetToken;
        private String username;

        public Boolean getActiva() {
            return activa;
        }

        public void setActiva(Boolean activa) {
            this.activa = activa;
        }

        public String getApellido() {
            return apellido;
        }

        public void setApellido(String apellido) {
            this.apellido = apellido;
        }

        public String getAppToken() {
            return appToken;
        }

        public void setAppToken(String appToken) {
            this.appToken = appToken;
        }

        public List<Carrera> getCarreras() {
            return carreras;
        }

        public void setCarreras(List<Carrera> carreras) {
            this.carreras = carreras;
        }

        public String getCedula() {
            return cedula;
        }

        public void setCedula(String cedula) {
            this.cedula = cedula;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public List<EstudianteExamen> getEstudianteExamen() {
            return estudianteExamen;
        }

        public void setEstudianteExamen(List<EstudianteExamen> estudianteExamen) {
            this.estudianteExamen = estudianteExamen;
        }

        public String getFechaNacimiento() {
            return fechaNacimiento;
        }

        public void setFechaNacimiento(String fechaNacimiento) {
            this.fechaNacimiento = fechaNacimiento;
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

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getResetToken() {
            return resetToken;
        }

        public void setResetToken(String resetToken) {
            this.resetToken = resetToken;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

    }

/*
    private Integer id;
    private String cedula;
    private String nombre;
    private String apellido;
    private String email;
    private String username;
    private String password;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}*/