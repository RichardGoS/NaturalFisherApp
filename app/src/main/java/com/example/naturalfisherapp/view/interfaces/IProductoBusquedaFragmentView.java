package com.example.naturalfisherapp.view.interfaces;

import com.example.naturalfisherapp.data.models.Producto;
import com.example.naturalfisherapp.data.models.Promocion;
import com.example.naturalfisherapp.data.models.interpretes.GeneralProductos;

import java.util.List;

/**
 * de RagooS
 * Autor: Richard Gomez O.
 * Para: EmpresaDevelopers.Frontend.NaturalFisher
 * Fecha: 09/07/2021
 */

public interface IProductoBusquedaFragmentView {

    void cargarAdapter(List<GeneralProductos> productos, int numProductos, int NumPromocion);
    void showProgress();
    void hideProgress();
    void actualizarDatos();
    void goToPromocionDetalle(Promocion promocion, String modo);
}
