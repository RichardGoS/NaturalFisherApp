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

public class Update {

    // Objetos para la conexion
    public static ConexionSqliteHelper con = null;
    public static SQLiteDatabase db = null;

    /**
     * @Autor RagooS
     * @Descripcion Metodo permite actualizar la informacion en la base de datos
     * @param context contexto de la aplicacion
     * @param param objeto de la clase
     * @param tabla tabla de base de datos a registrar
     */
    public static void actualizar(Context context, Object param, String tabla){

        con = new ConexionSqliteHelper(context);

        db = con.getWritableDatabase();

        ContentValues values = new ContentValues();

        switch(tabla){
            case ConfiguracionTable
                    .TABLA:

                Configuracion config = (Configuracion) param;

                values.put(ConfiguracionTable.CFG_ID, config.getId());
                values.put(ConfiguracionTable.CFG_DIRECCION_IP, config.getIp());
                values.put(ConfiguracionTable.CFG_PUERTO_IP, config.getPuerto());

                db.update(tabla, values,ConfiguracionTable.CFG_ID + "=" +config.getId(), null);
                SessionPreference.get(context).setConfiguracion(config.getId());

                Log.i("Update" + tabla , "se actualizo el registro en " + tabla);

                break;
        }

        db.close();

    }

}
