package com.example.naturalfisherapp.view.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.naturalfisherapp.R;
import com.example.naturalfisherapp.data.system.Configuracion;
import com.example.naturalfisherapp.utilidades.InformacionSession;
import com.example.naturalfisherapp.view.dialog.InformacionDialogFragment;
import com.example.naturalfisherapp.view.interfaces.IConfiguracionView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * de RagooS
 * Autor: Richard Gomez O.
 * Para: EmpresaDevelopers.Frontend.NaturalFisher
 * Fecha: 24/09/2021
 */

public class ConfiguracionActivity extends AppCompatActivity implements IConfiguracionView {

    private String direccionIp;
    private String puerto;
    private String baseDatos;

    @BindView(R.id.edtDireccionIp)
    EditText edtDireccionIp;

    @BindView(R.id.edtPuerto)
    EditText edtPuerto;

    @BindView(R.id.edtBd)
    EditText edtBd;

    @BindView(R.id.llBtnAtras)
    LinearLayout llBtnAtras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion);

        getSupportActionBar().hide();

        ButterKnife.bind(this);

        setCamposDefault();
    }



    /**
     * --------------================ METODOS =================--------------------------------
     */

    /**
     * -------------- METODOS ONCLICK BUTTERKNIFE --------------------------------
     */

    @OnClick(R.id.btnConfirmar)
    void onClickConfirmar(){
        if(validarCampos()){
            setDatos();
            mostrarDialogoInformativo("Exito", "Se ha realizado la configuracion con exito!..");
        }
     }

     @OnClick(R.id.llBtnAtras)
     void onClickLlBtnAtras(){
         goToLoginActivity();
     }

    /**
     * -------------- METODOS PROPIOS --------------------------------
     */

    /**
     * @Descripccion Metodo permite setear los datos en la Informacion de session
     * @Autor RagooS
     * @Date 26/09/21
     */
    private void setDatos() {
        Configuracion confi = new Configuracion();
        confi.setIp(direccionIp);
        confi.setPuerto(puerto);
        InformacionSession.getInstance().setConfiguracion(confi);
    }

    /**
     * @Descripccion Metodo permite validar que los campos no esten vacios
     * @Autor RagooS
     * @Date 26/09/21
     */
    private boolean validarCampos() {

        boolean valido = false;

        direccionIp = edtDireccionIp.getText().toString();
        puerto = edtPuerto.getText().toString();
        baseDatos = edtBd.getText().toString();

        if( direccionIp != null && !direccionIp.equals("") ){
            if( puerto != null && !puerto.equals("") ){
                /*if( baseDatos != null && !baseDatos.equals("") ){
                    valido = true;
                } else {
                }*/
                valido = true;
            } else {
                mostrarDialogoInformativo("Error", "El campo puerto no debe estar vacio!...");
            }
        } else {
            mostrarDialogoInformativo("Error", "El campo direccion ip no debe estar vacio!...");
        }

        return valido;
    }

    /**
     * @Autor RagooS
     * @Date 06/07/21
     * @Descripcion Metodo permite mostrar dialogo informativo
     */
    public void mostrarDialogoInformativo(String tipo, String informacion) {
        InformacionDialogFragment informacionDialogFragment = InformacionDialogFragment.newInstance(tipo, informacion, this);
        informacionDialogFragment.show(getSupportFragmentManager(), "InformacionDialog");
        android.app.Fragment fragment = getFragmentManager().findFragmentByTag("InformacionDialog");
        if (fragment != null) {
            getFragmentManager().beginTransaction().remove(fragment).commit();
        }

        /*Intent intent = new Intent(activity, VentaPrinsipalActivity.class);
        startActivity(intent);
        activity.finish();*/

    }

    /**
     * @Autor RagooS
     * @Date 28/09/21
     * @Descripcion Metodo permite setear informacion default en los campos editext
     */
    private void setCamposDefault() {
        edtDireccionIp.setText("192.168.1.10");
        edtPuerto.setText("6111");
    }

    /**
     * -------------- METODOS INTERFACE IConfiguracionView --------------------------------
     */

    /**
     * @Descripccion Metodo permite ir a la actividad LoginActivity
     * @Autor RagooS
     * @Date 26/09/21
     */

    @Override
    public void goToLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}