package com.example.naturalfisherapp.utilidades;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.naturalfisherapp.data.system.Configuracion;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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
    public static Double restringirDecimales(Double valor){

        valor = Math.round(valor * Math.pow(10, 3)) / Math.pow(10, 3);

        return valor;
    }

    /**
     * @Autor RagooS
     * @Fecha 04/05/2022
     * @Descripccion Metodo permite restringir la cantidad de decimales maximo 2
     * @param valor a formatear
     * @return Doble valor formateado
     */
    public static Double restringirDecimalesPorcentage(Double valor){

        valor = Math.round(valor * Math.pow(10, 2)) / Math.pow(10, 2);

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

    /**
     * @Autor RagooS
     * @Fecha 10/01/2022
     * @Descripccion Metodo permite obtener permisis de escritura sobre la aplicacion
     * @param context contexto de la aplicacion
     * @param activity activity de la aplicacion
     * @return boolean true cuando se obtienen correctamente de lo contrario false
     */
    public static boolean permisosEscritura(Context context, Activity activity) {
        boolean permiso = false;

        int permissionCheck = ContextCompat.checkSelfPermission(
                context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            Log.i("Mensaje", "No se tiene permiso para leer.");
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 225);
        } else {
            Log.i("Mensaje", "Se tiene permiso para leer y escribir!");
            permiso = true;
        }

        return permiso;
    }

    /**
     * @Autor RagooS
     * @Fecha 10/01/2022
     * @Descripccion Metodo permite concatenar una lista de objetos
     * @param lista objetos a concatenar
     * @return String con los objetos cocatenados
     */
    public static String concatenar(Object [] lista){
        String cadena = "";

        for(Object item: lista){
            cadena += item.toString();
        }

        return cadena;
    }

    /**
     * @Autor RagooS
     * @Fecha 10/01/2022
     * @Descripccion Metodo permite obtener la informacion del archivo creado
     * @return Objeto de la clase Configuracion con los datos leidos en el archivo
     */
    public static Configuracion leerFicheroConfiguracion(){

        Configuracion confi = new Configuracion();

        try {
            File carpeta = new File(Environment.getExternalStorageDirectory(),"DataNaturalFisherMarket");

            if(carpeta.exists()){
                File fileRegistro = new File(carpeta, "configuracion.txt");

                BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileRegistro)));
                String lei = "";

                while(lei != null){
                    lei = reader.readLine();
                    if(lei != null){
                        if(lei.contains(EnumVariables.DIRECCION_IP_SERVIDOR.getValor())){
                            String info[] = lei.split(":");
                            confi.setIp(info[1].trim());
                        } else if(lei.contains(EnumVariables.PUERTO_SERVIDOR.getValor())){
                            String info[] = lei.split(":");
                            confi.setPuerto(info[1].trim());
                        }
                    }
                }

                reader.close();
            } else {
                confi = null;
                Log.i("ERROR", "No existe la carpeta contenedora del archivo de configuracion");
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.i("ERROR", "Error al crear el archivo " + e.toString());
            confi = null;
        }

        return confi;
    }

    /**
     * @Date 10/01/2022
     * @Descripccion Metodo permite escribir sobre el archivo de configuracion
     * @param configuracion
     * @return
     */
    public static boolean escribirFicheroConfiguracion(Configuracion configuracion){

        boolean escrito = false;

        if(configuracion != null){
            try {
                File carpeta = new File(Environment.getExternalStorageDirectory(),"DataNaturalFisherMarket");

                if(!carpeta.exists()){
                    carpeta.mkdir();
                }

                File fileRegistro = new File(carpeta, "configuracion.txt");

                if(fileRegistro.exists()){
                    if(fileRegistro.delete()){

                        fileRegistro = new File(carpeta, "configuracion.txt");

                        FileWriter escribir = new FileWriter(fileRegistro.getAbsoluteFile(), true);

                        escribir.append(EnumVariables.DIRECCION_IP_SERVIDOR.getValor()+ ":" + configuracion.getIp() );
                        escribir.flush();
                        escribir.append("\n");
                        escribir.flush();
                        escribir.append(EnumVariables.PUERTO_SERVIDOR.getValor()+ ":" + configuracion.getPuerto() );
                        escribir.flush();

                        escrito = true;

                        Log.i("ArchivoCreado:", " Utilidades Se registro con exito en el archivo");
                    } else {
                        Log.i("ERROR:", " No se elimino el archivo");

                    }
                } else {
                    FileWriter escribir = new FileWriter(fileRegistro.getAbsoluteFile(), true);

                    escribir.append(EnumVariables.DIRECCION_IP_SERVIDOR.getValor()+ ":" + configuracion.getIp() );
                    escribir.flush();
                    escribir.append("\n");
                    escribir.flush();
                    escribir.append(EnumVariables.PUERTO_SERVIDOR.getValor()+ ":" + configuracion.getPuerto() );
                    escribir.flush();

                    escrito = true;

                    Log.i("ArchivoCreado:", " Utilidades Se registro con exito en el archivo");
                }

            } catch (IOException e) {
                e.printStackTrace();
                Log.i("ERROR", "Error al crear el archivo " + e.toString());
            }
        } else {
            Log.i("ERROR", "Objeto configuracion NULL");
        }

        return escrito;
    }

    /**
     * @Date 10/01/2022
     * @Descripccion Metodo permite comparar fechas
     * @param fecha1
     * @param fecha2
     * @return boolean
     */
    public static boolean compararFechasMismoDia(Date fecha1, Date fecha2){

        boolean igual = false;
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

        String fechaStr1 = "";
        String fechaStr2 = "";

        try{
            fechaStr1 = format.format(fecha1);
            fechaStr2 = format.format(fecha2);
            fecha1 = format.parse(fechaStr1);
            fecha2 = format.parse(fechaStr2);

            if(fecha1.equals(fecha2)){
                igual = true;
            }


        } catch (Exception e){
            e.printStackTrace();
            igual = false;
        }

        return igual;
    }

    public static String formatearFecha2(Date date){

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

        String fechaReturn = "";

        try{
            fechaReturn = format.format(date);
        } catch (Exception e){
            e.printStackTrace();
        }

        return fechaReturn;
    }

    /**
     * @Date 24/05/2022
     * @Descripccion Metodo permite obtener la hora
     * @return String
     */
    @SuppressLint("SimpleDateFormat")
    public static String getHora(){
        return new SimpleDateFormat("HH:mm").format(new Date());
    }

    /**
     * @Date 24/05/2022
     * @Descripccion Metodo permite obtener la fecha
     * @return String
     */
    public static String getFecha(){
        return new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
    }

    /**
     * @Autor RagooS
     * @Descripccion Metodo permite extraer el año y el mes de la fecha
     * @Fecha 10/03/2022
     */
    public static String obtenerMesAñoMesFecha(Date fechaActual) {
        String mesReturn = "";

        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM");

        mesReturn = format.format(fechaActual);

        return mesReturn;

    }


}
