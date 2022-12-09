package com.example.naturalfisherapp.view.interfaces.adapter;

import com.example.naturalfisherapp.data.models.ItemInversion;
import com.example.naturalfisherapp.data.models.Producto;

import java.util.List;

public interface IInversionRegistroHolderView {

    void agregarProducto(Producto producto);
    void mostrarLlProcesos();
    void calcularPrecioTotal();
    void habilitarDeahabilitarBtnConfirmar(boolean habilita);
    void cargarAdapterItems(List<ItemInversion> items, String modo);
    void eliminarProductoItem(Producto producto);
    void realizarInversion();
    void goToInversionActivity();
}
