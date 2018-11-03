package edu.grupo2.desarrollo.tecnobedeliasapp.modelos;

import java.io.Serializable;
import java.util.List;

public class Examen  implements Serializable {
    public List<EstudianteExamen> estudianteExamen;
    public String fecha;
    public String hora;
    public Integer id;
    public String nombreAsignatura;
}
