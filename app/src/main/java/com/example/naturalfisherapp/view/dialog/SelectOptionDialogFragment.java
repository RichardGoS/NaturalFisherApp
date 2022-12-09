package com.example.naturalfisherapp.view.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.naturalfisherapp.R;
import com.example.naturalfisherapp.utilidades.EnumVariables;
import com.example.naturalfisherapp.view.adapter.ItemSelectOptionAdapter;
import com.example.naturalfisherapp.view.interfaces.adapter.IVentaRegistroHolderView;
import com.example.naturalfisherapp.view.interfaces.dialog.ISelectOptionDialogFragment;

import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * de RagooS
 * Autor: Richard Gomez O.
 * Para: EmpresaDevelopers.Frontend.NaturalFisher
 * Fase 4 Tarea 2
 * Fecha: 30/07/2022
 */

public class SelectOptionDialogFragment extends DialogFragment implements ISelectOptionDialogFragment {

    private Dialog dialog;
    private FragmentManager fragmentManager;
    private IVentaRegistroHolderView ventaRegistroHolderView;
    private String titulo;
    private List<String> listOpciones;
    private LinearLayoutManager linearLayoutManager;
    private int posicionSeleccionada;
    private String tipoOpcion;

    @BindView(R.id.txtTitulo)
    TextView txtTitulo;

    @BindView(R.id.efRvOpciones)
    RecyclerView efRvOpciones;

    @BindView(R.id.edtOpcionSeleccionada)
    EditText edtOpcionSeleccionada;

    @BindView(R.id.btnCancelar)
    LinearLayout btnCancelar;

    @BindView(R.id.btnAceptar)
    LinearLayout btnAceptar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_FRAME, 0);
    }

    public static SelectOptionDialogFragment newInstance(FragmentManager fragmentManager, String titulo, String tipoOpcion, List<String> listOpciones, int posicionSeleccionada, IVentaRegistroHolderView ventaRegistroHolderView){
        SelectOptionDialogFragment selectOptionDialogFragment = new SelectOptionDialogFragment();
        selectOptionDialogFragment.fragmentManager = fragmentManager;
        selectOptionDialogFragment.titulo = titulo;
        selectOptionDialogFragment.listOpciones = listOpciones;
        selectOptionDialogFragment.ventaRegistroHolderView = ventaRegistroHolderView;
        selectOptionDialogFragment.posicionSeleccionada = posicionSeleccionada;
        selectOptionDialogFragment.tipoOpcion = tipoOpcion;
        return selectOptionDialogFragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(getActivity().LAYOUT_INFLATER_SERVICE);
        View seleccionarOpcionDialogFragment = inflater.inflate(R.layout.dialog_fragment_select_option, null);

        ButterKnife.bind(this, seleccionarOpcionDialogFragment);

        builder.setView(seleccionarOpcionDialogFragment);

        dialog = builder.create();

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        txtTitulo.setText(titulo);

        if(tipoOpcion.equals(EnumVariables.LETRAS.getValor())){
            edtOpcionSeleccionada.setInputType(InputType.TYPE_CLASS_TEXT);
        } else if(tipoOpcion.equals(EnumVariables.NUMBER.getValor())){
            edtOpcionSeleccionada.setInputType(InputType.TYPE_CLASS_NUMBER);
        }

        if(listOpciones != null && !listOpciones.isEmpty()){
            cargarAdapter(listOpciones, posicionSeleccionada);
            edtOpcionSeleccionada.setText(listOpciones.get(posicionSeleccionada));
        }

        return dialog;
    }

    /**
     * --------------================ METODOS =================--------------------------------
     */

    /**
     * -------------- METODOS ONCLICK BUTTERKNIFE --------------------------------
     */
    @OnClick(R.id.btnAceptar)
    void onClickBtnAceptar(){
        if(!edtOpcionSeleccionada.getText().toString().equals("")){
            if(ventaRegistroHolderView != null){
                if(tipoOpcion.equals(EnumVariables.LETRAS.getValor() ) && titulo.contains("Direccion")){
                    ventaRegistroHolderView.setDireccion(edtOpcionSeleccionada.getText().toString(), posicionSeleccionada);
                } else if(tipoOpcion.equals(EnumVariables.NUMBER.getValor() ) && titulo.contains("Telefono")){
                    ventaRegistroHolderView.setTelefono(edtOpcionSeleccionada.getText().toString(), posicionSeleccionada);
                }
            }
            dismissDialog();
        }
    }

    @OnClick(R.id.btnCancelar)
    void onClickBtnCancelar(){
        dismissDialog();
    }

    @OnTextChanged(R.id.edtOpcionSeleccionada)
    void onTextChangetEdtOpcionSeleccionada(){
        System.out.println("TextChanged");

        int i = 0;
        for(String opcion:listOpciones){
            if(opcion.equals(edtOpcionSeleccionada.getText().toString())){
                posicionSeleccionada = i;
                break;
            } else {
                posicionSeleccionada = -1;
            }
            i++;
        }

        cargarAdapter(listOpciones, posicionSeleccionada);
    }


    /**
     * -------------- METODOS INTERFACE IVentaRegistroHolderView --------------------------------
     */

    /**
     * Fase 4 Tarea 2
     * @author RagooS
     * @fecha 30/07/2022
     * @descripcion Metodo permite cargar el recyclersView
     * @param listOptions
     */
    @Override
    public void cargarAdapter(List<String> listOptions, int posicionSeleccionada) {
        linearLayoutManager = new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false);
        ItemSelectOptionAdapter adapter = new ItemSelectOptionAdapter(getContext(),getActivity().getSupportFragmentManager(), getActivity(), listOptions, posicionSeleccionada, this);
        efRvOpciones.setAdapter(adapter);
        efRvOpciones.setLayoutManager(linearLayoutManager);
    }

    /**
     * Fase 4 Tarea 2
     * @Autor RagooS
     * @Descripccion Metodo permite ocultar dialogo
     * @Fecha 31/07/2022
     */
    @Override
    public void dismissDialog() {
        dialog.dismiss();
    }

    /**
     * Fase 4 Tarea 2
     * @Autor RagooS
     * @Descripccion Metodo permite cambiar la opcion seleccionada
     * @Fecha 31/07/2022
     */
    @Override
    public void cambiarOpcion(String opcion, int posicionSeleccionada) {
        edtOpcionSeleccionada.setText(opcion);
        cargarAdapter(listOpciones, posicionSeleccionada);
        this.posicionSeleccionada = posicionSeleccionada;
    }
}
