package com.example.naturalfisherapp.view.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.naturalfisherapp.R;
import com.example.naturalfisherapp.data.models.Cliente;
import com.example.naturalfisherapp.presenter.activities.ClientePresenter;
import com.example.naturalfisherapp.presenter.interfaces.IClientePresenter;
import com.example.naturalfisherapp.view.interfaces.IAgregarClienteDialogFragmentView;
import com.example.naturalfisherapp.view.interfaces.dialog.IDetalleClienteDialogFragment;
import com.example.naturalfisherapp.view.interfaces.fragment.IClienteBusquedaFragmentView;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * de RagooS
 * Autor: Richard Gomez O.
 * Para: EmpresaDevelopers.Frontend.NaturalFisher
 * Fecha: 30/12/2021
 */

public class DetalleClienteDialogFragment extends DialogFragment implements IDetalleClienteDialogFragment {

    private Dialog dialog;
    private Activity activity;
    private Cliente cliente;
    private ProgressDialog progress;
    private IClientePresenter clientePresenter;
    private IClienteBusquedaFragmentView iClienteBusquedaFragmentView;
    private IAgregarClienteDialogFragmentView iAgregarClienteDialogFragmentView;
    private String tipoMensaje;

    @BindView(R.id.txtTitulo)
    TextView txtTitulo;

    @BindView(R.id.direccionCliente)
    TextView direccionCliente;

    @BindView(R.id.telefonoCliente)
    TextView telefonoCliente;

    @BindView(R.id.txtMensaje)
    TextView txtMensaje;

    @BindView(R.id.llMensajeDetalleCliente)
    LinearLayout llMensajeDetalleCliente;

    @BindView(R.id.llBotonesDetalleCliente)
    LinearLayout llBotonesDetalleCliente;

    @BindView(R.id.llBtnAtrasDetalleCliente)
    LinearLayout llBtnAtrasDetalleCliente;

    @BindView(R.id.llBtnEliminarDetalleCliente)
    LinearLayout llBtnEliminarDetalleCliente;

    @BindView(R.id.llBtnActualizarDetalleCliente)
    LinearLayout llBtnActualizarDetalleCliente;

    @BindView(R.id.llImageUsuario)
    LinearLayout llImageUsuario;

    public static DetalleClienteDialogFragment newInstance(Activity activity, Cliente cliente, IClienteBusquedaFragmentView iClienteBusquedaFragmentView){
        DetalleClienteDialogFragment detalleClienteDialogFragment = new DetalleClienteDialogFragment();
        detalleClienteDialogFragment.activity = activity;
        detalleClienteDialogFragment.cliente = cliente;
        detalleClienteDialogFragment.iClienteBusquedaFragmentView = iClienteBusquedaFragmentView;
        return  detalleClienteDialogFragment;
    }

