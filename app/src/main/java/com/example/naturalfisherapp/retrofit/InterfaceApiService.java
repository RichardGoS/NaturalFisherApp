package com.example.naturalfisherapp.retrofit;

import com.example.naturalfisherapp.data.models.Cliente;
import com.example.naturalfisherapp.data.models.Producto;
import com.example.naturalfisherapp.data.models.Venta;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * de RagooS
 * Autor: Richard Gomez O.
 * Para: EmpresaDevelopers.Frontend.NaturalFisher
 * Fecha: 23/06/2021
 */

public interface InterfaceApiService {

    /**
     * @Descripcion Metodo permite obtener la lista de productos
     * @return List<Producto> Lista de productos
     */
    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET("producto/productos")
    Call<List<Producto>> getProductos();

    /**
     * ===================================================== VENTAS =============================================================================================
     */

    /**
     * @Descripcion Metodo permite obtener la lista de ventas
     * @return <List<Venta>> Lista de ventas
     */
    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET("venta/ventas")
    Call<List<Venta>> getVentas();

    /**
     * @Descripcion Metodo permite obtener la lista de ventas en el rango de fechas por defecto
     * @return <List<Venta>> Lista de ventas
     */
    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET("venta/ventasRangoDefault")
    Call<List<Venta>> getVentasRangoDefault();

    /**
     * @Descripcion Metodo permite obtener la lista de ventas en un rango de fecha indicado
     * @return <List<Venta>> Lista de ventas
     */
    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET("venta/ventasEnFecha?")
    Call<List<Venta>> getVentasEnFecha(@Query("fecha") String fecha );

    /**
     * @Descripcion Metodo permite guardar una venta
     * @return Venta objeto tipo venta
     */
    @POST("/appi/venta/save")
    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    Call<Venta> saveVenta(@Body Venta ventaNew);

    /**
     * ===================================================== CLIENTES =============================================================================================
     */

    /**
     * @Descripcion Metodo permite guardar un cliente
     * @return Cliente objeto tipo cliente
     */
    @POST("/appi/cliente/save")
    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    Call<Cliente> saveCliente(@Body Cliente clienteNew);

    /**
     * @Descripcion Metodo permite obtener la lista de clientes
     * @return <List<Cliente>> lista de clientes
     */
    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET("cliente/clientes")
    Call<List<Cliente>> getClientes();

    /**
     * @Fecha 03/01/2022
     * @Descripcion Metodo permite eliminar un cliente
     * @return Boolean true si se elimina con exito de lo contrario false
     */
    @POST("/appi/cliente/eliminar")
    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    Call<Boolean> eliminarCliente(@Body Cliente clienteEliminar);

    /**
     * @Descripcion Metodo permite actualizar un cliente
     * @return Cliente objeto tipo cliente
     */
    @POST("/appi/cliente/save")
    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    Call<Cliente> actualizarCliente(@Body Cliente clienteNew);


}
