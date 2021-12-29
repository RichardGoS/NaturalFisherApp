package com.example.naturalfisherapp.view.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.example.naturalfisherapp.R;
import com.example.naturalfisherapp.utilidades.InformacionSession;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * de RagooS
 * Autor: Richard Gomez O.
 * Para: EmpresaDevelopers.Frontend.NaturalFisher
 * Fecha: 4/07/2021
 */

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.btnEntrar)
    LinearLayout btnEntrar;

    @BindView(R.id.llBtnConfig)
    LinearLayout llBtnConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().hide();

        ButterKnife.bind(this);
    }

    /**
     * --------------================ METODOS =================--------------------------------
     */

    /**
     * -------------- METODOS ONCLICK BUTTERKNIFE --------------------------------
     */

    @OnClick(R.id.btnEntrar)
    void goToEntrar(){
        //goToVentaPrinsipalActivity();
        if(InformacionSession.getInstance().getConfiguracion() != null){
            goToMenuPrincipalActivity();
        }

    }

    @OnClick(R.id.llBtnConfig)
    void onClickLlBtnConfig(){
        goToConfiguracionActivity();
    }



    /**
     * -------------- METODOS PROPIOS --------------------------------
     */

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
     * @Descripccion Metodo permite ir a la actividad MenuPrincipal
     * @Autor RagooS
     * @Date 04/07/21
     */
    private void goToMenuPrincipalActivity() {
        Intent intent = new Intent(this, MenuPrincipalActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * @Descripccion Metodo permite ir a la actividad MenuPrincipal
     * @Autor RagooS
     * @Date 04/07/21
     */
    private void goToConfiguracionActivity() {
        Intent intent = new Intent(this, ConfiguracionActivity.class);
        startActivity(intent);
        finish();
    }

}