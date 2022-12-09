package com.example.naturalfisherapp.presenter.interfaces;

import com.example.naturalfisherapp.data.models.Inversion;

import java.util.Date;

/**
 * Fase 4 Tarea 3
 * de RagooS
 * Autor: Richard Gomez O.
 * Para: EmpresaDevelopers.Frontend.NaturalFisher
 * Fecha: 18/09/2022
 */
public interface IInversionPresenter {

    void consultarInversionesEnMes(String fecha);
    void realizarInversion(Inversion inversion);
    void consultarInversionesEnFecha(String fecha);
}
