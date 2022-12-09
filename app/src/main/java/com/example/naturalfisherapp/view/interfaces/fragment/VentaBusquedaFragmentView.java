package com.example.naturalfisherapp.view.interfaces.fragment;

import com.example.naturalfisherapp.data.models.Venta;
import com.example.naturalfisherapp.data.models.interpretes.BusquedaVentas;
import com.example.naturalfisherapp.data.models.interpretes.DetalleVentas;

import java.util.List;

/**
 * de RagooS
 * Autor: Richard Gomez O.
 * Para: EmpresaDevelopers.Frontend.NaturalFisher
 * Fecha: 06/07/2021
 */

public interface VentaBusquedaFragmentView {

    void cargarDatos(List<BusquedaVentas> busquedaVentas, DetalleVentas detalleVentas);
    void showProgress();
    void hideProgress();

}
