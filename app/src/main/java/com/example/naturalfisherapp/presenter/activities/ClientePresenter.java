package com.example.naturalfisherapp.presenter.activities;

import android.content.Context;

import com.example.naturalfisherapp.data.models.Cliente;
import com.example.naturalfisherapp.presenter.interfaces.IClientePresenter;
import com.example.naturalfisherapp.retrofit.ClientApiService;
import com.example.naturalfisherapp.retrofit.InterfaceApiService;
import com.example.naturalfisherapp.utilidades.InformacionSession;
import com.example.naturalfisherapp.view.interfaces.dialog.IAgregarClienteDialogFragmentView;
import com.example.naturalfisherapp.view.interfaces.adapter.IVentaRegistroHolderView;
import com.example.naturalfisherapp.view.interfaces.dialog.IDetalleClienteDialogFragment;
import com.example.naturalfisherapp.view.interfaces.fragment.IClienteBusquedaFragmentView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * de RagooS
 * Autor: Richard Gomez O.
 * Para: EmpresaDevelopers.Frontend.NaturalFisher
 * Fecha: 16/07/2021
 */

public class ClientePresenter implements IClientePresenter {

    private Context context;
    private InterfaceApiService service;
    private IAgregarClienteDialogFragmentView agregarClienteDialogFragmentView;
    private IClienteBusquedaFragmentView iClienteBusquedaFragmentView;
    private IDetalleClienteDialogFragment iDetalleClienteDialogFragment;

    private IVentaRegistroHolderView iVentaRegistroHolderView;

    public ClientePresenter(Context context, IAgregarClienteDialogFragmentView agregarClienteDialogFragmentView) {
        this.context = context;
        this.agregarClienteDialogFragmentView = agregarClienteDialogFragmentView;
    }

    public ClientePresenter(Context context, IAgregarClienteDialogFragmentView agregarClienteDialogFragmentView, IClienteBusquedaFragmentView iClienteBusquedaFragmentView) {
        this.context = context;
        this.iClienteBusquedaFragmentView = iClienteBusquedaFragmentView;
        this.agregarClienteDialogFragmentView = agregarClienteDialogFragmentView;
    }

    public ClientePresenter(Context context, IClienteBusquedaFragmentView iClienteBusquedaFragmentView) {
        this.context = context;
        this.iClienteBusquedaFragmentView = iClienteBusquedaFragmentView;
    }

    public ClientePresenter(Context context, IClienteBusquedaFragmentView iClienteBusquedaFragmentView, IDetalleClienteDialogFragment iDetalleClienteDialogFragment) {
        this.context = context;
        this.iClienteBusquedaFragmentView = iClienteBusquedaFragmentView;
        this.iDetalleClienteDialogFragment = iDetalleClienteDialogFragment;
    }

    public ClientePresenter(Context context, IDetalleClienteDialogFragment iDetalleClienteDialogFragment, IAgregarClienteDialogFragmentView agregarClienteDialogFragmentView) {
        this.context = context;
        this.iDetalleClienteDialogFragment = iDetalleClienteDialogFragment;
        this.agregarClienteDialogFragmentView = agregarClienteDialogFragmentView;
    }

    public ClientePresenter(Context context, IVentaRegistroHolderView iVentaRegistroHolderView, IAgregarClienteDialogFragmentView agregarClienteDialogFragmentView) {
        this.context = context;
        this.iVentaRegistroHolderView = iVentaRegistroHolderView;
        this.agregarClienteDialogFragmentView = agregarClienteDialogFragmentView;
    }

    /**
     * --------------================ METODOS =================--------------------------------
     */

    /**
     * -------------- METODOS INTERFACE IClientePresenter --------------------------------
     */

    /**
     * @Autor RagooS
     * @Descripccion Metodo permite enviar cliente al servidor para ser guardados
     * @Fecha 16/07/21
     */
    @Override
    public void guardarCliente(final Cliente clienteNew) {

        try {

            if(agregarClienteDialogFragmentView != null){
                agregarClienteDialogFragmentView.showProgress("Agregando...");
            }

            service = ClientApiService.getClient().create(InterfaceApiService.class);

            Call<Cliente> call = service.saveCliente(clienteNew);

            call.enqueue(new Callback<Cliente>() {
                @Override
                public void onResponse(Call<Cliente> call, Response<Cliente> response) {
                    if(response != null){
                        if(response.body() != null){
                            validarClienteNuevo(response.body(), clienteNew);
                        }
                    } else {
                        System.out.println("Objeto respuesta null");
                        if(agregarClienteDialogFragmentView != null){
                            agregarClienteDialogFragmentView.hideProgress();
                            validarClienteNuevo(clienteNew, null);
                        }
                    }
                }

                @Override
                public void onFailure(Call<Cliente> call, Throwable t) {
                    if(agregarClienteDialogFragmentView != null){
                        agregarClienteDialogFragmentView.hideProgress();
                        validarClienteNuevo(clienteNew, null);
                    }

                }
            });

        } catch ( Exception e){
            e.printStackTrace();
            if(agregarClienteDialogFragmentView != null){
                agregarClienteDialogFragmentView.hideProgress();
                validarClienteNuevo(clienteNew, null);
            }
        }

    }

