package com.example.naturalfisherapp.view.interfaces;

import com.example.naturalfisherapp.data.models.Producto;

import java.util.List;

/**
 * de RagooS
 * Autor: Richard Gomez O.
 * Para: EmpresaDevelopers.Frontend.NaturalFisher
 * Fecha: 09/07/2021
 */

public interface IProductoBusquedaFragmentView {

    void cargarAdapter(List<Producto> productos);
    void showProgress();
    void hideProgress();
    void actualizarDatos();
}
