package com.example.naturalfisherapp.view.interfaces.dialog;

/**
 * Fase 4 Tarea 3
 * de RagooS
 * Autor: Richard Gomez O.
 * Para: EmpresaDevelopers.Frontend.NaturalFisher
 * Fecha: 01/10/2022
 */
public interface IDetalleProveedorDialogFragmentView {

    void showProgress(String mensaje);
    void hideProgress();
    void dismissDialog();
    void mostrarMensaje(String tipoMensaje);
    void eliminarProveedor();

}
