package com.example.naturalfisherapp.view.interfaces.fragment;

import com.example.naturalfisherapp.data.models.Venta;

import java.util.List;

/**
 * de RagooS
 * Autor: Richard Gomez O.
 * Para: EmpresaDevelopers.Frontend.NaturalFisher
 * Fecha: 08/07/2021
 */

public interface IDetalleRegistroVentaFragmentView {

    void cargarAdapter(List<Venta> ventas, String modo);
    void showProgress(String mensaje);
    void hideProgress();

    void mostrarDialogoInformativo(String tipo, String informacion);

    void goToVentaPrinsipalActivity();
}
