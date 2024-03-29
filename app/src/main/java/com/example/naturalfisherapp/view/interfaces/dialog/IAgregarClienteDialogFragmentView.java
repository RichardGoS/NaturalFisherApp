package com.example.naturalfisherapp.view.interfaces.dialog;

import com.example.naturalfisherapp.data.models.Cliente;
import com.example.naturalfisherapp.data.models.Producto;

import java.util.List;

/**
 * de RagooS
 * Autor: Richard Gomez O.
 * Para: EmpresaDevelopers.Frontend.NaturalFisher
 * Fecha: 17/07/2021
 */

public interface IAgregarClienteDialogFragmentView {

    void showProgress(String mensaje);
    void hideProgress();
    void dismissDialog();
    void agregarNombreClientesAutocomplete(String[] nombres);
    void goToVentaDetalle(Cliente client);
    void mostrarMensaje(Cliente cliente, String tipoMensaje, boolean isVenta);

}
