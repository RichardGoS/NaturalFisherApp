package com.example.naturalfisherapp.view.interfaces.dialog;

import com.example.naturalfisherapp.data.models.Producto;

import java.util.List;

/**
 * de RagooS
 * Autor: Richard Gomez O.
 * Para: EmpresaDevelopers.Frontend.NaturalFisher
 * Fecha: 18/07/2021
 */

public interface IAgregarProductoDialogFragment {

    void showProgress(String mensaje);
    void hideProgress();

    void dismissDialog();

    void cargarAdapter(List<Producto> productos);

}
