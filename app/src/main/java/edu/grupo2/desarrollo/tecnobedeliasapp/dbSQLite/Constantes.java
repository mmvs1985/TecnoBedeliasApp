package edu.grupo2.desarrollo.tecnobedeliasapp.dbSQLite;

public class Constantes {

    public  static  final String NOMBREBD="dbtest";
    //tablausuarios
    public static final String TABLAUSUARIOS="usuarios";
    public static final String TABLAASIGNATURAS="";
    //campos
    public static final String CAMPOID="id";
    public static final String CAMPOAPELLIDO="apellido";
    public static final String CAMPOAPPTOKEN="app_token";
    public static final String CAMPOCEDULA="cedula";
    public static final String CAMPOEMAIL="email";
    public static final String CAMPOFNAC="fecha_nacimiento";
    public static final String CAMPONOMBRE="nombre";
    public static final String CAMPOPASSWORD="password";
    public static final String CAMPORESETTOKEN="reset_token";
    public static final String CAMPOUSERNAME="username";
    public static final String CAMPOFOTO ="foto" ;
    //scripts
    public static final String SCRIPTCREATETABLEUSUARIOS = "CREATE TABLE "+ TABLAUSUARIOS+"("+CAMPOID+" bigint NOT NULL, "+CAMPOAPELLIDO+" character varying(255)  NOT NULL, "+CAMPOAPPTOKEN+" character varying(255), "+CAMPOCEDULA+" character varying(255)  NOT NULL,"+CAMPOEMAIL+" character varying(255)  NOT NULL, "+CAMPOFNAC+" timestamp without time zone,"+CAMPONOMBRE+" character varying(255)  NOT NULL, "+CAMPOPASSWORD+" character varying(255) NOT NULL, "+CAMPORESETTOKEN+" character varying(255), "+CAMPOUSERNAME+" character varying(255) NOT NULL)";
    public static final String SCRIPTDROPTABLEUSUARIOS="DROP TABLE IF EXISTS "+TABLAUSUARIOS;


}
