package com.example.naturalfisherapp.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.naturalfisherapp.R;
import com.example.naturalfisherapp.data.models.Cliente;
import com.example.naturalfisherapp.data.models.Venta;
import com.example.naturalfisherapp.retrofit.ClientApiService;
import com.example.naturalfisherapp.retrofit.InterfaceApiService;
import com.example.naturalfisherapp.utilidades.EnumVariables;
import com.example.naturalfisherapp.utilidades.InformacionSession;
import com.example.naturalfisherapp.view.adapter.VentaRegistroAdapter;
import com.example.naturalfisherapp.view.dialog.AgregarClienteDialogFragment;
import com.example.naturalfisherapp.view.fragment.DetalleRegistroVentaFragment;
import com.example.naturalfisherapp.view.fragment.VentaBusquedaFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * de RagooS
 * Autor: Richard Gomez O.
 * Para: EmpresaDevelopers.Frontend.NaturalFisher
 * Fecha: 11/06/2021
 */

public class VentaPrinsipalActivity extends AppCompatActivity {

    private Activity activity;
    private LinearLayoutManager linearLayoutManagerVentas;
    private InterfaceApiService service;

    //@BindView(R.id.efRvVentas)
    //RecyclerView recyclerViewVentas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venta_prinsipal);

        getSupportActionBar().hide();

        ButterKnife.bind(this);

        boolean isVenta = getIntent().getBooleanExtra("vender", false);

        if(isVenta){
            if(InformacionSession.getInstance().getClienteNewVenta() != null ){
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.venta_container, DetalleRegistroVentaFragment.newInstance(this, true, InformacionSession.getInstance().getClienteNewVenta()));
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        } else {
            goToVentaBusqueda();
        }

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
            if(InformacionSession.getInstance().getFragmentActual().equals(EnumVariables.FRAGMENT_VENTA_BUSQUEDA.getValor())){
                InformacionSession.getInstance().setDetalleVentas(null);
                InformacionSession.getInstance().setVentasConsultadas(null);

                InformacionSession.getInstance().setProductosActivosVenta(null);
                goToMenuPrincipal();
            } else if(InformacionSession.getInstance().getFragmentActual().equals(EnumVariables.FRAGMENT_DETALLE_REGISTRO_VENTA.getValor())){
                goToVentaBusqueda();
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
    private void goToLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * @Descripccion Metodo permite ir a la actividad MenuPrincipal
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
     * @Date 06/07/21
     * @Descripccion Metodo permite ir a el fragment VentaBusqueda
     */
    private void goToVentaBusqueda() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.venta_container, VentaBusquedaFragment.newInstance(this, getSupportFragmentManager()));
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}