    /**
     * @Autor RagooS
     * @Descripccion Metodo permite enviar peticion para consultar clientes almacenados en el servidor
     * @Fecha 17/07/21
     */
    @Override
    public void consultarClientes() {

        try{
            if(agregarClienteDialogFragmentView != null){
                agregarClienteDialogFragmentView.showProgress("Consultando...");
            } else if(iClienteBusquedaFragmentView != null){
                iClienteBusquedaFragmentView.showProgress("Consultando...");
            }

            service = ClientApiService.getClient().create(InterfaceApiService.class);

            Call<List<Cliente>> call = service.getClientes();

            call.enqueue(new Callback<List<Cliente>>() {
                @Override
                public void onResponse(Call<List<Cliente>> call, Response<List<Cliente>> response) {
                    validarRespuestaClientesConsultados(response);
                }

                @Override
                public void onFailure(Call<List<Cliente>> call, Throwable t) {
                    if(agregarClienteDialogFragmentView != null){
                        agregarClienteDialogFragmentView.hideProgress();
                    } else if(iClienteBusquedaFragmentView != null){
                        iClienteBusquedaFragmentView.hideProgress();
                    }
                }
            });

        } catch (Exception e){
            e.printStackTrace();
            if(agregarClienteDialogFragmentView != null){
                agregarClienteDialogFragmentView.hideProgress();
            } else if(iClienteBusquedaFragmentView != null){
                iClienteBusquedaFragmentView.hideProgress();
            }
        }

    }

