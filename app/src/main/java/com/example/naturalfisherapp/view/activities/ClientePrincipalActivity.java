package com.example.naturalfisherapp.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;

import com.example.naturalfisherapp.R;
import com.example.naturalfisherapp.view.fragment.ClienteBusquedaFragment;
import com.example.naturalfisherapp.view.fragment.ProductoBusquedaFragment;

import butterknife.ButterKnife;

/**
 * de RagooS
 * Autor: Richard Gomez O.
 * Para: EmpresaDevelopers.Frontend.NaturalFisher
 * Fecha: 29/12/2021
 */

public class ClientePrincipalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente_principal);

        getSupportActionBar().hide();

        ButterKnife.bind(this);

        goToClienteBusqueda();
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
     * @Date 29/12/21
     */
    private void goToMenuPrincipal() {
        Intent intent = new Intent(this, MenuPrincipalActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * @Descripccion Metodo permite ir al fragment ClienteBusqueda
     * @Autor RagooS
     * @Date 29/12/21
     */
    private void goToClienteBusqueda() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.cliente_container, ClienteBusquedaFragment.newInstance(this));
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}