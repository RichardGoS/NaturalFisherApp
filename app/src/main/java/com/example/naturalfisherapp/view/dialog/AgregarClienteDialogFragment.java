package com.example.naturalfisherapp.view.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.naturalfisherapp.R;
import com.example.naturalfisherapp.data.models.Cliente;
import com.example.naturalfisherapp.presenter.activities.ClientePresenter;
import com.example.naturalfisherapp.presenter.interfaces.IClientePresenter;
import com.example.naturalfisherapp.utilidades.InformacionSession;
import com.example.naturalfisherapp.view.activities.VentaPrinsipalActivity;
import com.example.naturalfisherapp.view.interfaces.dialog.IAgregarClienteDialogFragmentView;
import com.example.naturalfisherapp.view.interfaces.adapter.IVentaRegistroHolderView;
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
 * Fecha: 14/07/2021
 */

public class AgregarClienteDialogFragment extends DialogFragment implements IAgregarClienteDialogFragmentView {

    private Dialog dialog;
    private IClientePresenter clientePresenter;
    private IClienteBusquedaFragmentView iClienteBusquedaFragmentView;
    private IDetalleClienteDialogFragment iDetalleClienteDialogFragment;
    private ProgressDialog progress;
    private Activity activity;
    private String strNombreCliente = "";
    private Long id;
    private Cliente clienteSeleccionado;
    private FragmentManager fragmentManager;
    private String titulo;
    private Cliente clienteActualizar;

    private IVentaRegistroHolderView iVentaRegistroHolderView;
    private Cliente clienteCambiar;
    private boolean cambiarCliente;

    @BindView(R.id.txtTitulo)
    TextView txtTitulo;

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

    /**
     * Fase 4 Tarea 2
     * @author RagooS
     * @fecha 30/07/2022
     * @descripcion Se agregan los campos de la vista para el control de la misma
     */

    @BindView(R.id.llBtnPlusDireccion)
    LinearLayout llBtnPlusDireccion;

    @BindView(R.id.llBtnPlusTelefono)
    LinearLayout llBtnPlusTelefono;

    @BindView(R.id.llBtnMenosTelefonoRespaldo)
    LinearLayout llBtnMenosTelefonoRespaldo;

    @BindView(R.id.llBtnMenosDireccionRespaldo)
    LinearLayout llBtnMenosDireccionRespaldo;

    @BindView(R.id.llTelefonoRespaldo)
    LinearLayout llTelefonoRespaldo;

    @BindView(R.id.llDireccionRespaldo)
    LinearLayout llDireccionRespaldo;

    @BindView(R.id.edtDireccionRespaldoCliente)
    EditText edtDireccionRespaldoCliente;

    @BindView(R.id.edtTelefonoRespaldoCliente)
    EditText edtTelefonoRespaldoCliente;


    public static AgregarClienteDialogFragment newInstance(Activity activity, FragmentManager fragmentManager, String titulo){
        AgregarClienteDialogFragment agregarClienteDialogFragment = new AgregarClienteDialogFragment();
        agregarClienteDialogFragment.fragmentManager = fragmentManager;
        agregarClienteDialogFragment.titulo = titulo;
        agregarClienteDialogFragment.activity = activity;
        return agregarClienteDialogFragment;
    }

    public static AgregarClienteDialogFragment newInstance(Activity activity, FragmentManager fragmentManager, String titulo, IClienteBusquedaFragmentView iClienteBusquedaFragmentView){
        AgregarClienteDialogFragment agregarClienteDialogFragment = new AgregarClienteDialogFragment();
        agregarClienteDialogFragment.fragmentManager = fragmentManager;
        agregarClienteDialogFragment.titulo = titulo;
        agregarClienteDialogFragment.iClienteBusquedaFragmentView = iClienteBusquedaFragmentView;
        agregarClienteDialogFragment.activity = activity;
        return agregarClienteDialogFragment;
    }

