package com.example.naturalfisherapp.presenter.interfaces;

import com.example.naturalfisherapp.data.models.Venta;

/**
 * de RagooS
 * Autor: Richard Gomez O.
 * Para: EmpresaDevelopers.Frontend.NaturalFisher
 * Fecha: 06/07/2021
 */

public interface IVentaPresenter {

    void consultarVentasDefault();

    void consultarVentaEnFecha(String fecha);

    void realizarVenta(Venta venta);

}
