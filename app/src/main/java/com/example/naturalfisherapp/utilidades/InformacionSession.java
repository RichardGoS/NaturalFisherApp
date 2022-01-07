package com.example.naturalfisherapp.utilidades;

import com.example.naturalfisherapp.data.models.Cliente;
import com.example.naturalfisherapp.data.models.Producto;
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
    private List<Cliente> clientesConsultados;
    private List<Producto> productosConsultados;
    private Configuracion configuracion;
    private Cliente clienteNewVenta;

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

    public List<Producto> getProductosConsultados() {
        return productosConsultados;
    }

    public void setProductosConsultados(List<Producto> productosConsultados) {
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
}
