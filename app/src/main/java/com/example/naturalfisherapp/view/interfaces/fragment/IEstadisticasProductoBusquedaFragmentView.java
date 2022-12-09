package com.example.naturalfisherapp.view.interfaces.fragment;

import com.example.naturalfisherapp.data.models.EstadisticasMesProducto;
import com.example.naturalfisherapp.data.models.interpretes.DetalleEstadistica;

import java.util.List;

/**
 * Fase 4 Tarea 1
 * de RagooS
 * Autor: Richard Gomez O.
 * Para: EmpresaDevelopers.Frontend.NaturalFisher
 * Fecha: 07/08/2022
 */
public interface IEstadisticasProductoBusquedaFragmentView {
    void showProgress(String mensaje);
    void hideProgress();
    void cargarAdapter(DetalleEstadistica detalleEstadistica);
}