    public static DetalleClienteDialogFragment newInstance(Activity activity, Cliente cliente, IAgregarClienteDialogFragmentView iAgregarClienteDialogFragmentView, String tipoMensaje){
        DetalleClienteDialogFragment detalleClienteDialogFragment = new DetalleClienteDialogFragment();
        detalleClienteDialogFragment.activity = activity;
        detalleClienteDialogFragment.cliente = cliente;
        detalleClienteDialogFragment.iAgregarClienteDialogFragmentView = iAgregarClienteDialogFragmentView;
        detalleClienteDialogFragment.tipoMensaje = tipoMensaje;
        return  detalleClienteDialogFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_FRAME, 0);
        //setHasOptionsMenu(true);
    }


    @NonNull
    @NotNull
    @Override
    public Dialog onCreateDialog(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(getActivity().LAYOUT_INFLATER_SERVICE);
        View agregarClienteDialog = inflater.inflate(R.layout.dialog_fragment_detalle_cliente, null);

        ButterKnife.bind(this, agregarClienteDialog);

        builder.setView(agregarClienteDialog);

        txtTitulo.setText(cliente.getNombre());

        progress = new ProgressDialog(getContext());

        clientePresenter = new ClientePresenter(getContext(), iClienteBusquedaFragmentView, this);

        direccionCliente.setText(cliente.getDireccion());

        telefonoCliente.setText(cliente.getTelefono());

        dialog = builder.create();

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        if(iAgregarClienteDialogFragmentView != null && tipoMensaje!= null && !tipoMensaje.equals("")){
            mostrarMensaje(tipoMensaje);
        } else {
            llBotonesDetalleCliente.setVisibility(View.VISIBLE);
            llMensajeDetalleCliente.setVisibility(View.GONE);
        }

        return dialog;
    }

    /**
     * --------------================ METODOS =================--------------------------------
     */

    /**
     * -------------- METODOS ONCLICK BUTTERKNIFE --------------------------------
     */

    @OnClick(R.id.llBtnAtrasDetalleCliente)
    void OnClickBtnAtras(){
        System.out.println("Atras");
        dialog.dismiss();
    }

    @OnClick(R.id.llBtnEliminarDetalleCliente)
    void OnClickBtnEliminar(){
        System.out.println("Eliminar");
        mostrarDialogoConfirmacion();
        //clientePresenter.eliminarCliente(cliente);
    }

    @OnClick(R.id.llBtnActualizarDetalleCliente)
    void OnClickBtnActualizar(){
        System.out.println("Actualizar");
        AgregarClienteDialogFragment agregarClienteDialogFragment = AgregarClienteDialogFragment.newInstance(activity, cliente, "Actualizar Cliente", this);
        agregarClienteDialogFragment.show(getFragmentManager(), "ActualizarCliente");
        Fragment fragment = getFragmentManager().findFragmentByTag("ActualizarCliente");
        if (fragment != null) {
            getFragmentManager().beginTransaction().remove(fragment).commit();
        }
    }

    /**
     * -------------- METODOS PROPIOS --------------------------------
     */

    /**
     * @Autor RagooS
     * @Descripccion Metodo permite mostrar alerta de confirmacion antes de realizar una accion
     * @Fecha 04/01/2021
     */
    private void mostrarDialogoConfirmacion() {
        ConfirmarDialogFragment confirmarDialogFragment = ConfirmarDialogFragment.newInstance("Eliminar Cliente", "Esta seguro que desea eliminar el cliente...", "ALVERTENCIA", this);
        confirmarDialogFragment.show(getFragmentManager(), "EliminarCliente");
        android.app.Fragment fragment = activity.getFragmentManager().findFragmentByTag("EliminarCliente");
        if (fragment != null) {
            activity.getFragmentManager().beginTransaction().remove(fragment).commit();
        }
    }



    /**
     * -------------- METODOS INTERFACE IDetalleClienteDialogFragment --------------------------------
     */

    /**
     * @Autor RagooS
     * @Descripccion Metodo permite mostrar el progressBar
     * @param mensaje variable tipo String el cual contiene el mensaje que muestra el progresbar
     * @Fecha 03/01/2021 antes
     */
    @Override
    public void showProgress(String mensaje) {
        progress = ProgressDialog.show(getContext(), mensaje,null);
    }

    /**
     * @Autor RagooS
     * @Descripccion Metodo permite ocultar el progressBar
     * @Fecha 03/01/2021 antes
     */
    @Override
    public void hideProgress() {
        progress.dismiss();
    }

    /**
     * @Autor RagooS
     * @Descripccion Metodo permite ocultar el dialogo
     * @Fecha 03/01/2021 antes
     */
    @Override
    public void dismissDialog() {
        dialog.dismiss();
    }

    /**
     * @Autor RagooS
     * @Descripccion Metodo permite mostrar mensaje depues de accion
     * @param tipoMensaje variable tipo string contiene el tipo de mensaje a mostrar
     * @Fecha 03/01/2021
     */
    @Override
    public void mostrarMensaje(String tipoMensaje) {

        boolean exitoso = false;

        llBotonesDetalleCliente.setVisibility(View.GONE);
        llMensajeDetalleCliente.setVisibility(View.VISIBLE);

        if(tipoMensaje.equals("ERROR_ELIMINACION")){
            llImageUsuario.setBackgroundResource(R.drawable.ic_cliente_alvertencia);
            txtMensaje.setText("ALVERTENCIA! No se elimino.");
            exitoso = false;
        } else if(tipoMensaje.equals("EXITO_ELIMINACION")){
            llImageUsuario.setBackgroundResource(R.drawable.ic_cliente_eliminado);
            txtMensaje.setText("Se Elimino Con Exito.");
            exitoso = true;
        } else if(tipoMensaje.equals("ERROR_CREACION")){
            llImageUsuario.setBackgroundResource(R.drawable.ic_cliente_alvertencia);
            txtMensaje.setText("ALVERTENCIA! No se registro.");
            exitoso = false;
        } else if(tipoMensaje.equals("EXITO_CREACION")) {
            llImageUsuario.setBackgroundResource(R.drawable.ic_cliente_exito);
            txtMensaje.setText("Creado Con Éxito!");
            exitoso = true;
        } else if(tipoMensaje.equals("EXITO_ACTUALIZACION")) {
            llImageUsuario.setBackgroundResource(R.drawable.ic_cliente_exito);
            txtMensaje.setText("Actualizado Con Éxito!");
            exitoso = true;
        }

        boolean finalExitoso = exitoso;

        Thread timer = new Thread() {
            public void run(){
                try {
                    sleep(2000);
                    dismissDialog();
                    if(iClienteBusquedaFragmentView != null && finalExitoso){
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                iClienteBusquedaFragmentView.actualizarDatos();
                            }
                        });
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        };
        timer.start();
    }

    /**
     * @Autor RagooS
     * @Descripccion Metodo permite llamar al metodo que permite la eliminacion del usuario
     * @Fecha 04/01/2021
     */
    @Override
    public void eliminarCliente() {
        clientePresenter.eliminarCliente(cliente);
    }
}
