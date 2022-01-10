package com.example.naturalfisherapp.utilidades;

public enum EnumVariables {

    /**
     * Variables Configuracion
     */
    DIRECCION_IP_SERVIDOR("DIRECCION_IP_SERVIDOR"),
    PUERTO_SERVIDOR("PUERTO_SERVIDOR");

    private final String valor;

    EnumVariables(String pCodigo){
        this.valor = pCodigo;
    }

    public String getValor(){
        return valor;
    }
}
