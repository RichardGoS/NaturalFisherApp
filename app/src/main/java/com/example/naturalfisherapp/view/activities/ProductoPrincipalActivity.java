package com.example.naturalfisherapp.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;

import com.example.naturalfisherapp.R;
import com.example.naturalfisherapp.utilidades.EnumVariables;
import com.example.naturalfisherapp.utilidades.InformacionSession;
import com.example.naturalfisherapp.view.fragment.ProductoBusquedaFragment;
import com.example.naturalfisherapp.view.fragment.VentaBusquedaFragment;

import butterknife.ButterKnife;


/**
 * de RagooS
 * Autor: Richard Gomez O.
 * Para: EmpresaDevelopers.Frontend.NaturalFisher
 * Fecha: 09/07/2021
 */

public class ProductoPrincipalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producto_principal);

        getSupportActionBar().hide();

        ButterKnife.bind(this);

        goToProductoBusqueda();
    }

    /**
     * --------------================ METODOS =================--------------------------------
     */

    /**
     * -------------- METODOS SOBRESCRITOS --------------------------------
     */

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        if(InformacionSession.getInstance().getFragmentActual() != null){
            if(InformacionSession.getInstance().getFragmentActual().equals(EnumVariables.FRAGMENT_PROMOCION_DETALLE.getValor())){
                goToProductoBusqueda();
            } else if(InformacionSession.getInstance().getFragmentActual().equals(EnumVariables.FRAGMENT_PRODUCTO_BUSQUEDA.getValor())){
                InformacionSession.getInstance().setProductosConsultados(null);
                InformacionSession.getInstance().setProductosActivosPromo(null);
                goToMenuPrincipal();
            }
        } else {
            goToMenuPrincipal();
        }
    }

    /**
     * -------------- METODOS PROPIOS --------------------------------
     */

    /**
     * @Descripccion Metodo permite ir a la actividad Login
     * @Autor RagooS
     * @Date 04/07/21
     */
    private void goToMenuPrincipal() {
        Intent intent = new Intent(this, MenuPrincipalActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * @Autor RagooS
     * @Date 09/07/21
     * @Descripccion Metodo permite ir a el fragment VentaBusqueda
     */
    private void goToProductoBusqueda(){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.producto_container, ProductoBusquedaFragment.newInstance(this));
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}