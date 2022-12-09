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
import androidx.fragment.app.FragmentManager;

import com.example.naturalfisherapp.R;
import com.example.naturalfisherapp.data.models.Proveedor;
import com.example.naturalfisherapp.presenter.activities.ProveedorPresenter;
import com.example.naturalfisherapp.presenter.interfaces.IProveedorPresenter;
import com.example.naturalfisherapp.view.interfaces.dialog.IAgregarCrearProveedorDialogFragmentView;
import com.example.naturalfisherapp.view.interfaces.dialog.IDetalleProveedorDialogFragmentView;
import com.example.naturalfisherapp.view.interfaces.fragment.IProveedorBusquedaFragmentView;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Fase 4 Tarea 3
 * de RagooS
 * Autor: Richard Gomez O.
 * Para: EmpresaDevelopers.Frontend.NaturalFisher
 * Fecha: 01/10/2022
 */
public class DetalleProveedorDialogFragment extends DialogFragment implements IDetalleProveedorDialogFragmentView {

    private Dialog dialog;
    private Activity activity;
    private Proveedor proveedor;
    private ProgressDialog progress;
    private String tipoMensaje;
    private IProveedorPresenter proveedorPresenter;
    private IAgregarCrearProveedorDialogFragmentView agregarCrearProveedorDialogFragmentView;
    private IProveedorBusquedaFragmentView proveedorBusquedaFragmentView;
    private FragmentManager fragmentManager;

    @BindView(R.id.txtTitulo)
    TextView txtTitulo;

    @BindView(R.id.nitProveedor)
    TextView nitProveedor;

    @BindView(R.id.direccionProveedor)
    TextView direccionProveedor;

    @BindView(R.id.ciudadProveedor)
    TextView ciudadProveedor;

    @BindView(R.id.telefonoProveedor)
    TextView telefonoProveedor;

    @BindView(R.id.descripcionProveedor)
    TextView descripcionProveedor;

    @BindView(R.id.txtMensaje)
    TextView txtMensaje;

    @BindView(R.id.llMensajeDetalleProveedor)
    LinearLayout llMensajeDetalleProveedor;

    @BindView(R.id.llBotonesDetalleProveedor)
    LinearLayout llBotonesDetalleProveedor;

    @BindView(R.id.llBtnAtrasDetalleProveedor)
    LinearLayout llBtnAtrasDetalleProveedor;

    @BindView(R.id.llBtnEliminarDetalleProveedor)
    LinearLayout llBtnEliminarDetalleProveedor;

    @BindView(R.id.llBtnActualizarDetalleProveedor)
    LinearLayout llBtnActualizarDetalleProveedor;

    @BindView(R.id.llImageUsuario)
    LinearLayout llImageUsuario;

    @BindView(R.id.llDescripcionProveedor)
    LinearLayout llDescripcionProveedor;

    public static DetalleProveedorDialogFragment newInstance (Activity activity, FragmentManager fragmentManager, Proveedor proveedor, IProveedorBusquedaFragmentView proveedorBusquedaFragmentView){
        DetalleProveedorDialogFragment detalleProveedorDialogFragment = new DetalleProveedorDialogFragment();
        detalleProveedorDialogFragment.activity = activity;
        detalleProveedorDialogFragment.fragmentManager = fragmentManager;
        detalleProveedorDialogFragment.proveedor = proveedor;
        detalleProveedorDialogFragment.proveedorBusquedaFragmentView = proveedorBusquedaFragmentView;

        return detalleProveedorDialogFragment;
    }

    public static DetalleProveedorDialogFragment newInstance (Activity activity, FragmentManager fragmentManager,  Proveedor proveedor, IAgregarCrearProveedorDialogFragmentView agregarCrearProveedorDialogFragmentView, String tipoMensaje){
        DetalleProveedorDialogFragment detalleProveedorDialogFragment = new DetalleProveedorDialogFragment();
        detalleProveedorDialogFragment.activity = activity;
        detalleProveedorDialogFragment.fragmentManager = fragmentManager;
        detalleProveedorDialogFragment.proveedor = proveedor;
        detalleProveedorDialogFragment.agregarCrearProveedorDialogFragmentView = agregarCrearProveedorDialogFragmentView;
        detalleProveedorDialogFragment.tipoMensaje = tipoMensaje;

        return detalleProveedorDialogFragment;
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
        View agregarClienteDialog = inflater.inflate(R.layout.dialog_fragment_detalle_proveedor, null);

        ButterKnife.bind(this, agregarClienteDialog);

        builder.setView(agregarClienteDialog);

        progress = new ProgressDialog(getContext());

        dialog = builder.create();

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        proveedorPresenter = new ProveedorPresenter(getContext(), this);

        if(proveedor != null){
            txtTitulo.setText(proveedor.getNombre());
            nitProveedor.setText(proveedor.getNit());
            direccionProveedor.setText(proveedor.getDireccion());
            ciudadProveedor.setText(proveedor.getCiudad());

            String telefono = proveedor.getTelefono();

            if(proveedor.getTelefono_respaldo() != null && !proveedor.getTelefono_respaldo().equals("")){
                telefono += " - " + proveedor.getTelefono_respaldo();
            }

            telefonoProveedor.setText(telefono);

            if(proveedor.getDescripcion() != null && !proveedor.getDescripcion().equals("")){
                llDescripcionProveedor.setVisibility(View.VISIBLE);
                descripcionProveedor.setText(proveedor.getDescripcion());
            } else {
                llDescripcionProveedor.setVisibility(View.GONE);
            }

        }

        if(tipoMensaje!= null && !tipoMensaje.equals("")){
            mostrarMensaje(tipoMensaje);
        } else {
            llBotonesDetalleProveedor.setVisibility(View.VISIBLE);
            llMensajeDetalleProveedor.setVisibility(View.GONE);
        }

        return dialog;
    }

