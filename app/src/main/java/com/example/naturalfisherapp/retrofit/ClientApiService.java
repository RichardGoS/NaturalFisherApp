 package com.example.naturalfisherapp.retrofit;

import android.content.Context;

import com.example.naturalfisherapp.utilidades.InformacionSession;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

 /**
  * de RagooS
  * Autor: Richard Gomez O.
  * Para: EmpresaDevelopers.Frontend.NaturalFisher
  * Fecha: 23/06/2021
  */

public class ClientApiService {

    public static Retrofit retrofit = null;
     public Context context;
    public static String BASE_URL = "http://"+ InformacionSession.getInstance().getConfiguracion().getIp() + ":"+ InformacionSession.getInstance().getConfiguracion().getPuerto() + "/appi/";

    /*public ClientApiService(Context context) {
        this.context = context;

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }*/

    public static Retrofit getClient() throws Exception {
        //Configuracion configuracion = SessionDAO.getDataBaseDAO().obtenerConfiguracion();
        //final String BASE_URL = "http://"+configuracion.getIp()+":"+configuracion.getPuerto();
        //   Log.i("BASE_URL", BASE_URL);
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();

        //Para aumentar el timeOut un poco mas de lo normal
        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();

        try {
            if (retrofit == null) {
                retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .client(okHttpClient)
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .build();
            } else {
                if (!retrofit.baseUrl().toString().equalsIgnoreCase(BASE_URL)) {
                    retrofit = new Retrofit.Builder()
                            .baseUrl(BASE_URL)
                            .client(okHttpClient)
                            .addConverterFactory(GsonConverterFactory.create(gson))
                            .build();
                }
            }
        }catch (Exception e){
            //    e.printStackTrace();
            //Activity activity = InformacionSession.getInstance().getActivity();
            //throw new Exception(activity.getResources().getString(R.string.error_configuracion_conexion));
            throw new Exception("Excepccion Retrofit");
        }

        return retrofit;
    }
}
