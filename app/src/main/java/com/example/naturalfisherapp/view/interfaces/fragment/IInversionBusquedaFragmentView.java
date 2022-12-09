package com.example.naturalfisherapp.view.interfaces.fragment;

import com.example.naturalfisherapp.data.models.interpretes.BusquedaInversiones;
import com.example.naturalfisherapp.data.models.interpretes.DetalleInversiones;

import java.util.List;

/**
 * Fase 4 Tarea 3
 * de RagooS
 * Autor: Richard Gomez O.
 * Para: EmpresaDevelopers.Frontend.NaturalFisher
 * Fecha: 18/09/2022
 */
public interface IInversionBusquedaFragmentView {

    void showProgress();
    void hideProgress();
    void mostrarDialogoInformativo(String tipo, String informacion);
    void cargarDatos(List<BusquedaInversiones> busquedaInversiones, DetalleInversiones detalleInversiones);

}
