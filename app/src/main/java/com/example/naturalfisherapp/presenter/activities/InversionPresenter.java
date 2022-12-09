package com.example.naturalfisherapp.presenter.activities;

import android.content.Context;

import com.example.naturalfisherapp.data.models.Inversion;
import com.example.naturalfisherapp.data.models.interpretes.BusquedaInversiones;
import com.example.naturalfisherapp.data.models.interpretes.BusquedaProveedorInversion;
import com.example.naturalfisherapp.data.models.interpretes.DetalleInversiones;
import com.example.naturalfisherapp.presenter.interfaces.IInversionPresenter;
import com.example.naturalfisherapp.retrofit.ClientApiService;
import com.example.naturalfisherapp.retrofit.InterfaceApiService;
import com.example.naturalfisherapp.utilidades.EnumVariables;
import com.example.naturalfisherapp.utilidades.InformacionSession;
import com.example.naturalfisherapp.utilidades.Utilidades;
import com.example.naturalfisherapp.view.dialog.InformacionDialogFragment;
import com.example.naturalfisherapp.view.interfaces.fragment.IDetalleInversionFragmentView;
import com.example.naturalfisherapp.view.interfaces.fragment.IInversionBusquedaFragmentView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Fase 4 Tarea 3
 * de RagooS
 * Autor: Richard Gomez O.
 * Para: EmpresaDevelopers.Frontend.NaturalFisher
 * Fecha: 18/09/2022
 */
public class InversionPresenter implements IInversionPresenter {

    private Context context;
    private InterfaceApiService service;
    private IInversionBusquedaFragmentView inversionBusquedaFragmentView;
    private IDetalleInversionFragmentView detalleInversionFragmentView;

    public InversionPresenter(Context context, IInversionBusquedaFragmentView inversionBusquedaFragmentView) {
        this.context = context;
        this.inversionBusquedaFragmentView = inversionBusquedaFragmentView;
    }

    public InversionPresenter(Context context, IDetalleInversionFragmentView detalleInversionFragmentView) {
        this.context = context;
        this.detalleInversionFragmentView = detalleInversionFragmentView;
    }


    /**
     * --------------================ METODOS =================--------------------------------
     */

    /**
     * -------------- METODOS INTERFACE IInversionPresenter --------------------------------
     */

