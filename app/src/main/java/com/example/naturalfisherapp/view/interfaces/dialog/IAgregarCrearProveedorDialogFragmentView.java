package com.example.naturalfisherapp.view.interfaces.dialog;

import com.example.naturalfisherapp.data.models.Proveedor;

/**
 * Fase 4 Tarea 3
 * de RagooS
 * Autor: Richard Gomez O.
 * Para: EmpresaDevelopers.Frontend.NaturalFisher
 * Fecha: 26/09/2022
 */
public interface IAgregarCrearProveedorDialogFragmentView {

    void showProgress(String mensaje);
    void hideProgress();
    void dismissDialog();
    void agregarNombreNitAutocomplete(String[] nombres);
    void goToInversionDetalle(Proveedor proveedor);
    void mostrarMensaje(Proveedor proveedor, String tipoMensaje, boolean isInversion);
    void actualizarDatos();
}
