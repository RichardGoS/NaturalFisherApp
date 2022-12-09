package com.example.naturalfisherapp.data.models;

import java.util.Date;

/**
 * Fase 4 Tarea 1
 * de RagooS
 * Autor: Richard Gomez O.
 * Para: EmpresaDevelopers.Frontend.NaturalFisher
 * Fecha: 07/08/2022
 */
public class EstadisticasMesProducto {
    private Long id;
    private Date fecha;
    private Date fecha_ultima_ejecucion;
    private Double cant_peso;
    private Double total;
    private Producto producto;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Date getFecha_ultima_ejecucion() {
        return fecha_ultima_ejecucion;
    }

    public void setFecha_ultima_ejecucion(Date fecha_ultima_ejecucion) {
        this.fecha_ultima_ejecucion = fecha_ultima_ejecucion;
    }

    public Double getCant_peso() {
        return cant_peso;
    }

    public void setCant_peso(Double cant_peso) {
        this.cant_peso = cant_peso;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
}
