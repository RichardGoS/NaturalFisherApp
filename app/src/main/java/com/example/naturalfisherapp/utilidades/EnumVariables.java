package com.example.naturalfisherapp.utilidades;

public enum EnumVariables {

    /**
     * Variables Configuracion
     */

    /*VARIABLES MODOS*/
    MODO_CREACION("MODO_CREACION"),
    MODO_CONSULTA("MODO_CONSULTA"),
    MODO_EDICION("MODO_EDICION"),
    /*VARIABLES CONFIGURACION*/
    DIRECCION_IP_SERVIDOR("DIRECCION_IP_SERVIDOR"),
    PUERTO_SERVIDOR("PUERTO_SERVIDOR"),
    /*FRAGMENTS*/
    FRAGMENT_PROMOCION_DETALLE("FRAGMENT_PROMOCION_DETALLE"),
    FRAGMENT_PRODUCTO_BUSQUEDA("FRAGMENT_PRODUCTO_BUSQUEDA"),
    FRAGMENT_VENTA_BUSQUEDA("FRAGMENT_VENTA_BUSQUEDA"),
    FRAGMENT_DETALLE_REGISTRO_VENTA("FRAGMENT_DETALLE_REGISTRO_VENTA");

    private final String valor;

    EnumVariables(String pCodigo){
        this.valor = pCodigo;
    }

    public String getValor(){
        return valor;
    }
}
