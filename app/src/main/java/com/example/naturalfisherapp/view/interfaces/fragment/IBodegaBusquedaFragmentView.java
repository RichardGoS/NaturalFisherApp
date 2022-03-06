package com.example.naturalfisherapp.view.interfaces.fragment;

import com.example.naturalfisherapp.data.models.Bodega;

import java.util.List;

/**
 * de RagooS
 * Autor: Richard Gomez O.
 * Para: EmpresaDevelopers.Frontend.NaturalFisher
 * Fecha: 13/02/2022
 */

public interface IBodegaBusquedaFragmentView {

    void cargarAdapter(List<Bodega> bodegas);
    void showProgress(String mensaje);
    void hideProgress();
    void actualizarDatos();

}
