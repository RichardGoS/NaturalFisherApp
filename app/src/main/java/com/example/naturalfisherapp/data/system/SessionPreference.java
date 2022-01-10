package com.example.naturalfisherapp.data.system;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * de RagooS
 * Autor: Richard Gomez O.
 * Para: EmpresaDevelopers.Frontend.NaturalFisher
 * Fecha: 10/01/2022
 */

public class SessionPreference {

    // constantes
    private static final String PREF_NAME = "DATA_SESSION";

    private static final String PREF_CONFIGURACION = "PREF_CONFIGURACION";

    private final SharedPreferences nPref;
    private static SessionPreference INSTANCE;

    public static SessionPreference get(Context context) {
        if( INSTANCE == null){
            INSTANCE = new SessionPreference(context);
        }
        return INSTANCE;
    }

    public SessionPreference(Context context) {
        this.nPref = context.getApplicationContext().getSharedPreferences(PREF_NAME, context.MODE_PRIVATE);
    }

    public void setConfiguracion(int codigo){
        SharedPreferences.Editor objEdit = nPref.edit();
        objEdit.putInt(PREF_CONFIGURACION, codigo + 1);
        objEdit.apply();
    }

    public int getConfiguracion(){
        return nPref.getInt(PREF_CONFIGURACION, 1);
    }

}
