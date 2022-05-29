package com.example.naturalfisherapp.presenter.activities;

import android.content.Context;

import com.example.naturalfisherapp.data.models.Bodega;
import com.example.naturalfisherapp.presenter.interfaces.IBodegaPresenter;
import com.example.naturalfisherapp.retrofit.ClientApiService;
import com.example.naturalfisherapp.retrofit.InterfaceApiService;
import com.example.naturalfisherapp.utilidades.InformacionSession;
import com.example.naturalfisherapp.view.interfaces.activities.IMenuPrincipalActivitieView;
import com.example.naturalfisherapp.view.interfaces.fragment.IBodegaBusquedaFragmentView;

import java.util.List;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * de RagooS
 * Autor: Richard Gomez O.
 * Para: EmpresaDevelopers.Frontend.NaturalFisher
 * Fecha: 13/02/2022
 */

public class BodegaPresenter implements IBodegaPresenter {

    private Context context;
    private InterfaceApiService service;
    private IBodegaBusquedaFragmentView iBodegaBusquedaFragmentView;
    private IMenuPrincipalActivitieView menuPrincipalActivitieView;

    public BodegaPresenter(Context context, IMenuPrincipalActivitieView menuPrincipalActivitieView) {
        this.context = context;
        this.menuPrincipalActivitieView = menuPrincipalActivitieView;
    }

    public BodegaPresenter(Context context, IBodegaBusquedaFragmentView iBodegaBusquedaFragmentView) {
        this.context = context;
        this.iBodegaBusquedaFragmentView = iBodegaBusquedaFragmentView;
    }

    /**
     * --------------================ METODOS =================--------------------------------
     */

    /**
     * -------------- METODOS INTERFACE IBodegaPresenter --------------------------------
     */

    /**
     * @Autor RagooS
     * @Descripccion Metodo permite enviar peticion para consultar bodega almacenados en el servidor
     * @Fecha 13/02/2022
     */
    @Override
    public void consultarBodega() {

        try {
            if(iBodegaBusquedaFragmentView!= null){
                iBodegaBusquedaFragmentView.showProgress("Consultando...");
            }

            service = ClientApiService.getClient().create(InterfaceApiService.class);

            Call<List<Bodega>> call = service.getBodegas();

            call.enqueue(new Callback<List<Bodega>>() {
                @Override
                public void onResponse(Call<List<Bodega>> call, Response<List<Bodega>> response) {
                    validarConsulta(response.body());
                }

                @Override
                public void onFailure(Call<List<Bodega>> call, Throwable t) {
                    validarConsulta(null);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            validarConsulta(null);
        }

    }

    /**
     * @Autor RagooS
     * @Descripccion Metodo permite enviar peticion para realizar el inventario de todos los productos activos
     * @Fecha 22/02/2022
     */
    @Override
    public void realizarInventarioGeneral() {

        try {

            if(iBodegaBusquedaFragmentView != null){
                iBodegaBusquedaFragmentView.showProgress("Cargando...");
            }

            service = ClientApiService.getClient().create(InterfaceApiService.class);

            Call<Boolean> call = service.realizarInventarioGeneral();

            call.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    validarInventarioGeneral(response.body());
                }

                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {
                    validarInventarioGeneral(null);
                }
            });

        } catch (Exception e){
            e.printStackTrace();
            validarInventarioGeneral(null);
        }

    }



    /**
     * -------------- METODOS PROPIOS --------------------------------
     */

    /**
     * @Autor RagooS
     * @Descripccion Metodo permite validar la cunsulta
     * @Fecha 13/02/2022
     */
    private void validarConsulta(List<Bodega> body) {

        if(body != null && !body.isEmpty()){
            InformacionSession.getInstance().setBodegaList(body);
            if(iBodegaBusquedaFragmentView!= null){
                iBodegaBusquedaFragmentView.cargarAdapter(body);
                iBodegaBusquedaFragmentView.hideProgress();
            }
        } else {
            if(iBodegaBusquedaFragmentView!= null){
                iBodegaBusquedaFragmentView.cargarAdapter(body);
                iBodegaBusquedaFragmentView.hideProgress();
            }
        }

    }

    /**
     * @Autor RagooS
     * @Descripccion Metodo permite validar la respuesta despues de realizar el inventario general
     * @Fecha 22/02/2022
     */
    private void validarInventarioGeneral(Boolean body) {

        if(body != null && body){
            if(iBodegaBusquedaFragmentView != null){
                iBodegaBusquedaFragmentView.hideProgress();
                consultarBodega();
                InformacionSession.getInstance().setProductosActivosVenta(null);
            }
        } else {
            if(iBodegaBusquedaFragmentView != null){
                iBodegaBusquedaFragmentView.hideProgress();
            }
        }

    }
}
