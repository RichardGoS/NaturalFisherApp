package com.example.naturalfisherapp.data.models;

import java.util.List;

/**
 * de RagooS
 * Autor: Richard Gomez O.
 * Para: EmpresaDevelopers.Frontend.NaturalFisher
 * Fecha: 10/05/2022
 */

public class PromocionVenta {

    private Long id;
    private Double totalCalculado;
    private Double porcentage;
    private Double ganancia;
    private List<ItemPromocionVenta> items_promocion;
    private Promocion promocion;
    private Double total;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<ItemPromocionVenta> getItemsPromocionVenta() {
        return items_promocion;
    }

    public void setItemsPromocionVenta(List<ItemPromocionVenta> itemsPromocionVenta) {
        this.items_promocion = itemsPromocionVenta;
    }

    public Promocion getPromocion() {
        return promocion;
    }

    public void setPromocion(Promocion promocion) {
        this.promocion = promocion;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
}
