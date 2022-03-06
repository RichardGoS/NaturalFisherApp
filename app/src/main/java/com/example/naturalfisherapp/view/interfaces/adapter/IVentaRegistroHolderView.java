package com.example.naturalfisherapp.view.interfaces.adapter;

import com.example.naturalfisherapp.data.models.ItemVenta;
import com.example.naturalfisherapp.data.models.Producto;

import java.util.List;

/**
 * de RagooS
 * Autor: Richard Gomez O.
 * Para: EmpresaDevelopers.Frontend.NaturalFisher
 * Fecha: 18/07/2021
 */

public interface IVentaRegistroHolderView {

    void cargarAdapterItems(List<ItemVenta> items, String modo);

    void agregarProducto(Producto producto);

    void mostrarLlProcesos();

    void habilitarDeahabilitarBtnConfirmarVenta(boolean habilita);

    void calcularPrecioTotal();

    void realizarVenta();

    void eliminarItemVenta(Producto producto);

    //void mostrarDialogoInformativo(String tipo, String informacion);
}
