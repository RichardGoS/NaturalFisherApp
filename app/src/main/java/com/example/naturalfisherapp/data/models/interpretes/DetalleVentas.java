package com.example.naturalfisherapp.data.models.interpretes;

import com.example.naturalfisherapp.data.models.Venta;

import java.io.Serializable;
import java.util.List;

/**
 * de RagooS
 * Autor: Richard Gomez O.
 * Para: EmpresaDevelopers.Frontend.NaturalFisher
 * Fecha: 22/01/2022
 */

public class DetalleVentas implements Serializable {

    private List<Venta> ventas;
    private Double total;
    private String fecha;
    private int cantVentas;

    public List<Venta> getVentas() {
        return ventas;
    }

    public void setVentas(List<Venta> ventas) {
        this.ventas = ventas;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getCantVentas() {
        return cantVentas;
    }

    public void setCantVentas(int cantVentas) {
        this.cantVentas = cantVentas;
    }
}
