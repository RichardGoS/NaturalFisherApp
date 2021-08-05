package com.example.naturalfisherapp.view.interfaces;

import com.example.naturalfisherapp.data.models.Venta;
import com.example.naturalfisherapp.data.models.interpretes.BusquedaVentas;

import java.util.List;

/**
 * de RagooS
 * Autor: Richard Gomez O.
 * Para: EmpresaDevelopers.Frontend.NaturalFisher
 * Fecha: 06/07/2021
 */

public interface VentaBusquedaFragmentView {

    void cargarAdapter(List<BusquedaVentas> busquedaVentas);
    void showProgress();
    void hideProgress();

}
