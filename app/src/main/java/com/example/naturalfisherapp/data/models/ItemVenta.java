package com.example.naturalfisherapp.data.models;

/**
 * de RagooS
 * Autor: Richard Gomez O.
 * Para: EmpresaDevelopers.Frontend.NaturalFisher
 * Fecha: 24/06/2021
 */

public class ItemVenta {

    private Long id;
    private Producto producto;
    private Double cant_peso;
    private String usa_precio_distinto;
    private Double precio_distinto;
    private Double total;
    private PromocionVenta promocion_venta;

    private int codigoItemApp;

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

    public String getUsa_precio_distinto() {
        return usa_precio_distinto;
    }

    public void setUsa_precio_distinto(String usa_precio_distinto) {
        this.usa_precio_distinto = usa_precio_distinto;
    }

    public Double getPrecio_distinto() {
        return precio_distinto;
    }

    public void setPrecio_distinto(Double precio_distinto) {
        this.precio_distinto = precio_distinto;
    }

    public PromocionVenta getPromocionVenta() {
        return promocion_venta;
    }

    public void setPromocionVenta(PromocionVenta promocionVenta) {
        this.promocion_venta = promocionVenta;
    }

    public int getCodigoItemApp() {
        return codigoItemApp;
    }

    public void setCodigoItemApp(int codigoItemApp) {
        this.codigoItemApp = codigoItemApp;
    }
}
