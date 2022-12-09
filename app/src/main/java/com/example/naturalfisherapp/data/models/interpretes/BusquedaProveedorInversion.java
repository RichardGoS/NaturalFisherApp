package com.example.naturalfisherapp.data.models.interpretes;

import com.example.naturalfisherapp.data.models.Proveedor;

/**
 * Fase 4 Tarea 3
 * de RagooS
 * Autor: Richard Gomez O.
 * Para: EmpresaDevelopers.Frontend.NaturalFisher
 * Fecha: 25/09/2022
 */
public class BusquedaProveedorInversion {

    private Proveedor proveedor;
    private Double precioInversion;

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public Double getPrecioInversion() {
        return precioInversion;
    }

    public void setPrecioInversion(Double precioInversion) {
        this.precioInversion = precioInversion;
    }
}
