package com.example.naturalfisherapp.data.models.interpretes;

import com.example.naturalfisherapp.data.models.Producto;
import com.example.naturalfisherapp.data.models.Promocion;

import java.util.List;

/**
 * de RagooS
 * Autor: Richard Gomez O.
 * Para: EmpresaDevelopers.Frontend.NaturalFisher
 * Fecha: 06/05/2022
 */

public class ProductosTransporte {

    private List<Producto> productos;
    private List<Promocion> promocion;

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }

    public List<Promocion> getPromocion() {
        return promocion;
    }

    public void setPromocion(List<Promocion> promocion) {
        this.promocion = promocion;
    }
}
