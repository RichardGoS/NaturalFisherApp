package com.example.naturalfisherapp.presenter.activities;

import android.content.Context;

import com.example.naturalfisherapp.data.models.Proveedor;
import com.example.naturalfisherapp.presenter.interfaces.IProveedorPresenter;
import com.example.naturalfisherapp.retrofit.ClientApiService;
import com.example.naturalfisherapp.retrofit.InterfaceApiService;
import com.example.naturalfisherapp.utilidades.InformacionSession;
import com.example.naturalfisherapp.view.interfaces.dialog.IAgregarCrearProveedorDialogFragmentView;
import com.example.naturalfisherapp.view.interfaces.dialog.IDetalleProveedorDialogFragmentView;
import com.example.naturalfisherapp.view.interfaces.fragment.IInversionBusquedaFragmentView;
import com.example.naturalfisherapp.view.interfaces.fragment.IProveedorBusquedaFragmentView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Fase 4 Tarea 3
 * de RagooS
 * Autor: Richard Gomez O.
 * Para: EmpresaDevelopers.Frontend.NaturalFisher
 * Fecha: 27/09/2022
 */
public class ProveedorPresenter implements IProveedorPresenter {

    private Context context;
    private InterfaceApiService service;
    private IProveedorBusquedaFragmentView proveedorBusquedaFragment;
    private IAgregarCrearProveedorDialogFragmentView agregarCrearProveedorDialogFragmentView;
    private IDetalleProveedorDialogFragmentView detalleProveedorDialogFragmentView;

    public ProveedorPresenter(Context context, IProveedorBusquedaFragmentView proveedorBusquedaFragment) {
        this.context = context;
        this.proveedorBusquedaFragment = proveedorBusquedaFragment;
    }

    public ProveedorPresenter(Context context, IAgregarCrearProveedorDialogFragmentView agregarCrearProveedorDialogFragmentView) {
        this.context = context;
        this.agregarCrearProveedorDialogFragmentView = agregarCrearProveedorDialogFragmentView;
    }

    public ProveedorPresenter(Context context, IAgregarCrearProveedorDialogFragmentView agregarCrearProveedorDialogFragmentView , IDetalleProveedorDialogFragmentView detalleProveedorDialogFragmentView) {
        this.context = context;
        this.agregarCrearProveedorDialogFragmentView = agregarCrearProveedorDialogFragmentView;
        this.detalleProveedorDialogFragmentView = detalleProveedorDialogFragmentView;
    }

    public ProveedorPresenter(Context context, IDetalleProveedorDialogFragmentView detalleProveedorDialogFragmentView) {
        this.context = context;
        this.detalleProveedorDialogFragmentView = detalleProveedorDialogFragmentView;
    }

    /**
     * --------------================ METODOS =================--------------------------------
     */

    /**
     * -------------- METODOS INTERFACE IProveedorPresenter --------------------------------
     */

