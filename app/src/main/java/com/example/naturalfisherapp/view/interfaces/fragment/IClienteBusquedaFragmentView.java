package com.example.naturalfisherapp.view.interfaces.fragment;

import com.example.naturalfisherapp.data.models.Cliente;
import com.example.naturalfisherapp.data.models.Producto;

import java.util.List;

/**
 * de RagooS
 * Autor: Richard Gomez O.
 * Para: EmpresaDevelopers.Frontend.NaturalFisher
 * Fecha: 29/12/2021
 */

public interface IClienteBusquedaFragmentView {

    void actualizarDatos();
    void cargarAdapter(List<Cliente> clientes);
    void showProgress(String mensaje);
    void hideProgress();

}
