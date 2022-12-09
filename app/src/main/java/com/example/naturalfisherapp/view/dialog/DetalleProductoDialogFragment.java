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
import com.example.naturalfisherapp.data.models.Producto;
import com.example.naturalfisherapp.data.models.Promocion;
import com.example.naturalfisherapp.presenter.activities.ProductoPresenter;
import com.example.naturalfisherapp.presenter.interfaces.IProductoPresenter;
import com.example.naturalfisherapp.view.interfaces.fragment.IProductoBusquedaFragmentView;
import com.example.naturalfisherapp.view.interfaces.dialog.IDetalleProductoDialogFragment;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * de RagooS
 * Autor: Richard Gomez O.
 * Para: EmpresaDevelopers.Frontend.NaturalFisher
 * Fecha: 16/01/2022
 */

public class DetalleProductoDialogFragment extends DialogFragment implements IDetalleProductoDialogFragment {

    private Dialog dialog;
    private Activity activity;
    private ProgressDialog progress;
    private String tipoMensaje;
    private IProductoPresenter iProductoPresenter;
    private Object producto;
    private IProductoBusquedaFragmentView iProductoBusquedaFragmentView;

    @BindView(R.id.txtTitulo)
    TextView txtTitulo;

    @BindView(R.id.txtMensaje)
    TextView txtMensaje;

    @BindView(R.id.precioProducto)
    TextView precioProducto;

    @BindView(R.id.descripcionProducto)
    TextView descripcionProducto;

    @BindView(R.id.llImageProducto)
    LinearLayout llImageProducto;

    @BindView(R.id.llPrecioProducto)
    LinearLayout llPrecioProducto;

    @BindView(R.id.llDescripcionProducto)
    LinearLayout llDescripcionProducto;

    @BindView(R.id.llBotonesDetalleProducto)
    LinearLayout llBotonesDetalleProducto;

    @BindView(R.id.llMensajeDetalleProducto)
    LinearLayout llMensajeDetalleProducto;

    public static DetalleProductoDialogFragment newInstance(Activity activity, Object producto, IProductoBusquedaFragmentView iProductoBusquedaFragmentView){
        DetalleProductoDialogFragment detalleProductoDialogFragment = new DetalleProductoDialogFragment();
        detalleProductoDialogFragment.activity = activity;
        detalleProductoDialogFragment.producto = producto;
        detalleProductoDialogFragment.iProductoBusquedaFragmentView = iProductoBusquedaFragmentView;

        return detalleProductoDialogFragment;
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
        View agregarClienteDialog = inflater.inflate(R.layout.dialog_fragment_detalle_producto, null);

        ButterKnife.bind(this, agregarClienteDialog);

        builder.setView(agregarClienteDialog);

        progress = new ProgressDialog(getContext());

        if(producto.getClass() == Producto.class){
            setInfoProducto((Producto)producto);
        } else if(producto.getClass() == Promocion.class){
            setInfoPromocion((Promocion)producto);
        }

        dialog = builder.create();

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        return dialog;
    }




    /**
     * --------------================ METODOS =================--------------------------------
     */

    /**
     * -------------- METODOS BUTTERKNIFE --------------------------------
     */

    @OnClick(R.id.llBtnAtrasDetalleProducto)
    void onClickBtnAtras(){
        System.out.println("Atras");
        dismissDialog();
    }

    @OnClick(R.id.llBtnEliminarDetalleProducto)
    void onClickBtnEliminar(){
        System.out.println("Eliminar");
        mostrarDialogoConfirmacion("Eliminar Producto","Esta seguro que desea eliminar el producto...","ALVERTENCIA");
    }

    @OnClick(R.id.llBtnActualizarDetalleProducto)
    void onClickBtnActualizar(){
        System.out.println("Actualizar");
        actualizarProducto();
    }

    /**
     * -------------- METODOS PROPIOS --------------------------------
     */

    /**
     * @Autor RagooS
     * @Descripccion Metodo permite mostrar alerta de confirmacion antes de realizar una accion
     * @Fecha 16/01/2021
     */
    private void mostrarDialogoConfirmacion(String titulo, String descripcion, String tipoMensaje) {

        ConfirmarDialogFragment confirmarDialogFragment = ConfirmarDialogFragment.newInstance(titulo, descripcion, tipoMensaje, this);
        confirmarDialogFragment.show(getFragmentManager(), "ConfirmarDialog");
        android.app.Fragment fragment = activity.getFragmentManager().findFragmentByTag("ConfirmarDialog");
        if (fragment != null) {
            activity.getFragmentManager().beginTransaction().remove(fragment).commit();
        }

    }