    /**
     * @Autor RagooS
     * @Descripccion Metodo permite enviar consultar las inversiones en un rango fecha mes
     * @Fecha 25/09/2022
     */
    @Override
    public void consultarInversionesEnMes(String fecha) {

        try {

            if(inversionBusquedaFragmentView != null){
                inversionBusquedaFragmentView.showProgress();
            }

            service = ClientApiService.getClient().create(InterfaceApiService.class);

            //Call<DetalleInversiones> call = service.getInversionesDetalleMes(fecha);

            Call<DetalleInversiones> call = service.getIodasInversionesDetalle();

            call.enqueue(new Callback<DetalleInversiones>() {
                @Override
                public void onResponse(Call<DetalleInversiones> call, Response<DetalleInversiones> response) {
                    organizarRespuesta(response.body());
                }

                @Override
                public void onFailure(Call<DetalleInversiones> call, Throwable t) {
                    organizarRespuesta(null);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            organizarRespuesta(null);
        }

    }

    @Override
    public void realizarInversion(final Inversion inversion) {

        try {

            if(detalleInversionFragmentView != null){
                detalleInversionFragmentView.showProgress("Enviando Informacion...");
            }

            service = ClientApiService.getClient().create(InterfaceApiService.class);

            Call<Inversion> call = service.saveInversion(inversion);

            call.enqueue(new Callback<Inversion>() {
                @Override
                public void onResponse(Call<Inversion> call, Response<Inversion> response) {
                    validarRespuestaInversionNueva(response.body());
                }

                @Override
                public void onFailure(Call<Inversion> call, Throwable t) {
                    validarRespuestaInversionNueva(null);
                }
            });

        } catch (Exception e){
            e.printStackTrace();
            validarRespuestaInversionNueva(null);
        }

    }

    /**
     * @Autor RagooS
     * @Descripccion Metodo permite consultar las inversiones en la fecha
     * @Fecha 05/11/2022
     */
    @Override
    public void consultarInversionesEnFecha(String fecha) {
        try {

            if(detalleInversionFragmentView != null){
                detalleInversionFragmentView.showProgress("Enviando Informacion...");
            }

            service = ClientApiService.getClient().create(InterfaceApiService.class);

            Call<List<Inversion>> call = service.getInversionesEnFecha(fecha);

            call.enqueue(new Callback<List<Inversion>>() {
                @Override
                public void onResponse(Call<List<Inversion>> call, Response<List<Inversion>> response) {
                    validarRespuestaInversiones(response.body());
                }

                @Override
                public void onFailure(Call<List<Inversion>> call, Throwable t) {

                }
            });



        } catch (Exception e){
            e.printStackTrace();
            validarRespuestaInversionNueva(null);
        }
    }


    /**
     * -------------- METODOS PROPIOS --------------------------------
     */

    /**
     * @Autor RagooS
     * @Descripccion Metodo permite organizar respuesta devuelta por consulta
     * @Fecha 25/09/2022
     * @Param Inversiones devueltas en la respuesta
     */
    private void organizarRespuesta(DetalleInversiones detalleInversiones) {

        if(detalleInversiones != null){

            if(detalleInversiones.getInversiones() != null && !detalleInversiones.getInversiones().isEmpty()){
                List<Inversion> inversiones = detalleInversiones.getInversiones();
                List<BusquedaInversiones> busquedaInversiones = new ArrayList<>();
                List<Inversion> inversionesComparar = new ArrayList<>(inversiones);

                for(int j = 0; j < inversiones.size();){
                    Inversion inversion = inversiones.get(j);

                    if(!inversionesComparar.isEmpty() ){
                        List<Inversion> inversionesIguales = new ArrayList<>();

                        for (int i = 0; i < inversionesComparar.size();){

                            if(Utilidades.compararFechasMismoDia(inversion.getFecha(), inversionesComparar.get(i).getFecha())){
                                System.out.println("=== FECHA IGUAL ===");

                                inversionesIguales.add(inversionesComparar.get(i));
                                inversionesComparar.remove(i);
                            } else {
                                i++;
                            }

                        }
                        if(!inversionesIguales.isEmpty()){
                            ingresarObjetosInversionBusqueda(busquedaInversiones, inversionesIguales);
                        }
                    }
                    inversiones = inversionesComparar;
                }

                if(busquedaInversiones != null && !busquedaInversiones.isEmpty()){
                    busquedaInversiones = calcularTotales(busquedaInversiones);
                    busquedaInversiones = ordenarBusquedaPorFecha(busquedaInversiones);
                    if(inversionBusquedaFragmentView != null){
                        InformacionSession.getInstance().setBusquedaInversiones(busquedaInversiones);
                        InformacionSession.getInstance().setDetalleInversiones(detalleInversiones);
                        inversionBusquedaFragmentView.hideProgress();
                        inversionBusquedaFragmentView.cargarDatos(busquedaInversiones, detalleInversiones);
                    }
                }

            } else {
                if(inversionBusquedaFragmentView != null){
                    inversionBusquedaFragmentView.hideProgress();
                    inversionBusquedaFragmentView.mostrarDialogoInformativo("Error", "No se encontraron inversiones en la fecha!..");
                }
            }

        } else {
            if(inversionBusquedaFragmentView != null){
                inversionBusquedaFragmentView.hideProgress();
                inversionBusquedaFragmentView.mostrarDialogoInformativo("Error", "Ocurrio un Error al consultar las inversiones en la fecha!..");
            }
        }
    }

    /**
     * @Autor RagooS
     * @Descripccion Metodo permite ordenar por fecha la BusquedaInversiones
     * @Fecha 25/09/2022
     * @Param BusquedaInversiones inversiones a ordenar
     * @return List<BusquedaInversiones> lista de objetos ordenados por fecha
     */
    private List<BusquedaInversiones> ordenarBusquedaPorFecha(List<BusquedaInversiones> busquedaInversiones) {
        for(int i = 0; i < busquedaInversiones.size(); i++){
            for(int j = 0; j < busquedaInversiones.size() - 1; j++){
                if(busquedaInversiones.get(j).getFecha().before(busquedaInversiones.get(j+1).getFecha())){
                    BusquedaInversiones busquedaInversionesTemp1 = busquedaInversiones.get(j);
                    BusquedaInversiones busquedaInversionesTemp2 = busquedaInversiones.get(j+1);
                    busquedaInversiones.set(j, busquedaInversionesTemp2);
                    busquedaInversiones.set(j + 1, busquedaInversionesTemp1);
                }
            }
        }
        return busquedaInversiones;
    }

    /**
     * @Autor RagooS
     * @Descripccion Metodo permite calcular el total de las BusquedaInversiones
     * @Fecha 25/09/2022
     * @Param BusquedaInversiones inversiones a calcular
     * @return List<BusquedaInversiones> lista de objetos calculados
     */
    private List<BusquedaInversiones> calcularTotales(List<BusquedaInversiones> busquedaInversiones) {

        for (int i = 0; i < busquedaInversiones.size(); i++){
            Double total = 0.0;
            if (busquedaInversiones.get(i).getProveedorInversions() != null && !busquedaInversiones.get(i).getProveedorInversions().isEmpty()){
                for(BusquedaProveedorInversion proveedor:busquedaInversiones.get(i).getProveedorInversions()){
                    total += proveedor.getPrecioInversion();
                }
            }
            busquedaInversiones.get(i).setTotal(total);
        }
        return busquedaInversiones;
    }

    /**
     * @Autor RagooS
     * @Descripccion Metodo permite ingresar los datos al objeto BusquedaInversiones
     * @Fecha 25/09/2022
     * @Param BusquedaInversiones inversiones a ingresar
     * @Param Inversiones devueltas en la respuesta
     */
    private void ingresarObjetosInversionBusqueda(List<BusquedaInversiones> busquedaInversiones, List<Inversion> inversiones) {
        BusquedaInversiones busqueda = new BusquedaInversiones();
        busqueda.setFecha(inversiones.get(0).getFecha());
        List<BusquedaProveedorInversion> proveedorInversion = new ArrayList<>();

        for(Inversion inversion:inversiones){
            BusquedaProveedorInversion busquedaProveedorInversion = new BusquedaProveedorInversion();
            busquedaProveedorInversion.setProveedor(inversion.getProveedor());
            busquedaProveedorInversion.setPrecioInversion(inversion.getTotal());

            proveedorInversion.add(busquedaProveedorInversion);
        }

        busqueda.setProveedorInversions(proveedorInversion);
        busquedaInversiones.add(busqueda);
    }

    private void validarRespuestaInversionNueva(Inversion response) {

        if(response != null && response.getItems() != null && !response.getItems().isEmpty() && response.getProveedor() != null){
            System.out.println("Inversion Exitosa....");
            if(detalleInversionFragmentView != null){

                InformacionSession.getInstance().setBusquedaInversiones(null);
                InformacionSession.getInstance().setDetalleInversiones(null);

                detalleInversionFragmentView.hideProgress();
                detalleInversionFragmentView.mostrarDialogoInformativo("Exito", "Se ha realizado la inversion con exito!..");
            }

        } else {
            if(detalleInversionFragmentView != null){
                detalleInversionFragmentView.hideProgress();
                detalleInversionFragmentView.mostrarDialogoInformativo("Error", "A Ocurrido un Error consulte al administrador...");
            }
        }

    }

    /**
     * @Autor RagooS
     * @Descripccion Metodo permite validar la respuesta del metodo de consulta inversiones
     * @Fecha 05/11/2022
     * @Param inversiones devueltas en la respuesta
     */
    private void validarRespuestaInversiones(List<Inversion> inversiones) {

        if( inversiones != null && !inversiones.isEmpty()){

            if(detalleInversionFragmentView != null){
                detalleInversionFragmentView.hideProgress();
                detalleInversionFragmentView.cargarAdapter(inversiones, EnumVariables.MODO_CONSULTA.getValor());
            }

        } else {
            if(detalleInversionFragmentView != null){
                detalleInversionFragmentView.hideProgress();
                detalleInversionFragmentView.goToInversionActivity();
            }
        }
    }
}
