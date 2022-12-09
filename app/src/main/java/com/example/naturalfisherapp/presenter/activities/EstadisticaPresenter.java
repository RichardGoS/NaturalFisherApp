package com.example.naturalfisherapp.presenter.activities;

import android.content.Context;
import android.util.Log;

import com.example.naturalfisherapp.data.models.EstadisticasMesProducto;
import com.example.naturalfisherapp.data.models.interpretes.DetalleEstadistica;
import com.example.naturalfisherapp.presenter.interfaces.IEstadisticaPresenter;
import com.example.naturalfisherapp.retrofit.ClientApiService;
import com.example.naturalfisherapp.retrofit.InterfaceApiService;
import com.example.naturalfisherapp.view.interfaces.fragment.IEstadisticasProductoBusquedaFragmentView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Fase 4 Tarea 1
 * de RagooS
 * Autor: Richard Gomez O.
 * Para: EmpresaDevelopers.Frontend.NaturalFisher
 * Fecha: 07/08/2022
 */
public class EstadisticaPresenter implements IEstadisticaPresenter {

    private InterfaceApiService service;
    private Context context;

    private IEstadisticasProductoBusquedaFragmentView estadisticasProductoBusquedaFragmentView;

    public EstadisticaPresenter(){
    }

    public EstadisticaPresenter(Context context, IEstadisticasProductoBusquedaFragmentView estadisticasProductoBusquedaFragmentView) {
        this.context = context;
        this.estadisticasProductoBusquedaFragmentView = estadisticasProductoBusquedaFragmentView;
    }

    /**
     * --------------================ METODOS =================--------------------------------
     */

    /**
     * -------------- METODOS INTERFACE IEstadisticaPresenter --------------------------------
     */

    /**
     * @Autor RagooS
     * @Date 07/08/2022
     * @Descripccion Metodo permite consultar las estadisticas de los productos en un mes determinado
     * @param fecha
     */
    @Override
    public void consultarEstadisticasProductosEnMes(String fecha) {

        try {

            if(estadisticasProductoBusquedaFragmentView != null){
                estadisticasProductoBusquedaFragmentView.showProgress("Consultando...");
            }

            service = ClientApiService.getClient().create(InterfaceApiService.class);

            Call<DetalleEstadistica> call = service.getEstadisticasProductosEnMes(fecha);

            call.enqueue(new Callback<DetalleEstadistica>() {
                @Override
                public void onResponse(Call<DetalleEstadistica> call, Response<DetalleEstadistica> response) {
                    validarEstadisticasProductosEnMes(response.body());
                }

                @Override
                public void onFailure(Call<DetalleEstadistica> call, Throwable t) {
                    validarEstadisticasProductosEnMes(null);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("consultarEstadisticas", "Error al consultar las estadisticas de los productos en el mes");
            validarEstadisticasProductosEnMes(null);
        }

    }

    /**
     * -------------- METODOS PROPIOS --------------------------------
     */

    /**
     * @Autor RagooS
     * @Date 07/08/2022
     * @Descripccion Metodo permite validar la consulta de estadisticas productos en mes
     * @param body
     */
    private void validarEstadisticasProductosEnMes(DetalleEstadistica detalle) {

        if(detalle != null){
            if(detalle.getEstadisticas() != null && !detalle.getEstadisticas().isEmpty()){
                if(estadisticasProductoBusquedaFragmentView != null){
                    estadisticasProductoBusquedaFragmentView.hideProgress();
                    estadisticasProductoBusquedaFragmentView.cargarAdapter(detalle);
                }
            }

        } else {
            Log.e("consultarEstadisticas", "Respuesta vacia");
            if(estadisticasProductoBusquedaFragmentView != null){
                estadisticasProductoBusquedaFragmentView.hideProgress();
            }
        }

    }
}
