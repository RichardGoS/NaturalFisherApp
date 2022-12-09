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
import com.example.naturalfisherapp.view.interfaces.activities.IConfiguracionView;
import com.example.naturalfisherapp.view.interfaces.fragment.IDetalleInversionFragmentView;
import com.example.naturalfisherapp.view.interfaces.fragment.IDetalleRegistroVentaFragmentView;
import com.example.naturalfisherapp.view.interfaces.fragment.IInversionBusquedaFragmentView;
import com.example.naturalfisherapp.view.interfaces.fragment.IProveedorBusquedaFragmentView;

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
    private IConfiguracionView configuracionView;
    private IInversionBusquedaFragmentView inversionBusquedaFragmentView;
    private IProveedorBusquedaFragmentView proveedorBusquedaFragmentView;
    private IDetalleInversionFragmentView detalleInversionFragmentView;

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

    public static InformacionDialogFragment newInstance(String tipo, String informacion, IConfiguracionView configuracionView){
        InformacionDialogFragment informacionDialogFragment = new InformacionDialogFragment();
        informacionDialogFragment.tipo = tipo;
        informacionDialogFragment.informacion = informacion;
        informacionDialogFragment.configuracionView = configuracionView;
        return informacionDialogFragment;
    }

    public static InformacionDialogFragment newInstance(String tipo, String informacion, IInversionBusquedaFragmentView inversionBusquedaFragmentView){
        InformacionDialogFragment informacionDialogFragment = new InformacionDialogFragment();
        informacionDialogFragment.tipo = tipo;
        informacionDialogFragment.informacion = informacion;
        informacionDialogFragment.inversionBusquedaFragmentView = inversionBusquedaFragmentView;
        return informacionDialogFragment;
    }

    public static InformacionDialogFragment newInstance(String tipo, String informacion, IProveedorBusquedaFragmentView proveedorBusquedaFragmentView){
        InformacionDialogFragment informacionDialogFragment = new InformacionDialogFragment();
        informacionDialogFragment.tipo = tipo;
        informacionDialogFragment.informacion = informacion;
        informacionDialogFragment.proveedorBusquedaFragmentView = proveedorBusquedaFragmentView;
        return informacionDialogFragment;
    }

    public static InformacionDialogFragment newInstance(String tipo, String informacion, IDetalleInversionFragmentView detalleInversionFragmentView){
        InformacionDialogFragment informacionDialogFragment = new InformacionDialogFragment();
        informacionDialogFragment.tipo = tipo;
        informacionDialogFragment.informacion = informacion;
        informacionDialogFragment.detalleInversionFragmentView = detalleInversionFragmentView;
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

        if(tipo.equals("Error")){
            imvIcono.setImageDrawable(getResources().getDrawable(R.drawable.ic_error));
        } else {
            imvIcono.setImageDrawable(getResources().getDrawable(R.drawable.ic_confimar_mejorado));
        }

        txtInfo.setText(informacion);

        Thread timer = new Thread() {
            public void run(){
                try {
                    sleep(1000);
                    dialog.dismiss();

                    if(!tipo.equals("Error")){
                        if(detalleRegistroVentaFragmentView != null){
                            detalleRegistroVentaFragmentView.goToVentaPrinsipalActivity();
                        } else if(configuracionView != null){
                            configuracionView.goToLoginActivity();
                        } else if(detalleInversionFragmentView != null){
                            detalleInversionFragmentView.goToInversionActivity();
                        }
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
