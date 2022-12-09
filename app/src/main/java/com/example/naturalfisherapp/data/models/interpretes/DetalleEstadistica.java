package com.example.naturalfisherapp.data.models.interpretes;

import com.example.naturalfisherapp.data.models.EstadisticasMesProducto;

import java.io.Serializable;
import java.util.List;

/**
 * Fase 4 Tarea 3
 * de RagooS
 * Autor: Richard Gomez O.
 * Para: EmpresaDevelopers.Backend.NaturalFisher
 * Fecha: 30/09/2022
 */
public class DetalleEstadistica implements Serializable {

    private List<EstadisticasMesProducto> estadisticas;
    private Double total;
    private String fecha;
    private int cantEstadisticas;

    public List<EstadisticasMesProducto> getEstadisticas() {
        return estadisticas;
    }

    public void setEstadisticas(List<EstadisticasMesProducto> estadisticas) {
        this.estadisticas = estadisticas;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getCantEstadisticas() {
        return cantEstadisticas;
    }

    public void setCantEstadisticas(int cantEstadisticas) {
        this.cantEstadisticas = cantEstadisticas;
    }
}
