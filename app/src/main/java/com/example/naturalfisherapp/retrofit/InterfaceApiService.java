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

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET("producto/productos")
    Call<List<Producto>> getProductos();

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET("venta/ventas")
    Call<List<Venta>> getVentas();

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET("venta/ventasRangoDefault")
    Call<List<Venta>> getVentasRangoDefault();

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET("venta/ventasEnFecha?")
    Call<List<Venta>> getVentasEnFecha(@Query("fecha") String fecha );

    @POST("/appi/cliente/save")
    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    Call<Cliente> saveCliente(@Body Cliente clienteNew);

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET("cliente/clientes")
    Call<List<Cliente>> getClientes();

    @POST("/appi/venta/save")
    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    Call<Venta> saveVenta(@Body Venta ventaNew);

}
