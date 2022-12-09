package com.example.naturalfisherapp.view.interfaces.fragment;

import com.example.naturalfisherapp.data.models.Proveedor;

import java.util.List;

/**
 * Fase 4 Tarea 3
 * de RagooS
 * Autor: Richard Gomez O.
 * Para: EmpresaDevelopers.Frontend.NaturalFisher
 * Fecha: 27/09/2022
 */
public interface IProveedorBusquedaFragmentView {

    void showProgress(String mensaje);
    void hideProgress();
    void mostrarDialogoInformativo(String tipo, String informacion);
    void cargarAdapter(List<Proveedor> proveedores);
}