    /**
     * --------------================ METODOS =================--------------------------------
     */

    /**
     * -------------- METODOS ONCLICK BUTTERKNIFE --------------------------------
     */

    @OnClick(R.id.llBtnAtrasDetalleProveedor)
    void OnClickBtnAtras(){
        System.out.println("Atras");
        dialog.dismiss();
    }

    @OnClick(R.id.llBtnEliminarDetalleProveedor)
    void OnClickBtnEliminar(){
        System.out.println("Eliminar");
        mostrarDialogoConfirmacion();
    }

    @OnClick(R.id.llBtnActualizarDetalleProveedor)
    void OnClickBtnActualizar(){
        System.out.println("Actualizar");
        AgregarCrearProveedorDialogFragment agregarFragment = AgregarCrearProveedorDialogFragment.newInstance(activity, fragmentManager, proveedor, "Agregar Proveedor a la Inversion", this);
        agregarFragment.show(fragmentManager, "AgregarProveedor");
        Fragment fragment = fragmentManager.findFragmentByTag("AgregarCliente");
        if (fragment != null) {
            fragmentManager.beginTransaction().remove(fragment).commit();
        }
    }

    /**
     * -------------- METODOS INTERFACE IDetalleProveedorDialogFragmentView --------------------------------
     */

    /**
     * @Autor RagooS
     * @fecha 01/10/2022
     * @Descripccion Metodo permite mostrar el progressBar
     */
    @Override
    public void showProgress(String mensaje) {
        progress = ProgressDialog.show(getContext(), mensaje,null);
    }

    /**
     * @Autor RagooS
     * @fecha 01/10/2022
     * @Descripccion Metodo permite ocultar el progressBar
     */
    @Override
    public void hideProgress() {
        progress.dismiss();
    }

    /**
     * @Autor RagooS
     * @fecha 01/10/2022
     * @Descripccion Metodo permite ocultar el dialogo
     */
    @Override
    public void dismissDialog() {
        dialog.dismiss();
    }

    /**
     * @Autor RagooS
     * @fecha 01/10/2022
     * @Descripccion Metodo permite mostrar mensaje depues de accion
     */
    @Override
    public void mostrarMensaje(String tipoMensaje) {

        boolean exitoso = false;

        llBotonesDetalleProveedor.setVisibility(View.GONE);
        llMensajeDetalleProveedor.setVisibility(View.VISIBLE);

        if(tipoMensaje.equals("ERROR_ELIMINACION")){
            llImageUsuario.setBackgroundResource(R.drawable.ic_proveedor_advertencia);
            txtMensaje.setText("ALVERTENCIA! No se elimino.");
            exitoso = false;
        } else if(tipoMensaje.equals("EXITO_ELIMINACION")){
            llImageUsuario.setBackgroundResource(R.drawable.ic_proveedor_eliminado);
            txtMensaje.setText("Se Elimino Con Exito.");
            exitoso = true;
        } else if(tipoMensaje.equals("ERROR_CREACION")){
            llImageUsuario.setBackgroundResource(R.drawable.ic_proveedor_advertencia);
            txtMensaje.setText("ALVERTENCIA! No se registro.");
            exitoso = false;
        } else if(tipoMensaje.equals("EXITO_CREACION")) {
            llImageUsuario.setBackgroundResource(R.drawable.ic_proveedor_exito);
            txtMensaje.setText("Creado Con Éxito!");
            exitoso = true;
        } else if(tipoMensaje.equals("EXITO_ACTUALIZACION")) {
            llImageUsuario.setBackgroundResource(R.drawable.ic_proveedor_exito);
            txtMensaje.setText("Actualizado Con Éxito!");
            exitoso = true;
        }

        boolean finalExitoso = exitoso;

        Thread timer = new Thread() {
            public void run(){
                try {
                    sleep(2000);
                    dismissDialog();
                    /*if(iClienteBusquedaFragmentView != null && finalExitoso){
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                iClienteBusquedaFragmentView.actualizarDatos();
                            }
                        });
                    }*/
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        };
        timer.start();
    }

    /**
     * @Autor RagooS
     * @fecha 01/10/2022
     * @Descripccion Metodo permite llamar al metodo que permite la eliminacion del proveedor
     */
    @Override
    public void eliminarProveedor() {
        proveedorPresenter.eliminarProveedor(proveedor);
    }

    /**
     * -------------- METODOS PROPIOS --------------------------------
     */

    /**
     * @Autor RagooS
     * @fecha 01/10/2022
     * @Descripccion Metodo permite mostrar alerta de confirmacion antes de realizar una accion
     */
    private void mostrarDialogoConfirmacion() {
        ConfirmarDialogFragment confirmarDialogFragment = ConfirmarDialogFragment.newInstance("Eliminar Proveedor", "Esta seguro que desea eliminar el proveedor...", "ALVERTENCIA", this);
        confirmarDialogFragment.show(getFragmentManager(), "EliminarCliente");
        android.app.Fragment fragment = activity.getFragmentManager().findFragmentByTag("EliminarCliente");
        if (fragment != null) {
            activity.getFragmentManager().beginTransaction().remove(fragment).commit();
        }
    }
}