    public static AgregarClienteDialogFragment newInstance(Activity activity, Cliente clienteActualizar, String titulo, IDetalleClienteDialogFragment iDetalleClienteDialogFragment){
        AgregarClienteDialogFragment agregarClienteDialogFragment = new AgregarClienteDialogFragment();
        agregarClienteDialogFragment.titulo = titulo;
        agregarClienteDialogFragment.activity = activity;
        agregarClienteDialogFragment.clienteActualizar = clienteActualizar;
        agregarClienteDialogFragment.iDetalleClienteDialogFragment = iDetalleClienteDialogFragment;
        return agregarClienteDialogFragment;
    }

    public static AgregarClienteDialogFragment newInstance(Activity activity, String titulo, Cliente clienteCambiar, IVentaRegistroHolderView iVentaRegistroHolderView){
        AgregarClienteDialogFragment agregarClienteDialogFragment = new AgregarClienteDialogFragment();
        agregarClienteDialogFragment.activity = activity;
        agregarClienteDialogFragment.clienteCambiar = clienteCambiar;
        agregarClienteDialogFragment.iVentaRegistroHolderView = iVentaRegistroHolderView;
        agregarClienteDialogFragment.titulo = titulo;
        agregarClienteDialogFragment.cambiarCliente = true;
        agregarClienteDialogFragment.clienteSeleccionado = clienteCambiar;
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

        txtTitulo.setText(titulo);

        progress = new ProgressDialog(getContext());

        if ( iClienteBusquedaFragmentView != null) {
            clientePresenter = new ClientePresenter(getContext(), this, iClienteBusquedaFragmentView);

        } else if(iDetalleClienteDialogFragment != null){
            clientePresenter = new ClientePresenter(getContext(), iDetalleClienteDialogFragment, this);
        } else if(iVentaRegistroHolderView != null){
            clientePresenter = new ClientePresenter(getContext(), iVentaRegistroHolderView, this);
        } else {
            clientePresenter = new ClientePresenter(getContext(), this);
        }

        if(clienteActualizar != null){
            edtNombreCliente.setText(clienteActualizar.getNombre());
            edtDireccionCliente.setText(clienteActualizar.getDireccion());
            edtTelefonoCliente.setText(clienteActualizar.getTelefono());

            if(clienteActualizar.getTelefono_respaldo() != null && !clienteActualizar.getTelefono_respaldo().equals("")){
                edtTelefonoRespaldoCliente.setText(clienteActualizar.getTelefono_respaldo());
                llTelefonoRespaldo.setVisibility(View.VISIBLE);
            } else {
                edtTelefonoRespaldoCliente.setText("");
                llTelefonoRespaldo.setVisibility(View.GONE);
            }

            if(clienteActualizar.getDireccion_respaldo() != null && !clienteActualizar.getDireccion_respaldo().equals("")){
                edtDireccionRespaldoCliente.setText(clienteActualizar.getDireccion_respaldo());
                llDireccionRespaldo.setVisibility(View.VISIBLE);
            } else {
                edtDireccionRespaldoCliente.setText("");
                llDireccionRespaldo.setVisibility(View.GONE);
            }
        } else {

            if(clienteCambiar != null){
                edtNombreCliente.setText(clienteCambiar.getNombre());
                edtDireccionCliente.setText(clienteCambiar.getDireccion());
                edtTelefonoCliente.setText(clienteCambiar.getTelefono());

                edtTelefonoRespaldoCliente.setText(clienteCambiar.getTelefono_respaldo() != null && !clienteCambiar.getTelefono_respaldo().equals("") ? clienteCambiar.getTelefono_respaldo() : "");
                edtDireccionRespaldoCliente.setText(clienteCambiar.getDireccion_respaldo() != null && !clienteCambiar.getDireccion_respaldo().equals("") ? clienteCambiar.getDireccion_respaldo() : "");
            }

            clientePresenter.consultarClientes();
        }


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

                        edtDireccionRespaldoCliente.setText(clienteSeleccionado.getDireccion_respaldo());
                        edtTelefonoRespaldoCliente.setText(clienteSeleccionado.getTelefono_respaldo());
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
        //System.out.println("Cancelar");
        dismissDialog();
    }

