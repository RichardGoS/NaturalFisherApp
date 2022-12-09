package com.example.naturalfisherapp.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;

import com.example.naturalfisherapp.R;
import com.example.naturalfisherapp.data.models.Proveedor;
import com.example.naturalfisherapp.utilidades.EnumVariables;
import com.example.naturalfisherapp.utilidades.InformacionSession;
import com.example.naturalfisherapp.view.fragment.DetalleInversionFragment;
import com.example.naturalfisherapp.view.fragment.InversionBusquedaFragment;
import com.example.naturalfisherapp.view.interfaces.activities.IInversionPrincipalActivityView;

import butterknife.ButterKnife;

/**
 * Fase 4 Tarea 3
 * de RagooS
 * Autor: Richard Gomez O.
 * Para: EmpresaDevelopers.Frontend.NaturalFisher
 * Fecha: 14/09/2022
 */
public class InversionPrincipalActivity extends AppCompatActivity implements IInversionPrincipalActivityView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inversion_principal);
        getSupportActionBar().hide();

        ButterKnife.bind(this);

        goToInversionBusqueda();
    }

    /**
     * --------------================ METODOS =================--------------------------------
     */

    /**
     * -------------- METODOS SOBRESCRITOS --------------------------------
     */
    @Override
    public void onBackPressed() {
        if(InformacionSession.getInstance().getFragmentActual() != null){
            if(InformacionSession.getInstance().getFragmentActual().equals(EnumVariables.FRAGMENT_INVERSION_BUSQUEDA.getValor())){
                InformacionSession.getInstance().setDetalleInversiones(null);
                InformacionSession.getInstance().setBusquedaInversiones(null);
                goToMenuPrincipal();
            } else if(InformacionSession.getInstance().getFragmentActual().equals(EnumVariables.FRAGMENT_DETALLE_INVERSION.getValor())){
                goToInversionBusqueda();
            }
        } else {
            goToMenuPrincipal();
        }
    }
    /**
     * -------------- METODOS PROPIOS --------------------------------
     */

    /**
     * @Descripccion Metodo permite ir al fragment InversionBusquedaFragment
     * @Autor RagooS
     * @Date 18/09/2022
     */
    private void goToInversionBusqueda() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.inversion_container, InversionBusquedaFragment.newInstance(this, getSupportFragmentManager(), this));
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    /**
     * @Descripccion Metodo permite ir a la actividad MenuPrincipal
     * @Autor RagooS
     * @Date 18/09/2022
     */
    private void goToMenuPrincipal() {
        Intent intent = new Intent(this, MenuPrincipalActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * -------------- METODOS INTERFACE IInversionPrincipalActivityView --------------------------------
     */

    /**
     * @author RagooS
     * @fecha 01/10/2022
     * @descripcion Metodo permite ir al fragmen DetalleInversion
     */
    @Override
    public void goToDetalleInversion(Proveedor proveedor, boolean isNewInversion) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.inversion_container, DetalleInversionFragment.newInstance(this, getSupportFragmentManager(), proveedor, isNewInversion));
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}