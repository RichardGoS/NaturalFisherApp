package com.example.naturalfisherapp.view.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.naturalfisherapp.R;
import com.example.naturalfisherapp.data.system.Configuracion;
import com.example.naturalfisherapp.sqlite.crud.Select;
import com.example.naturalfisherapp.utilidades.InformacionSession;
import com.example.naturalfisherapp.utilidades.Utilidades;

/**
 * de RagooS
 * Autor: Richard Gomez O.
 * Para: EmpresaDevelopers.Frontend.NaturalFisher
 * Fecha: 11/06/2021
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //goToVentaPrinsipalActivity();

        if(InformacionSession.getInstance().getConfiguracion()==null){
            Configuracion configuracion = Select.seleccionarConfiguracion(getApplicationContext());
            if(configuracion == null){
                configuracion = Utilidades.leerFicheroConfiguracion();
                if(configuracion != null){
                    InformacionSession.getInstance().setConfiguracion(configuracion);
                }
            } else {
                InformacionSession.getInstance().setConfiguracion(configuracion);
            }
        }

        goToLogin();
    }

    /**
     * @Descripccion Metodo permite ir a la actividad Login
     * @Autor RagooS
     * @Date 04/07/21
     */
    private void goToLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    void goToVentaPrinsipalActivity(){
        Intent intent = new Intent(this, VentaPrinsipalActivity.class);
        startActivity(intent);
        finish();
    }
}