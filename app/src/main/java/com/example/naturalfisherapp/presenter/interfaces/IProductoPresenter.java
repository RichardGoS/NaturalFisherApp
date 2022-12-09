package com.example.naturalfisherapp.presenter.interfaces;

import com.example.naturalfisherapp.data.models.Producto;
import com.example.naturalfisherapp.data.models.Promocion;

/**
 * de RagooS
 * Autor: Richard Gomez O.
 * Para: EmpresaDevelopers.Frontend.NaturalFisher
 * Fecha: 09/07/2021
 */

public interface IProductoPresenter {

    void consultarProductos();
    void consultarProductosActivosVenta();
    void consultarProductosPromocion();
    void consultarProductosInversion();

    void guardarProducto(Producto producto);
    void eliminarProducto(Object producto);

    void guardarPromocion(Promocion promocion);
    void eliminarPromocion(Promocion promocion);

}
