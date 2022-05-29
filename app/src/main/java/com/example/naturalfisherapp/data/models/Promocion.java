package com.example.naturalfisherapp.data.models;

import java.util.List;

/**
 * de RagooS
 * Autor: Richard Gomez O.
 * Para: EmpresaDevelopers.Frontend.NaturalFisher
 * Fecha: 24/06/2021
 */

public class Promocion {

    private Long id;
    private String nombre;
    private String descripccion;
    private Double total;
    private Double totalCalculado;
    private Double porcentage;
    private Double ganancia;
    private String estado;
    private List<ItemPromocion> items_productos;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public List<ItemPromocion> getItems() {
        return items_productos;
    }

    public void setItems(List<ItemPromocion> items_productos) {
        this.items_productos = items_productos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripccion() {
        return descripccion;
    }

    public void setDescripccion(String descripccion) {
        this.descripccion = descripccion;
    }

    public Double getTotalCalculado() {
        return totalCalculado;
    }

    public void setTotalCalculado(Double totalCalculado) {
        this.totalCalculado = totalCalculado;
    }

    public Double getPorcentage() {
        return porcentage;
    }

    public void setPorcentage(Double porcentage) {
        this.porcentage = porcentage;
    }

    public Double getGanancia() {
        return ganancia;
    }

    public void setGanancia(Double ganancia) {
        this.ganancia = ganancia;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
