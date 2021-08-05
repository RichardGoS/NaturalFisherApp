package com.example.naturalfisherapp.presenter.activities;

import android.content.Context;

import com.example.naturalfisherapp.data.models.Venta;
import com.example.naturalfisherapp.data.models.interpretes.BusquedaClientesVenta;
import com.example.naturalfisherapp.data.models.interpretes.BusquedaVentas;
import com.example.naturalfisherapp.retrofit.ClientApiService;
import com.example.naturalfisherapp.retrofit.InterfaceApiService;
import com.example.naturalfisherapp.utilidades.Utilidades;
import com.example.naturalfisherapp.view.interfaces.IDetalleRegistroVentaFragmentView;
import com.example.naturalfisherapp.view.interfaces.VentaBusquedaFragmentView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * de RagooS
 * Autor: Richard Gomez O.
 * Para: EmpresaDevelopers.Frontend.NaturalFisher
 * Fecha: 06/07/2021
 */

public class ventaPresenter implements com.example.naturalfisherapp.presenter.interfaces.IVentaPresenter {

    private Context context;
    private VentaBusquedaFragmentView ventaBusquedaFragmentView;
    private IDetalleRegistroVentaFragmentView iDetalleRegistroVentaFragmentView;
    private InterfaceApiService service;

    public ventaPresenter() {
    }

    public ventaPresenter(Context context, VentaBusquedaFragmentView ventaBusquedaFragmentView){
        this.context = context;
        this.ventaBusquedaFragmentView = ventaBusquedaFragmentView;
    }

    public ventaPresenter(Context context, IDetalleRegistroVentaFragmentView iDetalleRegistroVentaFragmentView){
        this.context = context;
        this.iDetalleRegistroVentaFragmentView = iDetalleRegistroVentaFragmentView;
    }

    /**
     * --------------================ METODOS =================--------------------------------
     */

    /**
     * -------------- METODOS INTERFACE IVentaPresenter --------------------------------
     */

    /**
     * @Autor RagooS
     * @Descripccion Metodo permite consultar las ventas con rango de fecha por defecto
     * @Fecha 07/07/21
     */
    @Override
    public void consultarVentasDefault() {
        try {
            if(ventaBusquedaFragmentView != null){
                ventaBusquedaFragmentView.showProgress();
            }
            service = ClientApiService.getClient().create(InterfaceApiService.class);

            Call<List<Venta>> call = service.getVentasRangoDefault();

            call.enqueue(new Callback<List<Venta>>() {
                @Override
                public void onResponse(Call<List<Venta>> call, Response<List<Venta>> response) {
                    if(response != null){
                        organizarRespuesta(response.body());
                        //ventaBusquedaFragmentView.cargarAdapter(response.body());
                        //cargarAdapter(response.body());
                    } else {

                    }
                }

                @Override
                public void onFailure(Call<List<Venta>> call, Throwable t) {
                    if(ventaBusquedaFragmentView != null){
                        ventaBusquedaFragmentView.hideProgress();
                    }
                }
            });
        } catch (Exception e){
            e.printStackTrace();
            if(ventaBusquedaFragmentView != null){
                ventaBusquedaFragmentView.hideProgress();
            }
        }
    }

