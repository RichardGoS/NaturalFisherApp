package com.example.naturalfisherapp.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;

import com.example.naturalfisherapp.R;
import com.example.naturalfisherapp.utilidades.UtilidadesView;
import com.example.naturalfisherapp.view.fragment.BodegaBusquedaFragment;

import butterknife.ButterKnife;

/**
 * de RagooS
 * Autor: Richard Gomez O.
 * Para: EmpresaDevelopers.Frontend.NaturalFisher
 * Fecha: 13/02/2022
 */

public class BodegaPrincipalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bodega_principal);

        getSupportActionBar().hide();

        ButterKnife.bind(this);

        goToBodegaFragment();
    }

    /**
     * --------------================ METODOS =================--------------------------------
     */

    /**
     * -------------- METODOS SOBRESCRITOS --------------------------------
     */

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        goToMenuPrincipal();
    }

    /**
     * -------------- METODOS PROPIOS --------------------------------
     */

    /**
     * @Descripccion Metodo permite ir a la actividad Login
     * @Autor RagooS
     * @Date 13/02/22
     */
    private void goToMenuPrincipal() {
        Intent intent = new Intent(this, MenuPrincipalActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * @Descripccion Metodo permite ir al fragment ClienteBusqueda
     * @Autor RagooS
     * @Date 13/02/2022
     */
    private void goToBodegaFragment(){
         FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
         fragmentTransaction.replace(R.id.bodega_container, BodegaBusquedaFragment.newInstance(this));
         fragmentTransaction.addToBackStack(null);
         fragmentTransaction.commit();
    }
}