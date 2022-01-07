package com.example.naturalfisherapp.presenter.interfaces;

import com.example.naturalfisherapp.data.models.Cliente;

/**
 * de RagooS
 * Autor: Richard Gomez O.
 * Para: EmpresaDevelopers.Frontend.NaturalFisher
 * Fecha: 16/07/2021
 */

public interface IClientePresenter {

    void guardarCliente(Cliente cliente);
    void consultarClientes();
    void eliminarCliente(Cliente cliente);
    void actualizarCliente(Cliente cliente);
}