    /**
     * @author RagooS
     * @fecha 30/09/2022
     * @descripcion Metodo permite consultar los proveedores en el servidor
     */
    @Override
    public void consultarProveedores() {

        try {
            if(proveedorBusquedaFragment != null){
                proveedorBusquedaFragment.showProgress("Consultando...");
            } else if(agregarCrearProveedorDialogFragmentView != null) {
                agregarCrearProveedorDialogFragmentView.showProgress("Consultando...");
            }

            service = ClientApiService.getClient().create(InterfaceApiService.class);

            Call<List<Proveedor>> call = service.getProveedores();

            call.enqueue(new Callback<List<Proveedor>>() {
                @Override
                public void onResponse(Call<List<Proveedor>> call, Response<List<Proveedor>> response) {
                    validarRespuestaProveedoresConsultados(response.body());
                }

                @Override
                public void onFailure(Call<List<Proveedor>> call, Throwable t) {
                    validarRespuestaProveedoresConsultados(null);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            validarRespuestaProveedoresConsultados(null);
        }
    }

    /**
     * @author RagooS
     * @fecha 30/09/2022
     * @descripcion Metodo permite enviar informacion al servidor para almacenar el proveedor
     */
    @Override
    public void guardarProveedor(Proveedor proveedor) {
        try {
            if(agregarCrearProveedorDialogFragmentView != null && detalleProveedorDialogFragmentView != null ){
                agregarCrearProveedorDialogFragmentView.showProgress("Actualizando...");
            } else if(agregarCrearProveedorDialogFragmentView != null){
                agregarCrearProveedorDialogFragmentView.showProgress("Agregando...");
            }

            service = ClientApiService.getClient().create(InterfaceApiService.class);

            Call<Proveedor> call = service.saveProveedor(proveedor);

            call.enqueue(new Callback<Proveedor>() {
                @Override
                public void onResponse(Call<Proveedor> call, Response<Proveedor> response) {
                    validarRespuestaProveedorAlmacenado(response.body());
                }

                @Override
                public void onFailure(Call<Proveedor> call, Throwable t) {
                    validarRespuestaProveedorAlmacenado(null);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            validarRespuestaProveedorAlmacenado(null);
        }
    }

    /**
     * @Autor RagooS
     * @Descripccion Metodo permite eliminar un proveedor
     * @Fecha 20/09/2022
     */
    @Override
    public void eliminarProveedor(Proveedor proveedor) {
        try {
            if(proveedorBusquedaFragment != null){
                proveedorBusquedaFragment.showProgress("Eliminando...");
            }

            service = ClientApiService.getClient().create(InterfaceApiService.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * -------------- METODOS PROPIOS --------------------------------
     */

    /**
     * @Autor RagooS
     * @Descripccion Metodo permite validar los proveedores consultados
     * @Fecha 29/09/2022
     */
    private void validarRespuestaProveedoresConsultados(List<Proveedor> proveedores) {

        if(proveedores != null && !proveedores.isEmpty()){

            InformacionSession.getInstance().setProveedoresConsultados(proveedores);

            if(proveedorBusquedaFragment != null){
                proveedorBusquedaFragment.hideProgress();
                proveedorBusquedaFragment.cargarAdapter(proveedores);
            } else if(agregarCrearProveedorDialogFragmentView != null) {
                extraerNombresClientes(proveedores);
            }
        } else {
            if(proveedorBusquedaFragment != null){
                proveedorBusquedaFragment.hideProgress();
            } else if(agregarCrearProveedorDialogFragmentView != null) {
                agregarCrearProveedorDialogFragmentView.hideProgress();
            }
        }
    }

    /**
     * @author RagooS
     * @fecha 30/09/2022
     * @descripcion Metodo permite estraer el nombre con el nit de los proveedores
     */
    private void extraerNombresClientes(List<Proveedor> proveedores){
        String[] nombres = new String[proveedores.size()];

        int cont = 0;
        for(Proveedor proveedor:proveedores){
            nombres[cont] = proveedor.getNit() + " / " + proveedor.getNombre() + " / " + proveedor.getId();
            cont++;
        }

        if(agregarCrearProveedorDialogFragmentView != null){
            agregarCrearProveedorDialogFragmentView.agregarNombreNitAutocomplete(nombres);
            agregarCrearProveedorDialogFragmentView.hideProgress();
        }
    }

    /**
     * @author RagooS
     * @fecha 01/10/2022
     * @descripcion Metodo permite validar la respuesta de el proveedor almacenado
     */
    private void validarRespuestaProveedorAlmacenado(Proveedor proveedor) {

        if(proveedor != null){
            if(proveedor.getId() != null && !proveedor.getId().equals("")){
                if(proveedor.getNit() != null && !proveedor.getNit().equals("")){
                    if(agregarCrearProveedorDialogFragmentView != null && detalleProveedorDialogFragmentView != null){
                        agregarCrearProveedorDialogFragmentView.hideProgress();
                        agregarCrearProveedorDialogFragmentView.mostrarMensaje(proveedor,"EXITO_Actualizacion", false);
                    } else if(agregarCrearProveedorDialogFragmentView != null){
                        agregarCrearProveedorDialogFragmentView.hideProgress();
                        agregarCrearProveedorDialogFragmentView.mostrarMensaje(proveedor,"EXITO_CREACION", false);
                    }
                } else {
                    if(agregarCrearProveedorDialogFragmentView != null){
                        agregarCrearProveedorDialogFragmentView.hideProgress();
                        agregarCrearProveedorDialogFragmentView.mostrarMensaje(proveedor,"ALVERTENCIA! No se registro.", false);
                    }
                }
            } else {
                if(agregarCrearProveedorDialogFragmentView != null){
                    agregarCrearProveedorDialogFragmentView.hideProgress();
                    agregarCrearProveedorDialogFragmentView.mostrarMensaje(proveedor,"ALVERTENCIA! No se registro.", false);
                }
            }
        } else {
            if(agregarCrearProveedorDialogFragmentView != null){
                agregarCrearProveedorDialogFragmentView.hideProgress();
                agregarCrearProveedorDialogFragmentView.mostrarMensaje(proveedor,"ALVERTENCIA! No se registro.", false);
            }
        }
    }
}
