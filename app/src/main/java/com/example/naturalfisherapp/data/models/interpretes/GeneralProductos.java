package com.example.naturalfisherapp.data.models.interpretes;

import com.example.naturalfisherapp.data.models.Producto;
import com.example.naturalfisherapp.data.models.Promocion;

/**
 * de RagooS
 * Autor: Richard Gomez O.
 * Para: EmpresaDevelopers.Frontend.NaturalFisher
 * Fecha: 09/05/2022
 */

public class GeneralProductos {

    private Producto producto;
    private Promocion promocion;

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Promocion getPromocion() {
        return promocion;
    }

    public void setPromocion(Promocion promocion) {
        this.promocion = promocion;
    }
}
