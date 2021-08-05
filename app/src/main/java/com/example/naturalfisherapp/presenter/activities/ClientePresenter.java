package com.example.naturalfisherapp.presenter.activities;

import android.content.Context;

import com.example.naturalfisherapp.data.models.Cliente;
import com.example.naturalfisherapp.presenter.interfaces.IClientePresenter;
import com.example.naturalfisherapp.retrofit.ClientApiService;
import com.example.naturalfisherapp.retrofit.InterfaceApiService;
import com.example.naturalfisherapp.utilidades.InformacionSession;
import com.example.naturalfisherapp.view.interfaces.IAgregarClienteDialogFragmentView;

import java.util.ArrayList;
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

    public ClientePresenter(Context context, IAgregarClienteDialogFragmentView agregarClienteDialogFragmentView) {
        this.context = context;
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
                        }
                    }
                }

                @Override
                public void onFailure(Call<Cliente> call, Throwable t) {
                    if(agregarClienteDialogFragmentView != null){
                        agregarClienteDialogFragmentView.hideProgress();
                    }

                }
            });

        } catch ( Exception e){
            e.printStackTrace();
            if(agregarClienteDialogFragmentView != null){
                agregarClienteDialogFragmentView.hideProgress();
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
                    }
                }
            });

        } catch (Exception e){
            e.printStackTrace();
            if(agregarClienteDialogFragmentView != null){
                agregarClienteDialogFragmentView.hideProgress();
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

        if(body.getNombre().equals(clienteEnviado.getNombre())){
            if(body.getDireccion().equals(clienteEnviado.getDireccion())){
                if(body.getTelefono().equals(clienteEnviado.getTelefono())){
                    if(agregarClienteDialogFragmentView != null){
                        agregarClienteDialogFragmentView.hideProgress();
                    }
                    agregarClienteDialogFragmentView.goToVentaDetalle(body);
                    System.out.println("Cliente Guardado Con Exito");
                }
            }
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
                extraerNombresClientes(response.body());
            } else{
                if(agregarClienteDialogFragmentView != null){
                    agregarClienteDialogFragmentView.hideProgress();
                }
                System.out.println("Clientes vacios...");
            }
        } else {
            if(agregarClienteDialogFragmentView != null){
                agregarClienteDialogFragmentView.hideProgress();
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

}
