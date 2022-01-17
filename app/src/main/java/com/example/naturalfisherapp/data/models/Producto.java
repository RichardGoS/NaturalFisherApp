package com.example.naturalfisherapp.data.models;

/**
 * de RagooS
 * Autor: Richard Gomez O.
 * Para: EmpresaDevelopers.Frontend.NaturalFisher
 * Fecha: 11/06/2021
 */

public class Producto {

    private Long id;
    private String codigo;
    private String nombre;
    private String unidad;
    private Double precio;
    private String estado;
    private String descripcion_unidad;
    private Double variacion;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public String getDescripcion_unidad() {
        return descripcion_unidad;
    }

    public void setDescripcion_unidad(String descripcion_unidad) {
        this.descripcion_unidad = descripcion_unidad;
    }

    public Double getVariacion() {
        return variacion;
    }

    public void setVariacion(Double variacion) {
        this.variacion = variacion;
    }
}
