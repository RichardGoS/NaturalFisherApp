package com.example.naturalfisherapp.view.interfaces.fragment;

import com.example.naturalfisherapp.data.models.Inversion;

import java.util.List;

/**
 * Fase 4 Tarea 3
 * de RagooS
 * Autor: Richard Gomez O.
 * Para: EmpresaDevelopers.Frontend.NaturalFisher
 * Fecha: 02/10/2022
 */
public interface IDetalleInversionFragmentView {

    void showProgress(String mensaje);
    void hideProgress();

    void mostrarDialogoInformativo(String tipo, String informacion);
    void cargarAdapter(List<Inversion> inversiones, String modo);
    void goToInversionActivity();

}
