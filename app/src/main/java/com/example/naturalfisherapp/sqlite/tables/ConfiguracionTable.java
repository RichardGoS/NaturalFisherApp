package com.example.naturalfisherapp.sqlite.tables;

/**
 * de RagooS
 * Autor: Richard Gomez O.
 * Para: EmpresaDevelopers.Frontend.NaturalFisher
 * Fecha: 10/01/2022
 */

public class ConfiguracionTable {

    public static final String TABLA = "configuracion";

    public static final String CFG_ID = "cfg_id";
    public static final String CFG_DIRECCION_IP = "cfg_direccion_ip";
    public static final String CFG_PUERTO_IP = "cfg_puerto_ip";

    public static  final String CREAR_TABLE_CONFIGURACION =
            "CREATE TABLE " + TABLA + "("
                    + CFG_ID + " INT PRIMARY KEY, "
                    + CFG_DIRECCION_IP + " TEXT, "
                    + CFG_PUERTO_IP + " TEXT "
                    + ");";

    public static final String ELIMINAR_TABLA_CONFIGURACION = "DROP TABLE IF EXISTS " + TABLA + ";";

}
