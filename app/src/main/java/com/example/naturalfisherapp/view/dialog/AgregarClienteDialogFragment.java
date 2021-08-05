package com.example.naturalfisherapp.view.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.naturalfisherapp.R;
import com.example.naturalfisherapp.data.models.Cliente;
import com.example.naturalfisherapp.data.models.Producto;
import com.example.naturalfisherapp.presenter.activities.ClientePresenter;
import com.example.naturalfisherapp.presenter.interfaces.IClientePresenter;
import com.example.naturalfisherapp.utilidades.InformacionSession;
import com.example.naturalfisherapp.view.adapter.ItemAutocompleteTextViewAdapter;
import com.example.naturalfisherapp.view.fragment.DetalleRegistroVentaFragment;
import com.example.naturalfisherapp.view.interfaces.IAgregarClienteDialogFragmentView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;

/**
 * de RagooS
 * Autor: Richard Gomez O.
 * Para: EmpresaDevelopers.Frontend.NaturalFisher
 * Fecha: 14/07/2021
 */

public class AgregarClienteDialogFragment extends DialogFragment implements IAgregarClienteDialogFragmentView {

    private Dialog dialog;
    private IClientePresenter clientePresenter;
    private ProgressDialog progress;
    private String strNombreCliente = "";
    private Long id;
    private Cliente clienteSeleccionado;
    private FragmentManager fragmentManager;

    @BindView(R.id.edtNombreCliente)
    AutoCompleteTextView edtNombreCliente;

    @BindView(R.id.edtDireccionCliente)
    EditText edtDireccionCliente;

    @BindView(R.id.edtTelefonoCliente)
    EditText edtTelefonoCliente;

    @BindView(R.id.btnCancelar)
    LinearLayout btnCancelar;

    @BindView(R.id.btnAgregarCliente)
    LinearLayout btnAgregarCliente;


