package com.example.naturalfisherapp.aplication;

import android.app.Application;

import com.example.naturalfisherapp.sqlite.ConexionSqliteHelper;


/**
 * de RagooS
 * Autor: Richard Gomez O.
 * Para: EmpresaDevelopers.Frontend.NaturalFisher
 * Fecha: 10/01/2022
 */

public class NaturalFisherApp extends Application {

    ConexionSqliteHelper con;

    @Override
    public void onCreate() {

        super.onCreate();

        con = new ConexionSqliteHelper(NaturalFisherApp.this);// se crea la conexion

        con.close();// se cierra la conexion con la Base de Datos
    }

}
