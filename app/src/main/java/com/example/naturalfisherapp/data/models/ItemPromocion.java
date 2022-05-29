package com.example.naturalfisherapp.data.models;

/**
 * de RagooS
 * Autor: Richard Gomez O.
 * Para: EmpresaDevelopers.Frontend.NaturalFisher
 * Fecha: 24/06/2021
 */

public class ItemPromocion {
    private Long id;
    private Producto producto;
    private Double precio_venta;
    private Double cant_peso;
    private Double total;

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

    public Double getCant_peso() {
        return cant_peso;
    }

    public void setCant_peso(Double cant_peso) {
        this.cant_peso = cant_peso;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Double getPrecio_venta() {
        return precio_venta;
    }

    public void setPrecio_venta(Double precio_venta) {
        this.precio_venta = precio_venta;
    }
}
