package com.example.naturalfisherapp.data.models;

/**
 * de RagooS
 * Autor: Richard Gomez O.
 * Para: EmpresaDevelopers.Frontend.NaturalFisher
 * Fecha: 13/02/2022
 */

public class Bodega {

    private Long id;
    private Producto producto;
    private Double cant_disponible;

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

    public Double getCant_disponible() {
        return cant_disponible;
    }

    public void setCant_disponible(Double cant_disponible) {
        this.cant_disponible = cant_disponible;
    }
}