    @OnClick(R.id.btnAgregarCliente)
    void onClickAgregarCliente(){
        //System.out.println("Aceptar");

        if(validarCampos()){
            //clientePresenter.guardarCliente();
            Cliente cliente = extraerDatos();
            if(cliente != null){
                if(clienteSeleccionado != null){
                    if(cambiarCliente){
                        dismissDialog();
                        iVentaRegistroHolderView.setCliente(clienteSeleccionado);
                    } else {
                        goToVentaDetalle(clienteSeleccionado);
                    }
                } else{
                    clientePresenter.guardarCliente(cliente);
                }
            }

        } else {
            //System.out.println("Campos Vacios..");
        }
    }

    /**
     * Fase 4 Tarea 2
     * @author RagooS
     * @fecha 30/07/2022
     * @descripcion Se agregan los metodos onClick para el manejo de los botodes para la informacion de respaldo.
     */

    @OnClick(R.id.llBtnPlusDireccion)
    void onClickLlBtnPlusDireccion(){
        System.out.println("Plus Direccion");

        llDireccionRespaldo.setVisibility(View.VISIBLE);
        llBtnPlusDireccion.setVisibility(View.GONE);

    }

    @OnClick(R.id.llBtnPlusTelefono)
    void onClickLlBtnPlusTelefono(){
        System.out.println("Plus Telefono");

        llTelefonoRespaldo.setVisibility(View.VISIBLE);
        llBtnPlusTelefono.setVisibility(View.GONE);

    }

    @OnClick(R.id.llBtnMenosDireccionRespaldo)
    void onClickLlBtnMenosDireccionRespaldo(){
        System.out.println("Menos Direccion");

        llDireccionRespaldo.setVisibility(View.GONE);
        llBtnPlusDireccion.setVisibility(View.VISIBLE);

    }

    @OnClick(R.id.llBtnMenosTelefonoRespaldo)
    void onClickLlBtnMenosTelefonoRespaldo(){
        System.out.println("Menos Telefono");

        llTelefonoRespaldo.setVisibility(View.GONE);
        llBtnPlusTelefono.setVisibility(View.VISIBLE);

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

        if(!edtNombreCliente.getText().toString().equals("")){
            if(!edtDireccionCliente.getText().toString().equals("")){
                if(!edtTelefonoCliente.getText().toString().equals("")){
                    validado = true;
                } else {
                    System.out.println("Campo Telefono Vacio");
                }
            } else {
                System.out.println("Campo Direccion Vacio");
            }
        } else {
            System.out.println("Campo Nombre Vacio");
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

        cliente.setEstado("ACTIVO");

        if(clienteActualizar != null){
            cliente.setId(clienteActualizar.getId());
        }

        if(edtNombreCliente.getText().toString().contains("-")){
            strNombreCliente = eliminarIdNombre(edtNombreCliente.getText().toString());
            cliente.setNombre(strNombreCliente);
        } else{
            cliente.setNombre(edtNombreCliente.getText().toString());
        }

        cliente.setDireccion(edtDireccionCliente.getText().toString());
        cliente.setTelefono(edtTelefonoCliente.getText().toString());
        cliente.setDireccion_respaldo(edtDireccionRespaldoCliente.getText().toString());
        cliente.setTelefono_respaldo(edtTelefonoRespaldoCliente.getText().toString());


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
     * -------------- METODOS INTERFACE IAgregarClienteDialogFragmentView --------------------------------
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
        dismissDialog();

        InformacionSession.getInstance().setClienteNewVenta(client);

        Intent intent = new Intent(getContext(), VentaPrinsipalActivity.class);
        intent.putExtra("vender", true);
        startActivity(intent);
        getActivity().finish();

    }

    @Override
    public void mostrarMensaje(Cliente cliente, String tipoMensaje, boolean isVenta) {

        if(cliente != null){
            DetalleClienteDialogFragment detalleClienteDialogFragment = DetalleClienteDialogFragment.newInstance(activity, cliente, this, tipoMensaje);
            detalleClienteDialogFragment.show(fragmentManager, "DetalleCliente");
            Fragment fragment = fragmentManager.findFragmentByTag("DetalleCliente");
            if (fragment != null) {
                fragmentManager.beginTransaction().remove(fragment).commit();
            }
        }

        dismissDialog();

        if(!tipoMensaje.contains("ALVERTENCIA") && isVenta){
            goToVentaDetalle(cliente);
        }
    }

}
