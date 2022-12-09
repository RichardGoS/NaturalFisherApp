package com.example.naturalfisherapp.data.models.interpretes;

import java.util.Date;
import java.util.List;

/**
 * Fase 4 Tarea 3
 * de RagooS
 * Autor: Richard Gomez O.
 * Para: EmpresaDevelopers.Frontend.NaturalFisher
 * Fecha: 25/09/2022
 */
public class BusquedaInversiones {

    private Date fecha;
    private List<BusquedaProveedorInversion> proveedorInversions;
    private Double total;

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public List<BusquedaProveedorInversion> getProveedorInversions() {
        return proveedorInversions;
    }

    public void setProveedorInversions(List<BusquedaProveedorInversion> proveedorInversions) {
        this.proveedorInversions = proveedorInversions;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
}
