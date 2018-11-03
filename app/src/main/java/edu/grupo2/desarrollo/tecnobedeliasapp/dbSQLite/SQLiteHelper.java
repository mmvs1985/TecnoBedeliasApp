package edu.grupo2.desarrollo.tecnobedeliasapp.dbSQLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static edu.grupo2.desarrollo.tecnobedeliasapp.dbSQLite.Constantes.SCRIPTCREATETABLEUSUARIOS;
import static edu.grupo2.desarrollo.tecnobedeliasapp.dbSQLite.Constantes.SCRIPTDROPTABLEUSUARIOS;

public class SQLiteHelper extends SQLiteOpenHelper {
/*
    private List<Carrera> carreras = null;
    private List<EstudianteExamen> estudianteExamen = null;
*/


    public SQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(SCRIPTCREATETABLEUSUARIOS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAntigua, int versionNueva) {
        db.execSQL(SCRIPTDROPTABLEUSUARIOS);
        db.execSQL(SCRIPTCREATETABLEUSUARIOS);
    }
}
