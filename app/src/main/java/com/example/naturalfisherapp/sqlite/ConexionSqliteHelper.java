package com.example.naturalfisherapp.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.naturalfisherapp.data.system.Configuracion;
import com.example.naturalfisherapp.sqlite.tables.ConfiguracionTable;

/**
 * de RagooS
 * Autor: Richard Gomez O.
 * Para: EmpresaDevelopers.Frontend.NaturalFisher
 * Fecha: 10/01/2022
 */

public class ConexionSqliteHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "configuracion";

    public ConexionSqliteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ConfiguracionTable.CREAR_TABLE_CONFIGURACION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(ConfiguracionTable.ELIMINAR_TABLA_CONFIGURACION);
    }
}
