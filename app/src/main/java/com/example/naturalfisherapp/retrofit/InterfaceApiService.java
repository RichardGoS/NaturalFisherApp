package com.example.naturalfisherapp.retrofit;

import com.example.naturalfisherapp.data.models.Bodega;
import com.example.naturalfisherapp.data.models.Cliente;
import com.example.naturalfisherapp.data.models.EstadisticasMesProducto;
import com.example.naturalfisherapp.data.models.Inversion;
import com.example.naturalfisherapp.data.models.Producto;
import com.example.naturalfisherapp.data.models.Promocion;
import com.example.naturalfisherapp.data.models.Proveedor;
import com.example.naturalfisherapp.data.models.Venta;
import com.example.naturalfisherapp.data.models.interpretes.DetalleEstadistica;
import com.example.naturalfisherapp.data.models.interpretes.DetalleInversiones;
import com.example.naturalfisherapp.data.models.interpretes.DetalleVentas;
import com.example.naturalfisherapp.data.models.interpretes.ProductosTransporte;

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
     * ===================================================== PROVEEDOR =============================================================================================
     */

    /**
     * @Descripcion Metodo permite obtener la lista de bodegas de cada producto
     * @return List<Producto> Lista de productos
     */
    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET("proveedor/proveedores")
    Call<List<Proveedor>> getProveedores();

    /**
     * @Descripcion Metodo permite guardar un Proveedor
     * @return Proveedor objeto tipo Proveedor
     */
    @POST("proveedor/save/")
    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    Call<Proveedor> saveProveedor(@Body Proveedor proveedorNew);

    /**
     * @Descripcion Metodo permite eliminar una Inversion
     * @return boolean true correcto de lo contrario false
     */
    @POST("proveedor/delete/")
    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    Call<Boolean> eliminarProveedor(@Body Proveedor proveedor);

    /**
     * ===================================================== INVERSION =============================================================================================
     */

    /**
     * @Descripcion Metodo permite obtener la lista de inversiones todas
     * @return <List<Venta>> Lista de ventas
     */
    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET("/appi/inversion/inversionesEnFecha")
    Call<List<Inversion>> getInversionesEnFecha(@Query("fecha") String fecha);

    /**
     * @Descripcion Metodo permite obtener la lista de inversiones todas
     * @return <List<Venta>> Lista de ventas
     */
    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET("/appi/inversion/todasInversiones")
    Call<DetalleInversiones> getIodasInversionesDetalle();
    /**
     * @Descripcion Metodo permite obtener la lista de ventas en el rango de fechas por defecto
     * @return <List<Venta>> Lista de ventas
     */
    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET("/appi/inversion/inversionDetalleEnMes")
    Call<DetalleInversiones> getInversionesDetalleMes(@Query("fecha") String fecha);

    /**
     * @Descripcion Metodo permite guardar una Inversion
     * @return Inversion objeto tipo Inversion
     */
    @POST("inversion/save/")
    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    Call<Inversion> saveInversion(@Body Inversion inversionNew);

    /**
     * @Descripcion Metodo permite eliminar una Inversion
     * @return boolean true correcto de lo contrario false
     */
    @POST("inversion/delete/")
    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    Call<Boolean> eliminarInversion(@Body Inversion inversion);

    /**
     * ===================================================== PROMOCION =============================================================================================
     */

    /**
     * @Descripcion Metodo permite guardar un Producto
     * @return Producto objeto tipo Producto
     */
    @POST("promocion/save/")
    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    Call<Promocion> savePromocion(@Body Promocion promocionNew);

    /**
     * @Descripcion Metodo permite eliminar una promocion
     * @return boolean true correcto de lo contrario false
     */
    @POST("promocion/delete/")
    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    Call<Boolean> eliminarPromocion(@Body Promocion promocion);

    /**
     * ===================================================== INVENTARIO =============================================================================================
     */

    /**
     * @Descripcion Metodo permite realizar el inventario de todos los productos activos
     * @return boolean true correcto de lo contrario false
     */
    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET("inventario/realizarInventario")
    Call<Boolean> realizarInventarioGeneral();

    /**
     * ===================================================== BODEGA =============================================================================================
     */

    /**
     * @Descripcion Metodo permite obtener la lista de bodegas de cada producto
     * @return List<Producto> Lista de productos
     */
    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET("bodega/bodegas")
    Call<List<Bodega>> getBodegas();

    /**
     * ===================================================== PRODUCTOS =============================================================================================
     */

    /**
     * @Descripcion Metodo permite obtener la lista de productos
     * @return List<ProductosTransporte> Lista de productos
     */
    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET("producto/productos")
    Call<ProductosTransporte> getProductos();

    /**
     * @Descripcion Metodo permite obtener la lista de productos activos para las ventas
     * @return List<ProductosTransporte> Lista de productos
     */
    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET("producto/productosVenta")
    Call<ProductosTransporte> getProductosActivosVentas();

    /**
     * @Descripcion Metodo permite obtener la lista de productos activos para la promocion
     * @return List<Producto> Lista de productos
     */
    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET("producto/productosPromoVenta")
    Call<List<Producto>> getProductosPromoVentas();

    /**
     * @Descripcion Metodo permite obtener la lista de productos activos para la inversion
     * @return List<Producto> Lista de productos
     */
    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET("producto/productosActivosInversion")
    Call<ProductosTransporte> getProductosActivosInversion();

    /**
     * @Descripcion Metodo permite guardar un Producto
     * @return Producto objeto tipo Producto
     */
    @POST("producto/save/")
    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    Call<Producto> saveProducto(@Body Producto productoNew);

    /**
     * @Fecha 16/01/2022
     * @Descripcion Metodo permite eliminar un producto
     * @return Boolean true si se elimina con exito de lo contrario false
     */
    @POST("producto/eliminar")
    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    Call<Boolean> eliminarProducto(@Body Producto productoEliminar);

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
    @GET("/appi/venta/ventasRangoDefault")
    Call<DetalleVentas> getVentasRangoDefault();

    /**
     * @Descripcion Metodo permite obtener la lista de ventas en el rango de fechas por defecto
     * @return <List<Venta>> Lista de ventas
     */
    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET("/appi/venta/ventasDetalleEnMes")
    Call<DetalleVentas> getVentasDetalleMes(@Query("fecha") String fecha);

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
    @POST("cliente/save")
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
    @POST("cliente/eliminar")
    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    Call<Boolean> eliminarCliente(@Body Cliente clienteEliminar);

    /**
     * @Descripcion Metodo permite actualizar un cliente
     * @return Cliente objeto tipo cliente
     */
    @POST("cliente/save")
    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    Call<Cliente> actualizarCliente(@Body Cliente clienteNew);

    /**
     * Fase 4
     * ===================================================== ESTADISTICAS =============================================================================================
     */

    /**
     * @Descripcion Metodo permite obtener la lista de las estadisticas de los productos por mes
     * @return <List<EstadisticasMesProducto>> Lista de estadisticas
     */
    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET("estadisticas/estadisticaMesProductos?")
    Call<DetalleEstadistica> getEstadisticasProductosEnMes(@Query("fecha") String fecha );


}