    /**
     * @Autor RagooS
     * @Descripccion Metodo permite eliminar el cliente
     * @Fecha 03/01/2022
     */
    @Override
    public void eliminarCliente(Cliente cliente) {
        try{
            if(iDetalleClienteDialogFragment != null){
                iDetalleClienteDialogFragment.showProgress("Eliminando...");
            }

            service = ClientApiService.getClient().create(InterfaceApiService.class);

            Call<Boolean> call = service.eliminarCliente(cliente);

            call.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    validarClienteEliminado(response.body());
                }

                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {
                    validarClienteEliminado(false);
                }
            });

        } catch (Exception e){
            e.printStackTrace();
            validarClienteEliminado(false);
        }
    }

    /**
     * @Autor RagooS
     * @Descripccion Metodo permite actualizar el cliente
     * @Fecha 07/01/2022
     */
    @Override
    public void actualizarCliente(Cliente cliente) {
        try {

            if(agregarClienteDialogFragmentView != null){
                agregarClienteDialogFragmentView.showProgress("Actualizando...");
            }

            service = ClientApiService.getClient().create(InterfaceApiService.class);

            Call<Cliente> call = service.saveCliente(cliente);

            call.enqueue(new Callback<Cliente>() {
                @Override
                public void onResponse(Call<Cliente> call, Response<Cliente> response) {
                    if(response != null){
                        if(response.body() != null){
                            validarClienteNuevo(response.body(), cliente);
                        }
                    } else {
                        System.out.println("Objeto respuesta null");
                        if(agregarClienteDialogFragmentView != null){
                            agregarClienteDialogFragmentView.hideProgress();
                            validarClienteNuevo(cliente, null);
                        }
                    }
                }

                @Override
                public void onFailure(Call<Cliente> call, Throwable t) {
                    if(agregarClienteDialogFragmentView != null){
                        agregarClienteDialogFragmentView.hideProgress();
                        validarClienteNuevo(cliente, null);
                    }

                }
            });

        } catch ( Exception e){
            e.printStackTrace();
            if(agregarClienteDialogFragmentView != null){
                agregarClienteDialogFragmentView.hideProgress();
                validarClienteNuevo(cliente, null);
            }
        }
    }

    /**
     * -------------- METODOS PROPIOS --------------------------------
     */

    /**
     * @Autor RagooS
     * @Descripccion Metodo permite validar si el cliente fue creado con exito
     * @Fecha 16/07/21
     */
    private void validarClienteNuevo(Cliente body, Cliente clienteEnviado) {

        if(clienteEnviado != null){
            if(body.getNombre().equals(clienteEnviado.getNombre())){
                if(body.getDireccion().equals(clienteEnviado.getDireccion())){
                    if(body.getTelefono().equals(clienteEnviado.getTelefono())){
                        if(agregarClienteDialogFragmentView != null){
                            agregarClienteDialogFragmentView.hideProgress();
                        }
                        if(iClienteBusquedaFragmentView != null){
                            agregarClienteDialogFragmentView.dismissDialog();
                            agregarClienteDialogFragmentView.mostrarMensaje(body,"EXITO_CREACION", false);
                            iClienteBusquedaFragmentView.actualizarDatos();
                        } else if(iDetalleClienteDialogFragment != null){
                            agregarClienteDialogFragmentView.dismissDialog();
                            iDetalleClienteDialogFragment.mostrarMensaje("EXITO_ACTUALIZACION");
                        }else {
                            agregarClienteDialogFragmentView.mostrarMensaje(body,"EXITO_CREACION", true);
                        }
                        System.out.println("Cliente Guardado Con Exito");
                    } else {
                        agregarClienteDialogFragmentView.mostrarMensaje(body,"ALVERTENCIA! No se registro.", false);
                    }
                } else {
                    agregarClienteDialogFragmentView.mostrarMensaje(body,"ALVERTENCIA! No se registro.", false);
                }
            } else {
                agregarClienteDialogFragmentView.mostrarMensaje(body,"ALVERTENCIA! No se registro.", false);
            }
        } else {
            agregarClienteDialogFragmentView.mostrarMensaje(body,"ALVERTENCIA! No se registro.", false);
        }

    }

    /**
     * @Autor RagooS
     * @Descripccion Metodo permite validar respuesta de los clientes consultados
     * @Fecha 16/07/21
     */
    private void validarRespuestaClientesConsultados(Response<List<Cliente>> response) {

        if(response != null){
            if(response.body() != null && !response.body().isEmpty()){
                InformacionSession.getInstance().setClientesConsultados(response.body());
                if(agregarClienteDialogFragmentView != null){
                    extraerNombresClientes(response.body());
                } else if(iClienteBusquedaFragmentView != null){
                    iClienteBusquedaFragmentView.cargarAdapter(response.body());
                    iClienteBusquedaFragmentView.hideProgress();
                }
            } else{
                if(agregarClienteDialogFragmentView != null){
                    agregarClienteDialogFragmentView.hideProgress();
                } else if(iClienteBusquedaFragmentView != null){
                    iClienteBusquedaFragmentView.hideProgress();
                }
                System.out.println("Clientes vacios...");
            }
        } else {
            if(agregarClienteDialogFragmentView != null){
                agregarClienteDialogFragmentView.hideProgress();
            } else if(iClienteBusquedaFragmentView != null){
                iClienteBusquedaFragmentView.hideProgress();
            }
            System.out.println("Respuesta Null");
        }

    }

    /**
     * @Autor RagooS
     * @Descripccion Metodo permite extraer los nombres de los clientes consultados
     * @Fecha 16/07/21
     */
    private void extraerNombresClientes(List<Cliente> body) {

        String[] nombres = new String[body.size()];

        int cont = 0;
        for(Cliente cliente: body){
            nombres[cont] = cliente.getNombre() + " - " + cliente.getId();
            cont++;
        }

        if(agregarClienteDialogFragmentView != null){
            agregarClienteDialogFragmentView.agregarNombreClientesAutocomplete(nombres);
            agregarClienteDialogFragmentView.hideProgress();
        }

    }

    /**
     * if(tipoMensaje.equals("ERROR_ELIMINACION")){
     *             txtMensaje.setText("ALVERTENCIA! No se elimino.");
     *         } else if(tipoMensaje.equals("ERROR_CREACION")){
     *             txtMensaje.setText("ALVERTENCIA! No se registro.");
     *         } else if(tipoMensaje.equals("EXITO_CREACION")){
     *             txtMensaje.setText("Creado Con Éxito!");
     *         } else if(tipoMensaje.equals("EXITO_ELIMINACION")){
     *             txtMensaje.setText("Se Elimino Con Exito.");
     *         }
     */

    /**
     * @Autor RagooS
     * @Descripccion Metodo permite extraer los nombres de los clientes consultados
     * @Param Boolean variable tipo boolean indica si se elimino o no con exito
     * @Fecha 03/01/2022
     */
    private void validarClienteEliminado(Boolean body) {

        if(body){
            if(iDetalleClienteDialogFragment != null ){
                iDetalleClienteDialogFragment.hideProgress();
                iDetalleClienteDialogFragment.mostrarMensaje("EXITO_ELIMINACION");
            }
        } else {
            if(iDetalleClienteDialogFragment != null ){
                iDetalleClienteDialogFragment.hideProgress();
                iDetalleClienteDialogFragment.mostrarMensaje("ERROR_ELIMINACION");
            }
            System.out.println("Cliente No Eliminado");
        }
    }

}