    public static AgregarClienteDialogFragment newInstance(FragmentManager fragmentManager){
        AgregarClienteDialogFragment agregarClienteDialogFragment = new AgregarClienteDialogFragment();
        agregarClienteDialogFragment.fragmentManager = fragmentManager;
        return agregarClienteDialogFragment;
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
        View agregarClienteDialog = inflater.inflate(R.layout.dialog_fragment_agregar_cliente, null);

        ButterKnife.bind(this, agregarClienteDialog);

        builder.setView(agregarClienteDialog);

        progress = new ProgressDialog(getContext());

        clientePresenter = new ClientePresenter(getContext(), this);

        clientePresenter.consultarClientes();

        dialog = builder.create();

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        edtNombreCliente.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("Autocomplete...." + " - " + position + " - " + id + " - " + parent.getItemAtPosition(position));

                if(parent.getItemAtPosition(position).toString().contains("-")){
                    strNombreCliente = eliminarIdNombre(parent.getItemAtPosition(position).toString());

                    id = extraerIdClienteAutocomplete(parent.getItemAtPosition(position).toString());
                    System.out.println("Autoc" + " - " + id );
                    clienteSeleccionado = buscarClienteSeleccionado(id);

                    if(clienteSeleccionado != null){
                        edtNombreCliente.setText(clienteSeleccionado.getNombre());
                        edtDireccionCliente.setText(clienteSeleccionado.getDireccion());
                        edtTelefonoCliente.setText(clienteSeleccionado.getTelefono());
                    }

                } else {
                    edtNombreCliente.setText(parent.getItemAtPosition(position).toString());
                }



            }
        });


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

    @OnClick(R.id.btnAgregarCliente)
    void onClickAgregarCliente(){
        System.out.println("Aceptar");

        if(validarCampos()){
            //clientePresenter.guardarCliente();
            Cliente cliente = extraerDatos();
            if(cliente != null){
                if(clienteSeleccionado != null){
                    goToVentaDetalle(clienteSeleccionado);
                } else{
                    clientePresenter.guardarCliente(cliente);
                }

            }

        } else {
            System.out.println("Campos Vacios..");
        }
    }

    /*@OnItemClick(R.id.edtNombreCliente)
    void onClickAutoNombreCliente(){
        System.out.println("Autocomplete");
    }*/

    /**
     * -------------- METODOS PROPIOS --------------------------------
     */

    /**
     * @Autor RagooS
     * @Descripccion Metodo permite validar que los campor no esten vacios
     * @Fecha 16/07/21
     */
    private boolean validarCampos() {
        boolean validado = false;

        if(edtNombreCliente.getText().equals("")){
            System.out.println("Campo Nombre Vacio");
            validado = false;
        } else if(edtDireccionCliente.getText().equals("")){
            System.out.println("Campo Direccion Vacio");
            validado = false;
        } else if(edtTelefonoCliente.getText().equals("")){
            System.out.println("Campo Telefono Vacio");
            validado = false;
        } else {
            validado = true;
        }

        return  validado;
    }

    /**
     * @Autor RagooS
     * @Descripccion Metodo permite extraer los datos de los campos para crear el cliente
     * @Fecha 16/07/21
     */
    private Cliente extraerDatos() {
        Cliente cliente = new Cliente();

        if(edtNombreCliente.getText().toString().contains("-")){
            strNombreCliente = eliminarIdNombre(edtNombreCliente.getText().toString());

            cliente.setNombre(strNombreCliente);
            cliente.setDireccion(edtDireccionCliente.getText().toString());
            cliente.setTelefono(edtTelefonoCliente.getText().toString());
        } else{
            cliente.setNombre(edtNombreCliente.getText().toString());
            cliente.setDireccion(edtDireccionCliente.getText().toString());
            cliente.setTelefono(edtTelefonoCliente.getText().toString());
        }


        return cliente;
    }

    /**
     * @Autor RagooS
     * @Descripccion Metodo permite eliminar el id del campo de texto seleccionable
     * @Fecha 17/07/21
     */
    private String eliminarIdNombre(String nombre) {

        String[] nom = nombre.split("-");

        nombre = nom[0].trim();

        return nombre;

    }

    /**
     * @Autor RagooS
     * @Descripccion Metodo permite eliminar el nombre del campo de texto seleccionable
     * @Fecha 17/07/21
     */
    private long extraerIdClienteAutocomplete(String nombre) {

        String[] nom = nombre.split("-");

        Long id = Long.parseLong(nom[1].trim());

        return id;

    }

    /**
     * @Autor RagooS
     * @Descripccion Metodo permite buscar un cliente en la lista de clientes consultados por su id
     * @Fecha 17/07/21
     */
    private Cliente buscarClienteSeleccionado(long id) {

        Cliente cliente = null;

        if(InformacionSession.getInstance().getClientesConsultados() != null && !InformacionSession.getInstance().getClientesConsultados().isEmpty()){
            for (Cliente cli: InformacionSession.getInstance().getClientesConsultados()){
                if(cli.getId() == id){
                    cliente = cli;
                    break;
                }
            }
        }

        return cliente;
    }


    /**
     * -------------- METODOS INTERFACE IClientePresenter --------------------------------
     */

    /**
     * @Autor RagooS
     * @Descripccion Metodo permite mostrar barra de carga
     * @Fecha 16/07/21
     */
    @Override
    public void showProgress(String mensaje) {
        progress= ProgressDialog.show(getContext(), mensaje,null);
    }

    /**
     * @Autor RagooS
     * @Descripccion Metodo permite ocultar barra de carga
     * @Fecha 16/07/21
     */
    @Override
    public void hideProgress() {
        progress.dismiss();
    }

    /**
     * @Autor RagooS
     * @Descripccion Metodo permite ocultar dialogo
     * @Fecha 16/07/21
     */
    @Override
    public void dismissDialog() {
        dialog.dismiss();
    }

    /**
     * @Autor RagooS
     * @Descripccion Metodo permite agregar los nombres de los clientes al Autocomplete
     * @Fecha 16/07/21
     */
    @Override
    public void agregarNombreClientesAutocomplete(String[] nombres) {

        if(nombres != null && nombres.length != 0){

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),R.layout.adapter_autocomplete_text_azul_bajo, nombres);
            edtNombreCliente.setThreshold(1);//will start working from first character
            edtNombreCliente.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView
        }

    }

    /**
     * @Autor RagooS
     * @Descripccion Metodo permite cargar el fragmen detalleVenta
     * @Fecha 17/07/21
     */
    @Override
    public void goToVentaDetalle(Cliente client) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.venta_container, DetalleRegistroVentaFragment.newInstance(getActivity(), true, client));
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

        dismissDialog();
    }
}
