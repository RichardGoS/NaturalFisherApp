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
import com.example.naturalfisherapp.data.models.Proveedor;
import com.example.naturalfisherapp.presenter.activities.ProveedorPresenter;
import com.example.naturalfisherapp.presenter.interfaces.IProveedorPresenter;
import com.example.naturalfisherapp.utilidades.InformacionSession;
import com.example.naturalfisherapp.view.interfaces.activities.IInversionPrincipalActivityView;
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
 * Fecha: 26/09/2022
 */
public class AgregarCrearProveedorDialogFragment extends DialogFragment implements IAgregarCrearProveedorDialogFragmentView {

    private Dialog dialog;
    private Activity activity;
    private FragmentManager fragmentManager;
    private String titulo;
    private ProgressDialog progress;
    private String strNiProveedor = "";
    private String strNombreProveedor = "";
    private Proveedor proveedorSeleccionado;
    private IProveedorPresenter proveedorPresenter;
    private IInversionPrincipalActivityView inversionPrincipalActivityView;
    private IProveedorBusquedaFragmentView proveedorBusquedaFragmentView;
    private IDetalleProveedorDialogFragmentView detalleProveedorDialogFragmentView;
    private long idProveedorSeleccionado = 0L;
    private Proveedor proveedorActualizar = null;
    private boolean isInversion = false;

    @BindView(R.id.txtTitulo)
    TextView txtTitulo;

    @BindView(R.id.edtNitProveedor)
    AutoCompleteTextView edtNitProveedor;

    @BindView(R.id.edtNombreProveedor)
    AutoCompleteTextView edtNombreProveedor;

    @BindView(R.id.edtDireccionProveedor)
    EditText edtDireccionProveedor;

    @BindView(R.id.edtCiudadProveedor)
    EditText edtCiudadProveedor;

    @BindView(R.id.edtTelefonoProveedor)
    EditText edtTelefonoProveedor;

    @BindView(R.id.edtTelefonoRespaldoProveedor)
    EditText edtTelefonoRespaldoProveedor;

    @BindView(R.id.edtDescripcionProveedor)
    EditText edtDescripcionProveedor;

    @BindView(R.id.btnCancelar)
    LinearLayout btnCancelar;

    @BindView(R.id.btnAgregarProveedor)
    LinearLayout btnAgregarProveedor;

    @BindView(R.id.llBtnPlusTelefono)
    LinearLayout llBtnPlusTelefono;

    @BindView(R.id.llBtnMenosTelefonoRespaldo)
    LinearLayout llBtnMenosTelefonoRespaldo;

    @BindView(R.id.llTelefonoRespaldo)
    LinearLayout llTelefonoRespaldo;

    public static AgregarCrearProveedorDialogFragment newInstance(Activity activity, FragmentManager fragmentManager, String titulo, IProveedorBusquedaFragmentView proveedorBusquedaFragmentView){
        AgregarCrearProveedorDialogFragment agregarCrearProveedorDialogFragment = new AgregarCrearProveedorDialogFragment();
        agregarCrearProveedorDialogFragment.activity = activity;
        agregarCrearProveedorDialogFragment.fragmentManager = fragmentManager;
        agregarCrearProveedorDialogFragment.titulo = titulo;
        agregarCrearProveedorDialogFragment.proveedorBusquedaFragmentView = proveedorBusquedaFragmentView;
        return agregarCrearProveedorDialogFragment;
    }

