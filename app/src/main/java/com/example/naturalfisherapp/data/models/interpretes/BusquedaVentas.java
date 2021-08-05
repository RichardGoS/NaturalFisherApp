package com.example.naturalfisherapp.data.models.interpretes;

import java.util.Date;
import java.util.List;

/**
 * de RagooS
 * Autor: Richard Gomez O.
 * Para: EmpresaDevelopers.Frontend.NaturalFisher
 * Fecha: 07/07/2021
 */

public class BusquedaVentas {

    private Date fecha;
    private List<BusquedaClientesVenta> clientesVenta;
    private Double total;

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public List<BusquedaClientesVenta> getClientesVenta() {
        return clientesVenta;
    }

    public void setClientesVenta(List<BusquedaClientesVenta> clientesVenta) {
        this.clientesVenta = clientesVenta;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
}
