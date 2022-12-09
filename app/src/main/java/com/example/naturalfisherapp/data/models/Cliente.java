package com.example.naturalfisherapp.data.models;

/**
 * de RagooS
 * Autor: Richard Gomez O.
 * Para: EmpresaDevelopers.Frontend.NaturalFisher
 * Fecha: 11/06/2021
 */

public class Cliente {

    private Long id;
    private String nombre;
    private String direccion;
    private String telefono;
    private String estado;

    /**
     * Fase 4 Tarea 2
     * @author RagooS
     * @fecha 30/07/2022
     * @descripcion Se agregan variables nuevas
     */
    private String telefono_respaldo;
    private String direccion_respaldo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     * Fase 4 Tarea 2
     * @author RagooS
     * @fecha 30/07/2022
     * @descripcion metodos get y  set de las variables creadas
     */
    public String getTelefono_respaldo() {
        return telefono_respaldo;
    }

    public void setTelefono_respaldo(String telefono_respaldo) {
        this.telefono_respaldo = telefono_respaldo;
    }

    public String getDireccion_respaldo() {
        return direccion_respaldo;
    }

    public void setDireccion_respaldo(String direccion_respaldo) {
        this.direccion_respaldo = direccion_respaldo;
    }
}
