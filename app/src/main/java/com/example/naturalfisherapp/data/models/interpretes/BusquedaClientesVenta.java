package com.example.naturalfisherapp.data.models.interpretes;

import com.example.naturalfisherapp.data.models.Cliente;

/**
 * de RagooS
 * Autor: Richard Gomez O.
 * Para: EmpresaDevelopers.Frontend.NaturalFisher
 * Fecha: 07/07/2021
 */

public class BusquedaClientesVenta {

    private Cliente cliente;
    private Double precioVenta;

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Double getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(Double precioVenta) {
        this.precioVenta = precioVenta;
    }
}
