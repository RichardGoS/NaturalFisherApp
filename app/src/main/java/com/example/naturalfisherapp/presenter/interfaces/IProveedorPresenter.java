package com.example.naturalfisherapp.presenter.interfaces;

import com.example.naturalfisherapp.data.models.Proveedor;

/**
 * Fase 4 Tarea 3
 * de RagooS
 * Autor: Richard Gomez O.
 * Para: EmpresaDevelopers.Frontend.NaturalFisher
 * Fecha: 27/09/2022
 */
public interface IProveedorPresenter {

    void consultarProveedores();
    void guardarProveedor(Proveedor proveedor);
    void eliminarProveedor(Proveedor proveedor);
}
