
package edu.grupo2.desarrollo.tecnobedeliasapp.modelos;


import java.io.Serializable;
import java.util.List;

public class Asignatura  implements Serializable {

    //private Boolean activa;
    //private String codigo;
    private List<Curso> cursos = null;
    private String descripcion;
    private List<Examen> examenes = null;
    private Integer id;
    private String nombre;
    private Boolean taller;


   /* public Boolean getActiva() {
        return activa;
    }

    public void setActiva(Boolean activa) {
        this.activa = activa;
    }
*/
   /* public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }*/

    public List<Curso> getCursos() {
        return cursos;
    }

    public void setCursos(List<Curso> cursos) {
        this.cursos = cursos;
    }

    public List<Examen> getExamenes() {
        return examenes;
    }

    public void setExamenes(List<Examen> examenes) {
        this.examenes = examenes;
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
