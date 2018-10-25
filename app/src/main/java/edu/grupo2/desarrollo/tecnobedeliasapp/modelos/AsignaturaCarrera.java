
package edu.grupo2.desarrollo.tecnobedeliasapp.modelos;


public class AsignaturaCarrera {

    private Asignatura asignatura;
    private Integer creditos;
    private Boolean electiva;
    private Integer id;
    private Integer notaMaxima;
    private Integer notaMinimaExamen;
    private Integer notaMinimaExonera;
    private Integer notaSalvaExamen;

    public Asignatura getAsignatura() {
        return asignatura;
    }

    public void setAsignatura(Asignatura asignatura) {
        this.asignatura = asignatura;
    }

    public Integer getCreditos() {
        return creditos;
    }

    public void setCreditos(Integer creditos) {
        this.creditos = creditos;
    }

    public Boolean getElectiva() {
        return electiva;
    }

    public void setElectiva(Boolean electiva) {
        this.electiva = electiva;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNotaMaxima() {
        return notaMaxima;
    }

    public void setNotaMaxima(Integer notaMaxima) {
        this.notaMaxima = notaMaxima;
    }

    public Integer getNotaMinimaExamen() {
        return notaMinimaExamen;
    }

    public void setNotaMinimaExamen(Integer notaMinimaExamen) {
        this.notaMinimaExamen = notaMinimaExamen;
    }

    public Integer getNotaMinimaExonera() {
        return notaMinimaExonera;
    }

    public void setNotaMinimaExonera(Integer notaMinimaExonera) {
        this.notaMinimaExonera = notaMinimaExonera;
    }

    public Integer getNotaSalvaExamen() {
        return notaSalvaExamen;
    }

    public void setNotaSalvaExamen(Integer notaSalvaExamen) {
        this.notaSalvaExamen = notaSalvaExamen;
    }

}
