package com.example.naturalfisherapp.view.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
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

import com.example.naturalfisherapp.R;
import com.example.naturalfisherapp.view.interfaces.adapter.IVentaRegistroHolderView;
import com.example.naturalfisherapp.view.interfaces.dialog.IDetalleClienteDialogFragment;
import com.example.naturalfisherapp.view.interfaces.dialog.IDetalleProductoDialogFragment;
import com.example.naturalfisherapp.view.interfaces.fragment.IPromocionDetalleFragmentView;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * de RagooS
 * Autor: Richard Gomez O.
 * Para: EmpresaDevelopers.Frontend.NaturalFisher
 * Fecha: 27/07/2021
 */
public class ConfirmarDialogFragment extends DialogFragment {

    private Dialog dialog;
    private String titulo = "";
    private String descripcion = "";
    private String tipoMensaje = "";
    private IVentaRegistroHolderView ventaRegistroHolderView;
    private IDetalleClienteDialogFragment iDetalleClienteDialogFragment;
    private IDetalleProductoDialogFragment iDetalleProductoDialogFragment;
    private IPromocionDetalleFragmentView iPromocionDetalleFragmentView;

    @BindView(R.id.txtTitulo)
    TextView txtTitulo;

    @BindView(R.id.txtDescripcion)
    TextView txtDescripcion;

    @BindView(R.id.llImageConfirmacion)
    LinearLayout llImageConfirmacion;

    @BindView(R.id.btnCancelar)
    LinearLayout btnCancelar;

    @BindView(R.id.btnConfirmar)
    LinearLayout btnConfirmar;

    public static ConfirmarDialogFragment newInstance(String titulo, String descripcion, String tipoMensaje, IVentaRegistroHolderView ventaRegistroHolderView){
        ConfirmarDialogFragment confirmarDialogFragment = new ConfirmarDialogFragment();
        confirmarDialogFragment.titulo = titulo;
        confirmarDialogFragment.descripcion = descripcion;
        confirmarDialogFragment.tipoMensaje = tipoMensaje;
        confirmarDialogFragment.ventaRegistroHolderView = ventaRegistroHolderView;
        return confirmarDialogFragment;
    }

    public static ConfirmarDialogFragment newInstance(String titulo, String descripcion, String tipoMensaje, IDetalleClienteDialogFragment iDetalleClienteDialogFragment){
        ConfirmarDialogFragment confirmarDialogFragment = new ConfirmarDialogFragment();
        confirmarDialogFragment.titulo = titulo;
        confirmarDialogFragment.descripcion = descripcion;
        confirmarDialogFragment.tipoMensaje = tipoMensaje;
        confirmarDialogFragment.iDetalleClienteDialogFragment = iDetalleClienteDialogFragment;
        return confirmarDialogFragment;
    }

    public static ConfirmarDialogFragment newInstance(String titulo, String descripcion, String tipoMensaje, IDetalleProductoDialogFragment iDetalleProductoDialogFragment){
        ConfirmarDialogFragment confirmarDialogFragment = new ConfirmarDialogFragment();
        confirmarDialogFragment.titulo = titulo;
        confirmarDialogFragment.descripcion = descripcion;
        confirmarDialogFragment.tipoMensaje = tipoMensaje;
        confirmarDialogFragment.iDetalleProductoDialogFragment = iDetalleProductoDialogFragment;
        return confirmarDialogFragment;
    }

    public static ConfirmarDialogFragment newInstance(String titulo, String descripcion, String tipoMensaje, IPromocionDetalleFragmentView iPromocionDetalleFragmentView){
        ConfirmarDialogFragment confirmarDialogFragment = new ConfirmarDialogFragment();
        confirmarDialogFragment.titulo = titulo;
        confirmarDialogFragment.descripcion = descripcion;
        confirmarDialogFragment.tipoMensaje = tipoMensaje;
        confirmarDialogFragment.iPromocionDetalleFragmentView = iPromocionDetalleFragmentView;
        return confirmarDialogFragment;
    }

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_FRAME, 0);
    }

    @NonNull
    @NotNull
    @Override
    public Dialog onCreateDialog(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(getActivity().LAYOUT_INFLATER_SERVICE);
        View agregarProductoDialog = inflater.inflate(R.layout.dialog_fragment_confirmar, null);

        ButterKnife.bind(this, agregarProductoDialog);

        txtTitulo.setText(titulo);
        txtDescripcion.setText(descripcion);

        if(tipoMensaje.equals("ALVERTENCIA")){
            llImageConfirmacion.setBackgroundResource(R.drawable.ic_warning);
        } else if(tipoMensaje.equals("CONFIRMAR_VENTA")){
            llImageConfirmacion.setBackgroundResource(R.drawable.ic_confirmarcompra);
        } else if(tipoMensaje.equals("CONFIRMAR_PROMOCION")){
            llImageConfirmacion.setBackgroundResource(R.drawable.ic_mega_promocion);
        }

        builder.setView(agregarProductoDialog);

        dialog = builder.create();

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        return dialog;
    }

    /**
     * --------------================ METODOS =================--------------------------------
     */

    /**
     * -------------- METODOS ONCLICK BUTTERKNIFE --------------------------------
     */

    @OnClick(R.id.btnCancelar)
    void onClickCancelar(){
        dialog.dismiss();
    }

    @OnClick(R.id.btnConfirmar)
    void onClickConfirmar(){

        if(ventaRegistroHolderView != null){
            ventaRegistroHolderView.realizarVenta();
        } else if(iDetalleClienteDialogFragment != null){
            iDetalleClienteDialogFragment.eliminarCliente();
        } else if(iDetalleProductoDialogFragment != null){
            if(titulo.contains("Eliminar")){
                iDetalleProductoDialogFragment.eliminarProducto();
            } else if(titulo.contains("Actualizar")){

            }

        } else if(iPromocionDetalleFragmentView != null){
            iPromocionDetalleFragmentView.crearPromocion();
        }
        dialog.dismiss();
    }


}
