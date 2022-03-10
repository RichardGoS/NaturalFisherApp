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
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.naturalfisherapp.R;
import com.example.naturalfisherapp.view.interfaces.adapter.IItemVentaHolderView;
import com.example.naturalfisherapp.view.interfaces.dialog.ICambiarPrecioItemProductoVentaDialogFragment;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * de RagooS
 * Autor: Richard Gomez O.
 * Para: EmpresaDevelopers.Frontend.NaturalFisher
 * Fecha: 27/02/2021
 */

public class CambiarPrecioItemProductoVentaDialogFragment extends DialogFragment implements ICambiarPrecioItemProductoVentaDialogFragment {

    private Dialog dialog;
    private ProgressDialog progress;
    private String titulo;
    private Activity activity;
    private IItemVentaHolderView iItemVentaHolderView;

    @BindView(R.id.txtTitulo)
    TextView txtTitulo;

    @BindView(R.id.edtPrecioProducto)
    EditText edtPrecioProducto;

    public static CambiarPrecioItemProductoVentaDialogFragment newInstance(String titulo, Activity activity, IItemVentaHolderView iItemVentaHolderView){
        CambiarPrecioItemProductoVentaDialogFragment cambiarPrecio = new CambiarPrecioItemProductoVentaDialogFragment();
        cambiarPrecio.activity = activity;
        cambiarPrecio.titulo = titulo;
        cambiarPrecio.iItemVentaHolderView = iItemVentaHolderView;
        return cambiarPrecio;
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
        View agregarClienteDialog = inflater.inflate(R.layout.dialog_fragment_cambiar_precio, null);

        ButterKnife.bind(this, agregarClienteDialog);

        builder.setView(agregarClienteDialog);

        txtTitulo.setText(titulo);

        progress = new ProgressDialog(getContext());

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
        System.out.println("Cancelar");
        dismissDialog();
    }

    @OnClick(R.id.btnAceptar)
    void onClickbtnAceptar(){

        System.out.println("Aceptar");

        if(!edtPrecioProducto.getText().toString().equals("")){
            if(iItemVentaHolderView != null){
                iItemVentaHolderView.cambiarPrecio(edtPrecioProducto.getText().toString());
                dismissDialog();
            }
        } else {
            System.out.println("El Campo Precio Esta Vacio");
        }

    }



    /**
     * -------------- METODOS INTERFACE ICambiarPrecioItemProductoVentaDialogFragment --------------------------------
     */

    /**
     * @Autor RagooS
     * @Descripccion Metodo permite mostrar barra de carga
     * @Fecha 28/02/22
     */
    @Override
    public void showProgress(String mensaje) {
        progress= ProgressDialog.show(getContext(), mensaje,null);
    }

    /**
     * @Autor RagooS
     * @Descripccion Metodo permite ocultar barra de carga
     * @Fecha 28/02/22
     */
    @Override
    public void hideProgress() {
        progress.dismiss();
    }

    /**
     * @Autor RagooS
     * @Descripccion Metodo permite ocultar dialogo
     * @Fecha 28/02/22
     */
    @Override
    public void dismissDialog() {
        dialog.dismiss();
    }


}
