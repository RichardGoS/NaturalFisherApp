package com.example.naturalfisherapp.presenter.interfaces;

import com.example.naturalfisherapp.data.models.Producto;

/**
 * de RagooS
 * Autor: Richard Gomez O.
 * Para: EmpresaDevelopers.Frontend.NaturalFisher
 * Fecha: 09/07/2021
 */

public interface IProductoPresenter {

    void consultarProductos();
    void guardarProducto(Producto producto);
    void eliminarProducto(Producto producto);

}
