package com.example.naturalfisherapp.view.interfaces.dialog;

/**
 * de RagooS
 * Autor: Richard Gomez O.
 * Para: EmpresaDevelopers.Frontend.NaturalFisher
 * Fecha: 03/01/2022
 */
public interface IDetalleClienteDialogFragment {

    void showProgress(String mensaje);
    void hideProgress();
    void dismissDialog();
    void mostrarMensaje(String tipoMensaje);
    void eliminarCliente();

}
