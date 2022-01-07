package com.example.naturalfisherapp.view.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.naturalfisherapp.R;
import com.example.naturalfisherapp.view.dialog.AgregarClienteDialogFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * de RagooS
 * Autor: Richard Gomez O.
 * Para: EmpresaDevelopers.Frontend.NaturalFisher
 * Fecha: 04/07/2021
 */

public class MenuPrincipalActivity extends AppCompatActivity {

    private boolean isFABOpen = false;

    @BindView(R.id.btnAddProducto)
    FloatingActionButton btnAddProducto;

    @BindView(R.id.btnAddVenta)
    FloatingActionButton btnAddVenta;

    @BindView(R.id.btnOpenMenu)
    FloatingActionButton btnOpenMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);

        getSupportActionBar().hide();

        ButterKnife.bind(this);

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
        goToLogin();
    }

    /**
     * -------------- METODOS ONCLICK BUTTERKNIFE --------------------------------
     */

    @OnClick(R.id.btnVentas)
    void goToVentas(){
        goToVentaPrinsipalActivity();
    }

    @OnClick(R.id.btnProductos)
    void goToProductos(){
        goToProductoPrinsipalActivity();
    }

    @OnClick(R.id.btnClientes)
    void goToClientes(){
        goToClientesPrinsipalActivity();
    }

    @OnClick(R.id.btnOpenMenu)
    void openMenu(){
        if(isFABOpen){
            closeFABMenu();
        } else {
            showFABMenu();
        }
    }

    @OnClick(R.id.btnAddVenta)
    void onClickVender(){
        goToVender();
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
     * @Descripccion Metodo permite ir a la actividad VentaPrincipal
     * @Autor RagooS
     * @Date 04/07/21
     */
    void goToVentaPrinsipalActivity(){
        Intent intent = new Intent(this, VentaPrinsipalActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * @Descripccion Metodo permite ir a la actividad VentaPrincipal
     * @Autor RagooS
     * @Date 09/07/21
     */
    void goToProductoPrinsipalActivity(){
        Intent intent = new Intent(this, ProductoPrincipalActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * @Descripccion Metodo permite ir a la actividad ClientePrincipal
     * @Autor RagooS
     * @Date 29/12/21
     */
    void goToClientesPrinsipalActivity(){
        Intent intent = new Intent(this, ClientePrincipalActivity.class);
        startActivity(intent);
        finish();
    }


    /**
     * @Descripccion Metodo permite ir a la actividad VentaPrincipal para generar una venta
     * @Autor RagooS
     * @Date 13/07/21
     */
    void goToVender(){
        AgregarClienteDialogFragment agregarClienteDialogFragment = AgregarClienteDialogFragment.newInstance(this, getSupportFragmentManager(), "Agregar Cliente a la Venta");
        agregarClienteDialogFragment.show(getSupportFragmentManager(), "AgregarCliente");
        android.app.Fragment fragment = getFragmentManager().findFragmentByTag("AgregarCliente");
        if (fragment != null) {
            getFragmentManager().beginTransaction().remove(fragment).commit();
        }
    }

    /**
     * @Descripccion Metodo permite mostrar los botones flotantes
     * @Autor RagooS
     * @Date 13/07/21
     */
    private void showFABMenu(){
        isFABOpen=true;
        btnAddVenta.setVisibility(View.VISIBLE);
        btnAddProducto.setVisibility(View.VISIBLE);
        btnAddVenta.animate().translationX(-getResources().getDimension(R.dimen.standard_65));
        btnAddProducto.animate().translationX(-getResources().getDimension(R.dimen.standard_130));
    }

    /**
     * @Descripccion Metodo permite ocultar los botones flotantes
     * @Autor RagooS
     * @Date 13/07/21
     */
    private void closeFABMenu(){
        isFABOpen=false;
        btnAddVenta.animate().translationX(0);
        btnAddProducto.animate().translationX(0);
        btnAddVenta.setVisibility(View.GONE);
        btnAddProducto.setVisibility(View.GONE);
    }
}