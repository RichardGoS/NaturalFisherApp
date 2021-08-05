package com.example.naturalfisherapp.utilidades;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * de RagooS
 * Autor: Richard Gomez O.
 * Para: EmpresaDevelopers.Frontend.NaturalFisher
 * Fecha: 03/07/2021
 */

public class Utilidades {

    /**
     * @Autor RagooS
     * @Fecha 06/07/21
     * @Descripccion Metodo permite formatear Date a string en formato dd/mm/yy
     * @param date a formatear
     * @return String cadena de caracteres que representan fecha formateada
     */
    public static String formatearFecha(Date date){

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

        String fechaReturn = "";

        try{
            fechaReturn = format.format(date);
        } catch (Exception e){
            e.printStackTrace();
        }

        return fechaReturn;
    }

    /**
     * @Autor RagooS
     * @Fecha 06/07/21
     * @Descripccion Metodo permite agregar puntos de mil en un valor Double
     * @param valor a agregar puntos de mil
     * @return String cadena de numeros con puntos de mil
     */
    public static String puntoMil (Double valor){

        String cadenaReturn = "";

        DecimalFormat formatea = new DecimalFormat("###,###");

        cadenaReturn = formatea.format(valor);


        return cadenaReturn;
    }

    /**
     * @Autor RagooS
     * @Fecha 18/07/21
     * @Descripccion Metodo permite cortar cadenas muy largas hasta la cantidad de caracteres indicada
     * @param cadena a cortar
     * @param cantidad largo de cadena esperado
     * @return String cadena cortada
     */
    public static String cortarCadena(String cadena, int cantidad) {

        if( cadena.length() > cantidad){
            cadena = cadena.substring(0, cantidad);
        }

        return cadena;
    }

    /**
     * @Autor RagooS
     * @Fecha 18/07/21
     * @Descripccion Metodo permite restringir la cantidad de decimales maximo 3
     * @param valor a formatear
     * @return Doble valor formateado
     */
    public static Double restringirDecimales (Double valor){

        valor = Math.round(valor * Math.pow(10, 3)) / Math.pow(10, 3);

        return valor;
    }

    /**
     * @Autor RagooS
     * @Fecha 18/07/21
     * @Descripccion Metodo permite obtener fecha en formato dd/MM/yyyy
     * @param date a formatear
     * @return date ya formateado
     */
    public static Date obtenerFechaFormatoEstandar(Date date){
        SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");

        try {
            String fectaTem = format.format(date);
            date = format.parse(fectaTem);
        } catch (ParseException e) {
            e.printStackTrace();
            date = null;
        }

        return date;
    }
}
