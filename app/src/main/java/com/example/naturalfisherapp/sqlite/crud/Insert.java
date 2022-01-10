package com.example.naturalfisherapp.sqlite.crud;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.naturalfisherapp.data.system.Configuracion;
import com.example.naturalfisherapp.data.system.SessionPreference;
import com.example.naturalfisherapp.sqlite.ConexionSqliteHelper;
import com.example.naturalfisherapp.sqlite.tables.ConfiguracionTable;

/**
 * de RagooS
 * Autor: Richard Gomez O.
 * Para: EmpresaDevelopers.Frontend.NaturalFisher
 * Fecha: 10/01/2022
 */

public class Insert {

    /**
     * --------------================ METODOS =================--------------------------------
     */

    /**
     * -------------- METODOS PROPIOS --------------------------------
     */

    /**
     * @Autor RagooS
     * @Descripcion Metodo permite registrar la informacion en la base de datos
     * @param context contexto de la aplicacion
     * @param param objeto de la clase
     * @param tabla tabla de base de datos a registrar
     */
    public static void registrar(Context context, Object param, String tabla){

        ConexionSqliteHelper con = new ConexionSqliteHelper(context);

        SQLiteDatabase db = con.getWritableDatabase();

        ContentValues values = new ContentValues();

        switch(tabla){
            case ConfiguracionTable
                    .TABLA:

                Configuracion config = (Configuracion) param;

                values.put(ConfiguracionTable.CFG_ID, config.getId());
                values.put(ConfiguracionTable.CFG_DIRECCION_IP, config.getIp());
                values.put(ConfiguracionTable.CFG_PUERTO_IP, config.getPuerto());

                db.insert(tabla, ConfiguracionTable.CFG_ID, values);
                SessionPreference.get(context).setConfiguracion(config.getId());

                Log.i("Insert" + tabla , "se inserto registro en " + tabla);

                break;
        }

        db.close();

    }

}
