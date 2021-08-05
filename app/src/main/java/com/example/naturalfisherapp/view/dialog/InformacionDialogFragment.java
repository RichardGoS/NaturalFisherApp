package com.example.naturalfisherapp.view.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.naturalfisherapp.R;
import com.example.naturalfisherapp.view.interfaces.IDetalleRegistroVentaFragmentView;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * de RagooS
 * Autor: Richard Gomez O.
 * Para: EmpresaDevelopers.Frontend.NaturalFisher
 * Fecha: 28/07/2021
 */

public class InformacionDialogFragment extends DialogFragment {

    private Dialog dialog;
    private String tipo;
    private String informacion;
    private IDetalleRegistroVentaFragmentView detalleRegistroVentaFragmentView;

    @BindView(R.id.imvIcono)
    ImageView imvIcono;

    @BindView(R.id.txtInfo)
    TextView txtInfo;

    public static InformacionDialogFragment newInstance(String tipo, String informacion, IDetalleRegistroVentaFragmentView detalleRegistroVentaFragmentView){
        InformacionDialogFragment informacionDialogFragment = new InformacionDialogFragment();
        informacionDialogFragment.tipo = tipo;
        informacionDialogFragment.informacion = informacion;
        informacionDialogFragment.detalleRegistroVentaFragmentView = detalleRegistroVentaFragmentView;
        return informacionDialogFragment;
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
        View agregarProductoDialog = inflater.inflate(R.layout.dialog_fragment_information, null);

        ButterKnife.bind(this, agregarProductoDialog);

        builder.setView(agregarProductoDialog);

        dialog = builder.create();

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        txtInfo.setText(informacion);

        Thread timer = new Thread() {
            public void run(){
                try {
                    sleep(1000);
                    dialog.dismiss();

                    if(detalleRegistroVentaFragmentView != null){
                        detalleRegistroVentaFragmentView.goToVentaPrinsipalActivity();
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        };
        timer.start();

        return dialog;
    }
}
