package com.example.naturalfisherapp.sqlite.crud;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.naturalfisherapp.data.system.Configuracion;
import com.example.naturalfisherapp.sqlite.ConexionSqliteHelper;
import com.example.naturalfisherapp.sqlite.tables.ConfiguracionTable;
import com.example.naturalfisherapp.utilidades.Utilidades;

/**
 * de RagooS
 * Autor: Richard Gomez O.
 * Para: EmpresaDevelopers.Frontend.NaturalFisher
 * Fecha: 10/01/2022
 */

public class Select {

    // Objetos para la conexion
    public static ConexionSqliteHelper con = null;
    public static SQLiteDatabase db = null;

    /**
     * @Autor RagooS
     * @Descripcion Metodo permite seleccionar la configuracion en la base de datos
     * @param context contexto de la aplicacion
     * @return objeto de la Clase Configuracion
     */
    public static Configuracion seleccionarConfiguracion(Context context ){
        Configuracion configuracion = null;

        con = new ConexionSqliteHelper(context);

        db = con.getReadableDatabase();// se utiliza este metodo para hacer la lectura a la BD

        Cursor registro;

        String query = Utilidades.concatenar(new Object[]{"select distinct * from ",
                ConfiguracionTable.TABLA, " where ", ConfiguracionTable.CFG_ID  + "= 0"});

        registro = db.rawQuery(query, new String[]{});

        while(registro.moveToNext()){
            configuracion = new Configuracion(
                    registro.getInt(registro.getColumnIndex(ConfiguracionTable.CFG_ID)),
                    registro.getString(registro.getColumnIndex(ConfiguracionTable.CFG_DIRECCION_IP)),
                    registro.getString(registro.getColumnIndex(ConfiguracionTable.CFG_PUERTO_IP))
            );
        }

        db.close();

        return configuracion;
    }

}