    /**
     * @Autor RagooS
     * @Descripccion Metodo permite setear la informacion del producto
     * @Fecha 06/05/2022
     */
    private void setInfoProducto(Producto producto) {
        txtTitulo.setText(producto.getNombre());

        precioProducto.setText(producto.getPrecio() + " - " + producto.getUnidad());

        if(producto.getDescripcion_unidad() != null && !producto.getDescripcion_unidad().equals("")){
            llDescripcionProducto.setVisibility(View.VISIBLE);
            descripcionProducto.setText(producto.getDescripcion_unidad());
        } else {
            llDescripcionProducto.setVisibility(View.GONE);
        }
    }

    /**
     * @Autor RagooS
     * @Descripccion Metodo permite setear la informacion de la promocion
     * @Fecha 06/05/2022
     */
    private void setInfoPromocion(Promocion promocion) {
        txtTitulo.setText(promocion.getNombre());

        precioProducto.setText(promocion.getTotal() + "");
    }

    /**
     * -------------- METODOS INTERFACE IDetalleProductoDialogFragment --------------------------------
     */

    /**
     * @Autor RagooS
     * @Descripccion Metodo permite mostrar el progressBar
     * @param mensaje variable tipo String el cual contiene el mensaje que muestra el progresbar
     * @Fecha 16/01/2021 antes
     */
    @Override
    public void showProgress(String mensaje) {
        progress = ProgressDialog.show(getContext(), mensaje,null);
    }

    /**
     * @Autor RagooS
     * @Descripccion Metodo permite ocultar el progressBar
     * @Fecha 16/01/2021 antes
     */
    @Override
    public void hideProgress() {
        progress.dismiss();
    }

    /**
     * @Autor RagooS
     * @Descripccion Metodo permite ocultar el dialogo
     * @Fecha 16/01/2021 antes
     */
    @Override
    public void dismissDialog() {
        dialog.dismiss();
    }

    @Override
    public void mostrarMensaje(String tipoMensaje) {
        boolean exitoso = false;

        llBotonesDetalleProducto.setVisibility(View.GONE);
        llMensajeDetalleProducto.setVisibility(View.VISIBLE);

        if(tipoMensaje.equals("ERROR_ELIMINACION")){
            llImageProducto.setBackgroundResource(R.drawable.ic_producto_alvertencia);
            txtMensaje.setText("ALVERTENCIA! No se elimino.");
            exitoso = false;
        } else if(tipoMensaje.equals("EXITO_ELIMINACION")){
            llImageProducto.setBackgroundResource(R.drawable.ic_producto_eliminado);
            txtMensaje.setText("Se Elimino Con Exito.");
            exitoso = true;
        } else if(tipoMensaje.equals("ERROR_CREACION")){
            llImageProducto.setBackgroundResource(R.drawable.ic_producto_alvertencia);
            txtMensaje.setText("ALVERTENCIA! No se registro.");
            exitoso = false;
        } else if(tipoMensaje.equals("EXITO_CREACION")) {
            llImageProducto.setBackgroundResource(R.drawable.ic_producto_agregado);
            txtMensaje.setText("Creado Con Éxito!");
            exitoso = true;
        } else if(tipoMensaje.equals("EXITO_ACTUALIZACION")) {
            llImageProducto.setBackgroundResource(R.drawable.ic_producto_agregado);
            txtMensaje.setText("Actualizado Con Éxito!");
            exitoso = true;
        }


         boolean finalExitoso = exitoso;

        Thread timer = new Thread() {
            public void run(){
                try {
                    sleep(2000);
                    dismissDialog();
                    if(iProductoBusquedaFragmentView != null && finalExitoso){
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                iProductoBusquedaFragmentView.actualizarDatos();
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

    @Override
    public void eliminarProducto() {
        iProductoPresenter = new ProductoPresenter(getContext(), iProductoBusquedaFragmentView, this);
        iProductoPresenter.eliminarProducto(producto);
    }

    @Override
    public void actualizarProducto() {
        if(producto.getClass() == Producto.class){
            CrearProductoDialogFragment crearProductoDialogFragment = CrearProductoDialogFragment.newInstance(activity, (Producto) producto,"Actualizar Producto", this);
            crearProductoDialogFragment.show(getChildFragmentManager(), "ActualizarProducto");
            Fragment fragment = getFragmentManager().findFragmentByTag("ActualizarProducto");
            if (fragment != null) {
                getFragmentManager().beginTransaction().remove(fragment).commit();
            }
        }

    }
}
