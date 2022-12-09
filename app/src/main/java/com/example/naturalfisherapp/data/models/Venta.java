package com.example.naturalfisherapp.data.models;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * de RagooS
 * Autor: Richard Gomez O.
 * Para: EmpresaDevelopers.Frontend.NaturalFisher
 * Fecha: 11/06/2021
 */

public class Venta implements Serializable {

    private Long id;
    private Cliente cliente;
    private Date fecha;
    private List<ItemVenta> items;
    private Double total;

    /**
     * Fase 4 Tarea 2
     * @author RagooS
     * @fecha 30/07/2022
     * @descripcion Se agregan variables nuevas
     */
    private String direccion;
    private String telefono;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public List<ItemVenta> getItems() {
        return items;
    }

    public void setItems(List<ItemVenta> items) {
        this.items = items;
    }

    /**
     * Fase 4 Tarea 2
     * @author RagooS
     * @fecha 30/07/2022
     * @descripcion metodos get y  set de las variables creadas
     */
    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
