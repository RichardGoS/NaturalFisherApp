package com.example.naturalfisherapp.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;

import com.example.naturalfisherapp.R;
import com.example.naturalfisherapp.view.fragment.EstadisticaProductoBusquedaFragment;

import butterknife.ButterKnife;

/**
 * Fase 4 Tarea 1
 * de RagooS
 * Autor: Richard Gomez O.
 * Para: EmpresaDevelopers.Frontend.NaturalFisher
 * Fecha: 07/08/2022
 */
public class EstadisticaPrincipalActivity extends AppCompatActivity {

    private String fecha = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estadistica_principal);

        getSupportActionBar().hide();

        ButterKnife.bind(this);

        fecha = getIntent().getStringExtra("fecha");


        if(fecha != null && !fecha.equals("")){
            goToEstadisticaProductoBusqueda();
        }

    }

    /**
     * --------------================ METODOS =================--------------------------------
     */

    /**
     * -------------- METODOS PROPIOS --------------------------------
     */

    /**
     * @Descripccion Metodo permite ir al fragment EstadisticaProductoBusqueda
     * @Autor RagooS
     * @Date 07/08/2022
     */
    private void goToEstadisticaProductoBusqueda() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.estadistica_container, EstadisticaProductoBusquedaFragment.newInstance(this, fecha));
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    /**
     * @Descripccion Metodo permite ir a la Actividad VentaPrinsipalActivity
     * @Autor RagooS
     * @Date 07/08/2022
     */
    private void goToVentaPrincipalActivity() {
        Intent intent = new Intent(this, VentaPrinsipalActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * -------------- METODOS SOBRESCRITOS --------------------------------
     */
    @Override
    public void onBackPressed() {
        goToVentaPrincipalActivity();
    }


}