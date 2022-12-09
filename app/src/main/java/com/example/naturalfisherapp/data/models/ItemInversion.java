package com.example.naturalfisherapp.data.models;

/**
 * Fase 4 Tarea 3
 * de RagooS
 * Autor: Richard Gomez O.
 * Para: EmpresaDevelopers.Backend.NaturalFisher
 * Fecha: 21/09/2022
 */
public class ItemInversion {

    private Long id;
    private Producto producto;
    private Double precio_unitario;
    private Double cant_comprado;
    private Double precio_total;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Double getPrecio_unitario() {
        return precio_unitario;
    }

    public void setPrecio_unitario(Double precio_unitario) {
        this.precio_unitario = precio_unitario;
    }

    public Double getCant_comprado() {
        return cant_comprado;
    }

    public void setCant_comprado(Double cant_comprado) {
        this.cant_comprado = cant_comprado;
    }

    public Double getPrecio_total() {
        return precio_total;
    }

    public void setPrecio_total(Double precio_total) {
        this.precio_total = precio_total;
    }
}
