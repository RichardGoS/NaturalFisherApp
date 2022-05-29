package com.example.naturalfisherapp.view.interfaces.fragment;

import com.example.naturalfisherapp.data.models.ItemPromocion;
import com.example.naturalfisherapp.data.models.Producto;

import java.util.List;

/**
 * de RagooS
 * Autor: Richard Gomez O.
 * Para: EmpresaDevelopers.Frontend.NaturalFisher
 * Fecha: 30/04/2022
 */

public interface IPromocionDetalleFragmentView {

    void showProgress(String mensaje);
    void hideProgress();
    void cargarAdapter(List<ItemPromocion> itemPromocions, String modo);
    void agregarProducto(Producto producto);
    void eliminarProductoItem(Producto producto);
    void mostrarLlProcesos();
    void calcularPrecioTotal();
    void habilitarDeahabilitarBtnConfirmar(boolean habilita);
    void crearPromocion();
    void goToProductoActivity();
}