    public static AgregarCrearProveedorDialogFragment newInstance(Activity activity, FragmentManager fragmentManager, String titulo, IInversionPrincipalActivityView inversionPrincipalActivityView){
        AgregarCrearProveedorDialogFragment agregarCrearProveedorDialogFragment = new AgregarCrearProveedorDialogFragment();
        agregarCrearProveedorDialogFragment.activity = activity;
        agregarCrearProveedorDialogFragment.fragmentManager = fragmentManager;
        agregarCrearProveedorDialogFragment.titulo = titulo;
        agregarCrearProveedorDialogFragment.inversionPrincipalActivityView = inversionPrincipalActivityView;
        return agregarCrearProveedorDialogFragment;
    }
    public static AgregarCrearProveedorDialogFragment newInstance(Activity activity, FragmentManager fragmentManager, Proveedor proveedor, String titulo, IDetalleProveedorDialogFragmentView detalleProveedorDialogFragmentView){
        AgregarCrearProveedorDialogFragment agregarCrearProveedorDialogFragment = new AgregarCrearProveedorDialogFragment();
        agregarCrearProveedorDialogFragment.activity = activity;
        agregarCrearProveedorDialogFragment.proveedorActualizar = proveedor;
        agregarCrearProveedorDialogFragment.fragmentManager = fragmentManager;
        agregarCrearProveedorDialogFragment.titulo = titulo;
        agregarCrearProveedorDialogFragment.detalleProveedorDialogFragmentView = detalleProveedorDialogFragmentView;
        return agregarCrearProveedorDialogFragment;
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
        View agregarDialog = inflater.inflate(R.layout.dialog_fragment_agregar_crear_proveedor, null);

        ButterKnife.bind(this, agregarDialog);

        builder.setView(agregarDialog);

        txtTitulo.setText(titulo);

        if( detalleProveedorDialogFragmentView != null){
            proveedorPresenter = new ProveedorPresenter(getContext(), this,  detalleProveedorDialogFragmentView);
        } else {
            proveedorPresenter = new ProveedorPresenter(getContext(), this);
        }

        progress = new ProgressDialog(getContext());



        if(proveedorActualizar != null){

            edtNitProveedor.setText(proveedorActualizar.getNit());
            edtNombreProveedor.setText(proveedorActualizar.getNombre());
            edtDireccionProveedor.setText(proveedorActualizar.getDireccion());
            edtCiudadProveedor.setText(proveedorActualizar.getCiudad());
            edtTelefonoProveedor.setText(proveedorActualizar.getTelefono());

            if(proveedorActualizar.getTelefono_respaldo() != null && !proveedorActualizar.getTelefono_respaldo().equals("")){
                edtTelefonoRespaldoProveedor.setText(proveedorActualizar.getTelefono_respaldo());
                llTelefonoRespaldo.setVisibility(View.VISIBLE);
            } else {
                edtTelefonoRespaldoProveedor.setText("");
                llTelefonoRespaldo.setVisibility(View.GONE);
            }

            edtDescripcionProveedor.setText(proveedorActualizar.getDescripcion());

        } else {
            if(proveedorPresenter != null){
                proveedorPresenter.consultarProveedores();
            }
        }

        dialog = builder.create();

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        edtNitProveedor.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("Autocomplete...." + " - " + position + " - " + id + " - " + parent.getItemAtPosition(position));

                if(parent.getItemAtPosition(position).toString().contains("/")){
                    idProveedorSeleccionado = extraerIdProveedor(parent.getItemAtPosition(position).toString());

                    System.out.println("Autoc ID" + " - " + idProveedorSeleccionado );

                    proveedorSeleccionado = buscarProveedorSeleccionado(idProveedorSeleccionado);

                    if(proveedorSeleccionado != null){
                        edtNitProveedor.setText(proveedorSeleccionado.getNit());
                        edtNombreProveedor.setText(proveedorSeleccionado.getNombre());
                        edtDireccionProveedor.setText(proveedorSeleccionado.getDireccion());
                        edtCiudadProveedor.setText(proveedorSeleccionado.getCiudad());
                        edtTelefonoProveedor.setText(proveedorSeleccionado.getTelefono());
                        edtTelefonoRespaldoProveedor.setText(proveedorSeleccionado.getTelefono_respaldo());
                        edtDescripcionProveedor.setText(proveedorSeleccionado.getDescripcion());
                    }

                }

            }
        });

        edtNombreProveedor.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("Autocomplete...." + " - " + position + " - " + id + " - " + parent.getItemAtPosition(position));

                if(parent.getItemAtPosition(position).toString().contains("/")){
                    idProveedorSeleccionado = extraerIdProveedor(parent.getItemAtPosition(position).toString());

                    System.out.println("Autoc ID" + " - " + idProveedorSeleccionado );

                    proveedorSeleccionado = buscarProveedorSeleccionado(idProveedorSeleccionado);

                    if(proveedorSeleccionado != null){
                        edtNitProveedor.setText(proveedorSeleccionado.getNit());
                        edtNombreProveedor.setText(proveedorSeleccionado.getNombre());
                        edtDireccionProveedor.setText(proveedorSeleccionado.getDireccion());
                        edtCiudadProveedor.setText(proveedorSeleccionado.getCiudad());
                        edtTelefonoProveedor.setText(proveedorSeleccionado.getTelefono());
                        edtTelefonoRespaldoProveedor.setText(proveedorSeleccionado.getTelefono_respaldo());
                        edtDescripcionProveedor.setText(proveedorSeleccionado.getDescripcion());
                    }

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

    @OnClick(R.id.btnAgregarProveedor)
    void onClickAgregarProveedor(){
        if(validarCampos()){
            Proveedor proveedor = extrarDatos();

            if(proveedor != null){
                if(proveedorSeleccionado != null){
                    if(inversionPrincipalActivityView != null){
                        goToInversionDetalle(proveedorSeleccionado);
                    }
                } else {
                    proveedorPresenter.guardarProveedor(proveedor);
                }
            }
        }
    }

    /**
     * -------------- METODOS PROPIOS --------------------------------
     */

    /**
     * @author RagooS
     * @fecha 30/09/2022
     * @descripcion Metodo encargado de eliminar el nit del campo de texto del nit
     */
    /*private String eliminarNitNombreProveedor(String texto) {

        String[] nom = texto.split("/");

        String nombre = nom[1].trim();

        return nombre;
    }*/

    /**
     * @author RagooS
     * @fecha 30/09/2022
     * @descripcion Metodo encargado de extraer el nit de los campos autocompletables
     */
    private String extraerNitAutocomplete(String texto) {

        String[] nom = texto.split("/");

        String nit = nom[0].trim();

        return nit;

    }

    private long extraerIdProveedor(String texto) {

        String[] nom = texto.split("/");

        long id = Long.parseLong(nom[2].trim());

        return id;

    }

    /**
     * @author RagooS
     * @fecha 30/09/2022
     * @descripcion Metodo permite buscar el proveedor seleccionado por nit
     */
    private Proveedor buscarProveedorSeleccionado(Long idProveedorSeleccionado) {

        Proveedor proveedor = null;

        if(InformacionSession.getInstance().getProveedoresConsultados() != null && !InformacionSession.getInstance().getProveedoresConsultados().isEmpty()){
            for(Proveedor pro: InformacionSession.getInstance().getProveedoresConsultados()){
                if(pro.getId() == idProveedorSeleccionado){
                    proveedor = pro;
                }
            }
        }
        return proveedor;
    }

    /**
     * @author RagooS
     * @fecha 30/09/2022
     * @descripcion Metodo permite validar que todos los campos se encuentren con informacion
     */
    private boolean validarCampos() {
        boolean validado = false;

        if(!edtNitProveedor.getText().toString().equals("") && !buscarNitProveedoresConsultados(edtNitProveedor.getId())){
            if(!edtNombreProveedor.getText().toString().equals("")){
                if(!edtDireccionProveedor.getText().toString().equals("")){
                    if(!edtCiudadProveedor.getText().toString().equals("")){
                        if(!edtTelefonoProveedor.getText().toString().equals("")){
                            validado = true;
                        } else {
                            System.out.println("Campo Telefono Vacio");
                        }
                    } else {
                        System.out.println("Campo Ciudad Vacio");
                    }
                } else {
                    System.out.println("Campo Direccion Vacio");
                }
            } else {
                System.out.println("Campo Nombre Vacio");
            }
        } else {
            System.out.println("Campo NIT Vacio");
        }

        return validado;
    }

    /**
     * @author RagooS
     * @fecha 30/09/2022
     * @descripcion Metodo permite extraer la informacion de los campos y llenar el objeto Proveedor
     */
    private Proveedor extrarDatos() {
        Proveedor proveedor = new Proveedor();

        if(proveedorActualizar != null){
            proveedor.setId(proveedorActualizar.getId());
        }

        if(edtNitProveedor.getText().toString().contains("-")){
            strNiProveedor = extraerNitAutocomplete(edtNitProveedor.getText().toString());
            proveedor.setNit(strNiProveedor.trim());
        } else{
            proveedor.setNit(edtNitProveedor.getText().toString().trim());
        }

        if(edtNombreProveedor.getText().toString().contains("-")){
            strNombreProveedor = extraerNitAutocomplete(edtNombreProveedor.getText().toString());
            proveedor.setNombre(strNombreProveedor.trim());
        } else{
            proveedor.setNombre(edtNombreProveedor.getText().toString().trim());
        }

        proveedor.setDireccion(edtDireccionProveedor.getText().toString());
        proveedor.setTelefono(edtTelefonoProveedor.getText().toString());
        proveedor.setCiudad(edtCiudadProveedor.getText().toString());
        proveedor.setTelefono_respaldo(edtTelefonoRespaldoProveedor.getText().toString());
        proveedor.setDescripcion(edtDescripcionProveedor.getText().toString());

    return proveedor;
    }

    /**
     * @author RagooS
     * @fecha 30/09/2022
     * @descripcion Metodo permite buscar si existe un proveedor con el mismo NIT
     */
    private boolean buscarNitProveedoresConsultados(long id) {

        if(buscarProveedorSeleccionado(id) != null){
            return true;
        }

        return false;
    }

    /**
     * -------------- METODOS INTERFACE IAgregarCrearProveedorDialogFragmentView --------------------------------
     */

    /**
     * @Autor RagooS
     * @fecha 30/09/2022
     * @Descripccion Metodo permite mostrar barra de carga
     */
    @Override
    public void showProgress(String mensaje) {
        progress= ProgressDialog.show(getContext(), mensaje,null);
    }

    /**
     * @Autor RagooS
     * @fecha 30/09/2022
     * @Descripccion Metodo permite ocultar barra de carga
     */
    @Override
    public void hideProgress() {
        progress.dismiss();
    }

    /**
     * @Autor RagooS
     * @fecha 30/09/2022
     * @Descripccion Metodo permite ocultar dialogo
     */
    @Override
    public void dismissDialog() {
        dialog.dismiss();
    }

    /**
     * @Autor RagooS
     * @fecha 30/09/2022
     * @Descripccion Metodo permite agregar los nombres de los proveedores al Autocomplete
     */
    @Override
    public void agregarNombreNitAutocomplete(String[] nombres) {
        if(nombres != null && nombres.length != 0){

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),R.layout.adapter_autocomplete_text_azul_bajo, nombres);
            edtNombreProveedor.setThreshold(1);//will start working from first character
            edtNombreProveedor.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView

            edtNitProveedor.setThreshold(1);//will start working from first character
            edtNitProveedor.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView
        }
    }

    /**
     * @Autor RagooS
     * @fecha 01/10/2022
     * @Descripccion Metodo permite ir al fragmen DetalleInversion
     */
    @Override
    public void goToInversionDetalle(Proveedor proveedor){
        dismissDialog();

        InformacionSession.getInstance().setProveedorNewInversion(proveedor);

        if(inversionPrincipalActivityView != null){
            inversionPrincipalActivityView.goToDetalleInversion(proveedor, true);
        }
    }

    @Override
    public void mostrarMensaje(Proveedor proveedor, String tipoMensaje, boolean isInversion) {
        if(proveedor != null){
            DetalleProveedorDialogFragment detalleProveedorDialogFragment = DetalleProveedorDialogFragment.newInstance(activity, fragmentManager, proveedor, this, tipoMensaje);
            detalleProveedorDialogFragment.show(fragmentManager, "DetalleProveedor");
            Fragment fragment = fragmentManager.findFragmentByTag("DetalleProveedor");
            if (fragment != null) {
                fragmentManager.beginTransaction().remove(fragment).commit();
            }
        }
        if(isInversion){
            actualizarDatos();
        }
        dismissDialog();
    }

    @Override
    public void actualizarDatos() {
        proveedorPresenter.consultarProveedores();
    }
}
