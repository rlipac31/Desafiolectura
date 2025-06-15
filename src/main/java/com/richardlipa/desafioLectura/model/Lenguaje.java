package com.richardlipa.desafioLectura.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Lenguaje {
    ESPANIOL("es","Espaniol"),
    INGLES("en","Ingles"),
    FRANCES("fr","Frances"),
    ALEMAN("de", "Aleman"),
    HUNGARO("hu", "Humgaro"),
    FILANDEZ("fi", "Filandidez"),
    HOLANDES("nl", "Holandes"),
    PORTUGUES("pt", "Portuguez"),
    ARABE("ar", "ARABE"),
    CHINO("zh","Chino"),
    DESCONOCIDO("unk", "Desconocido"); // <-- ¡Añade esta constante!



    private final String codigo;
    private final String nombre;

    Lenguaje(String codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
    }

    @JsonValue
    public String getCodigo() {
        return codigo;
    }

    @Override
    public String toString() {
        return nombre;
    }

    @JsonCreator
    public static Lenguaje fromCodigo(String codigo) {
        for (Lenguaje lenguaje : Lenguaje.values()) {
            if (lenguaje.codigo.equalsIgnoreCase(codigo)) {
                return lenguaje;
            }
        }

        System.err.println("Advertencia: Código de lenguaje no válido o no soportado: " + codigo); // Opcional: loggear la advertencia
        return DESCONOCIDO;
    }


    public static Lenguaje fromEspaniol(String text) {
        for (Lenguaje lenguaje : Lenguaje.values()) {
            if (lenguaje.nombre.equalsIgnoreCase(text)) {
                return lenguaje;
            }
        }
        System.err.println("Advertencia: Nombre de lenguaje en español no válido o no soportado: " + text);
        return DESCONOCIDO;
    }



    }