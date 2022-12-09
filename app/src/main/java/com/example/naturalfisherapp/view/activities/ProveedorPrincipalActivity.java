package com.example.naturalfisherapp.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;

import com.example.naturalfisherapp.R;
import com.example.naturalfisherapp.view.fragment.ProductoBusquedaFragment;
import com.example.naturalfisherapp.view.fragment.ProveedorBusquedaFragment;

import butterknife.ButterKnife;

/**
 * Fase 4 Tarea 3
 * de RagooS
 * Autor: Richard Gomez O.
 * Para: EmpresaDevelopers.Frontend.NaturalFisher
 * Fecha: 27/09/2022
 */
public class ProveedorPrincipalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proveedor_principal);

        getSupportActionBar().hide();

        ButterKnife.bind(this);

        goToProveedorBusqueda();
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
    private void goToProveedorBusqueda() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.proveedor_container, ProveedorBusquedaFragment.newInstance(this, getSupportFragmentManager()));
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    /**
     * @Descripccion Metodo permite ir a la Actividad
     * @Autor RagooS
     * @Date 07/08/2022
     */
    private void goToInversionPrincipalActivity() {
        Intent intent = new Intent(this, InversionPrincipalActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * -------------- METODOS SOBRESCRITOS --------------------------------
     */
    @Override
    public void onBackPressed() {
        goToInversionPrincipalActivity();
    }


}