    /**
     * @Autor RagooS
     * @Descripccion Metodo permite consultar las ventas en la fecha
     * @Fecha 08/07/21
     */
    @Override
    public void consultarVentaEnFecha(String fecha) {

        try {

            if(iDetalleRegistroVentaFragmentView != null){
                iDetalleRegistroVentaFragmentView.showProgress("Consultando...");
            }

            service = service = ClientApiService.getClient().create(InterfaceApiService.class);

            Call<List<Venta>> call = service.getVentasEnFecha(fecha);

            call.enqueue(new Callback<List<Venta>>() {
                @Override
                public void onResponse(Call<List<Venta>> call, Response<List<Venta>> response) {
                    if(response != null){
                        validarRespuestaVentas(response.body());
                    } else {
                        if(iDetalleRegistroVentaFragmentView != null){
                            iDetalleRegistroVentaFragmentView.hideProgress();
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<Venta>> call, Throwable t) {
                    if(iDetalleRegistroVentaFragmentView != null){
                        iDetalleRegistroVentaFragmentView.hideProgress();
                    }
                }
            });

        }catch (Exception e){
            e.printStackTrace();
            if(iDetalleRegistroVentaFragmentView != null){
                iDetalleRegistroVentaFragmentView.hideProgress();
            }
        }
    }

    /**
     * @Autor RagooS
     * @Descripccion Metodo permite enviar datos de la venta al servidor
     * @Fecha 18/07/21
     */
    @Override
    public void realizarVenta(final Venta venta) {

        try{

            if(iDetalleRegistroVentaFragmentView != null){
                iDetalleRegistroVentaFragmentView.showProgress("Enviando Informacion...");
            }

            service = ClientApiService.getClient().create(InterfaceApiService.class);

            Call<Venta> call = service.saveVenta(venta);

            call.enqueue(new Callback<Venta>() {
                @Override
                public void onResponse(Call<Venta> call, Response<Venta> response) {
                    validarRespuestaVentaNueva(response, venta);
                }

                @Override
                public void onFailure(Call<Venta> call, Throwable t) {
                    if(iDetalleRegistroVentaFragmentView != null){
                        iDetalleRegistroVentaFragmentView.hideProgress();
                    }
                }
            });

        } catch (Exception e){
            e.printStackTrace();
            if(iDetalleRegistroVentaFragmentView != null){
                iDetalleRegistroVentaFragmentView.hideProgress();
            }
        }

    }

    /**
     * -------------- METODOS PROPIOS --------------------------------
     */

    /**
     * @Autor RagooS
     * @Descripccion Metodo permite organizar respuesta devuelta por consulta
     * @Fecha 07/07/21
     * @Param ventas devueltas en la respuesta
     */
    private void organizarRespuesta(List<Venta> ventas) {

        List<BusquedaVentas> busquedaVentas = new ArrayList<>();

        if(ventas != null && !ventas.isEmpty()){
            List<Venta> ventasComparar = new ArrayList<>(ventas);
            //List<Venta> ventasReciduo = new ArrayList<>(ventas);
            //ventasComparar.remove(0);

            for(Venta venta: ventas){
                //ventasIguales.add(venta);
                if(!ventasComparar.isEmpty()){
                    List<Venta> ventasIguales = new ArrayList<>();
                    for (int i = 0; i < ventasComparar.size();){
                        if((venta.getId() != ventasComparar.get(i).getId()) && (Utilidades.formatearFecha(venta.getFecha()).equals(Utilidades.formatearFecha(ventasComparar.get(i).getFecha())))){
                            if(!buscarIgual(venta, ventasIguales)){
                                ventasIguales.add(venta);
                            }
                            if(!buscarIgual(ventasComparar.get(i), ventasIguales)){
                                ventasIguales.add(ventasComparar.get(i));
                            }
                            ventasComparar.remove(i);
                        } else if((venta.getId() == ventasComparar.get(i).getId()) && (Utilidades.formatearFecha(venta.getFecha()).equals(Utilidades.formatearFecha(ventasComparar.get(i).getFecha())))) {
                            if(!buscarIgual(venta, ventasIguales)){
                                ventasIguales.add(venta);
                            }
                            ventasComparar.remove(i);
                        }else {
                            i++;
                        }
                    }
                    if(!ventasIguales.isEmpty()){
                        ingresarObjetosVentaBusqueda(busquedaVentas, ventasIguales);
                    }
                }
            }

            /*if(ventasIguales != null && !ventasIguales.isEmpty()){
                BusquedaVentas busqueda = new BusquedaVentas();
                busqueda.setFecha(ventasIguales.get(0).getFecha());
                List<BusquedaClientesVenta> clientesVenta = new ArrayList<>();
                for (Venta ventaIgual: ventasIguales){
                    BusquedaClientesVenta busquedaClientesVenta = new BusquedaClientesVenta();

                    busquedaClientesVenta.setCliente(ventaIgual.getCliente());
                    busquedaClientesVenta.setPrecioVenta(ventaIgual.getTotal());

                    clientesVenta.add(busquedaClientesVenta);

                }
                busqueda.setClientesVenta(clientesVenta);
                busquedaVentas.add(busqueda);
            }*/

            if(ventasComparar != null && !ventasComparar.isEmpty()){
                for (Venta ventaAdd: ventasComparar){
                    BusquedaVentas busqueda = new BusquedaVentas();
                    List<BusquedaClientesVenta> clientesVenta = new ArrayList<>();
                    busqueda.setFecha(ventaAdd.getFecha());
                    BusquedaClientesVenta busquedaClientesVenta = new BusquedaClientesVenta();

                    busquedaClientesVenta.setCliente(ventaAdd.getCliente());
                    busquedaClientesVenta.setPrecioVenta(ventaAdd.getTotal());

                    clientesVenta.add(busquedaClientesVenta);

                    busqueda.setClientesVenta(clientesVenta);
                    busquedaVentas.add(busqueda);
                }
            }

            //cargarAdapter(busquedaVentas);
            if(busquedaVentas != null && !busquedaVentas.isEmpty()){
                busquedaVentas = calcularTotales(busquedaVentas);
                busquedaVentas = ordenarBusquedaPorFecha(busquedaVentas);
                if(ventaBusquedaFragmentView != null){
                    ventaBusquedaFragmentView.hideProgress();
                    ventaBusquedaFragmentView.cargarAdapter(busquedaVentas);
                }
            }
        } else {
            if(ventaBusquedaFragmentView != null){
                ventaBusquedaFragmentView.hideProgress();
            }
        }
    }

    /**
     * @Autor RagooS
     * @Descripccion Metodo permite validar la respuesta del metodo de consulta ventas
     * @Fecha 08/07/21
     * @Param ventas devueltas en la respuesta
     */
    private void validarRespuestaVentas(List<Venta> ventas) {

        if(ventas != null && !ventas.isEmpty()){
            if(iDetalleRegistroVentaFragmentView != null){
                iDetalleRegistroVentaFragmentView.hideProgress();
                iDetalleRegistroVentaFragmentView.cargarAdapter(ventas);
            }
        }
    }

    /**
     * @Autor RagooS
     * @Descripccion Metodo permite calcular el total de la venta en la fecha
     * @Fecha 08/07/21
     * @Param busquedaVentas devueltas en la respuesta
     */
    private List<BusquedaVentas> calcularTotales(List<BusquedaVentas> busquedaVentas) {

        for (int i = 0; i < busquedaVentas.size(); i++){
            Double total = 0.0;
            if (busquedaVentas.get(i).getClientesVenta() != null && !busquedaVentas.get(i).getClientesVenta().isEmpty()){
                for (BusquedaClientesVenta venta: busquedaVentas.get(i).getClientesVenta()){
                    total += venta.getPrecioVenta();
                }
            }
            busquedaVentas.get(i).setTotal(total);
        }

        return busquedaVentas;
    }

    /**
     * @Autor RagooS
     * @Descripccion Metodo permite ingresar los objetos a la lista de BusquedaVentas
     * @Fecha 08/07/21
     * @Param busquedaVentas a ingresar
     * @param ventas
     */
    public void ingresarObjetosVentaBusqueda(List<BusquedaVentas> busquedaVentas, List<Venta> ventas){
        BusquedaVentas busqueda = new BusquedaVentas();
        busqueda.setFecha(ventas.get(0).getFecha());
        List<BusquedaClientesVenta> clientesVenta = new ArrayList<>();

        for (Venta ventaAdd: ventas){

            BusquedaClientesVenta busquedaClientesVenta = new BusquedaClientesVenta();

            busquedaClientesVenta.setCliente(ventaAdd.getCliente());
            busquedaClientesVenta.setPrecioVenta(ventaAdd.getTotal());

            clientesVenta.add(busquedaClientesVenta);


        }

        busqueda.setClientesVenta(clientesVenta);
        busquedaVentas.add(busqueda);
    }

    /**
     * @Autor RagooS
     * @Descripccion Metodo permite ordenar Lista de objetos por fecha
     * @Fecha 07/07/21
     * @param busquedaVentas Lista a ordenar
     * @return List<BusquedaVentas> lista de objetos ordenados por fecha
     */
    private List<BusquedaVentas> ordenarBusquedaPorFecha(List<BusquedaVentas> busquedaVentas) {

        for(int i = 0; i < busquedaVentas.size(); i++){
            for(int j = 0; j < busquedaVentas.size() - 1; j++){
                if(busquedaVentas.get(j).getFecha().before(busquedaVentas.get(j+1).getFecha())){
                    BusquedaVentas busquedaVentasTemp1 = busquedaVentas.get(j);
                    BusquedaVentas busquedaVentasTemp2 = busquedaVentas.get(j+1);
                    busquedaVentas.set(j, busquedaVentasTemp2);
                    busquedaVentas.set(j + 1, busquedaVentasTemp1);
                }
            }
        }

        return busquedaVentas;
    }

    /**
     * @Autor RagooS
     * @Descripccion Metodo permite Identificar si una venta se encuentra en la lista
     * @Fecha 07/07/21
     * @param buscado Objeto a buscar
     * @param buscar Lista de objetos a buscar
     * @return boolean true si lo encuentra de lo contrario false
     */
    private boolean buscarIgual(Venta buscado, List<Venta> buscar) {
        boolean encontrado = false;
        if( buscar!= null && !buscar.isEmpty()){
            for ( Venta v: buscar){
                if(v.getId() == buscado.getId()){
                    encontrado = true;
                }
            }
        }
        return encontrado;
    }

    /**
     * @Autor RagooS
     * @Descripccion Metodo permite validar respuesta venta realizada
     * @Fecha 18/07/21
     * @param response objeto venta insertada
     * @param venta objeto venta enviada
     */
    private void validarRespuestaVentaNueva(Response<Venta> response, Venta venta) {

        if(response != null && response.body() != null){
            if(response.body().getId() > 0 ){
                if(response.body().getCliente() != null && (response.body().getCliente().getId() == venta.getCliente().getId())){
                    if(response.body().getItems() != null && (response.body().getItems().size() == venta.getItems().size())){
                        System.out.println("Venta Exitosa....");

                        if(iDetalleRegistroVentaFragmentView != null){
                            iDetalleRegistroVentaFragmentView.hideProgress();
                            iDetalleRegistroVentaFragmentView.mostrarDialogoInformativo("Exito", "Se ha realizado la venta con exito!..");
                        }

                    } else {
                        System.out.println("Venta Error....");
                    }
                } else {
                    System.out.println("Venta Error....");
                }
            } else {
                System.out.println("Venta Error....");
            }
        } else {
            System.out.println("Venta Error....");
        }
    }

}
