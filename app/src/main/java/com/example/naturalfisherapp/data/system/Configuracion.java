package com.example.naturalfisherapp.data.system;

/**
 * de RagooS
 * Autor: Richard Gomez O.
 * Para: EmpresaDevelopers.Frontend.NaturalFisher
 * Fecha: 23/09/2021
 */

public class Configuracion {

    private int id;
    private String ip;
    private String puerto;

    public Configuracion() {
    }

    public Configuracion(int id, String ip, String puerto) {
        this.id = id;
        this.ip = ip;
        this.puerto = puerto;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPuerto() {
        return puerto;
    }

    public void setPuerto(String puerto) {
        this.puerto = puerto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
