package com.example.naturalfisherapp.utilidades;

import com.example.naturalfisherapp.data.models.Bodega;
import com.example.naturalfisherapp.data.models.Cliente;
import com.example.naturalfisherapp.data.models.Producto;
import com.example.naturalfisherapp.data.models.interpretes.BusquedaVentas;
import com.example.naturalfisherapp.data.models.interpretes.DetalleVentas;
import com.example.naturalfisherapp.data.models.interpretes.GeneralProductos;
import com.example.naturalfisherapp.data.system.Configuracion;

import java.util.List;

/**
 * de RagooS
 * Autor: Richard Gomez O.
 * Para: EmpresaDevelopers.Frontend.NaturalFisher
 * Fecha: 17/07/2021
 */

public class InformacionSession {

    private static InformacionSession instance;

    private String fragmentActual;

    private Configuracion configuracion;
    private Cliente clienteNewVenta;

    private List<Cliente> clientesConsultados;
    private List<Bodega> bodegaList;
    private List<GeneralProductos> productosActivosVenta;
    private List<GeneralProductos> productosConsultados;
    private List<GeneralProductos> productosActivosPromo;
    private List<BusquedaVentas> busquedaVentas;
    private DetalleVentas detalleVentas;
    private int numProductos = 0;
    private int numPromocion = 0;

    public static InformacionSession getInstance() {
        if (instance == null) {
            instance = new InformacionSession();
        }
        return instance;
    }

    public static void setInstance(InformacionSession instance) {
        InformacionSession.instance = instance;
    }

    public List<Cliente> getClientesConsultados() {
        return clientesConsultados;
    }

    public void setClientesConsultados(List<Cliente> clientesConsultados) {
        this.clientesConsultados = clientesConsultados;
    }

    public List<GeneralProductos> getProductosConsultados() {
        return productosConsultados;
    }

    public void setProductosConsultados(List<GeneralProductos> productosConsultados) {
        this.productosConsultados = productosConsultados;
    }

    public Configuracion getConfiguracion() {
        return configuracion;
    }

    public void setConfiguracion(Configuracion configuracion) {
        this.configuracion = configuracion;
    }

    public Cliente getClienteNewVenta() {
        return clienteNewVenta;
    }

    public void setClienteNewVenta(Cliente clienteNewVenta) {
        this.clienteNewVenta = clienteNewVenta;
    }

    public List<Bodega> getBodegaList() {
        return bodegaList;
    }

    public void setBodegaList(List<Bodega> bodegaList) {
        this.bodegaList = bodegaList;
    }

    public List<GeneralProductos> getProductosActivosVenta() {
        return productosActivosVenta;
    }

    public void setProductosActivosVenta(List<GeneralProductos> productosActivosVenta) {
        this.productosActivosVenta = productosActivosVenta;
    }

    public List<GeneralProductos> getProductosActivosPromo() {
        return productosActivosPromo;
    }

    public void setProductosActivosPromo(List<GeneralProductos> productosActivosPromo) {
        this.productosActivosPromo = productosActivosPromo;
    }

    public String getFragmentActual() {
        return fragmentActual;
    }

    public void setFragmentActual(String fragmentActual) {
        this.fragmentActual = fragmentActual;
    }

    public List<BusquedaVentas> getVentasConsultadas() {
        return busquedaVentas;
    }

    public void setVentasConsultadas(List<BusquedaVentas> busquedaVentas) {
        this.busquedaVentas = busquedaVentas;
    }

    public DetalleVentas getDetalleVentas() {
        return detalleVentas;
    }

    public void setDetalleVentas(DetalleVentas detalleVentas) {
        this.detalleVentas = detalleVentas;
    }

    public int getNumProductos() {
        return numProductos;
    }

    public void setNumProductos(int numProductos) {
        this.numProductos = numProductos;
    }

    public int getNumPromocion() {
        return numPromocion;
    }

    public void setNumPromocion(int numPromocion) {
        this.numPromocion = numPromocion;
    }
}
