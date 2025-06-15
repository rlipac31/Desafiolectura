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
    CHINO("zh","Chino");
    // Puedes agregar más lenguajes según necesites


    private final String codigo;
    private final String nombre;

    Lenguaje(String codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
    }

    @JsonValue // Usado para serialización
    public String getCodigo() {
        return codigo;
    }

    // --- ¡LA CLAVE ESTÁ AQUÍ! Sobrescribe el método toString() ---
    @Override
    public String toString() {
        return nombre; // Imprime el nombre legible del idioma
    }

    @JsonCreator // Usado para deserialización
    public static Lenguaje fromCodigo(String codigo) {
        for (Lenguaje lenguaje : Lenguaje.values()) {
            if (lenguaje.codigo.equalsIgnoreCase(codigo)) {
                return lenguaje;
            }
        }
        throw new IllegalArgumentException("Código de lenguaje no válido: " + codigo);
    }

    // Mantén tus otros métodos si los necesitas
    public static Lenguaje fromEspaniol(String text) {
        for (Lenguaje lenguaje : Lenguaje.values()) {
            if (lenguaje.nombre.equalsIgnoreCase(text)) {
                return lenguaje;
            }
        }
        throw new IllegalArgumentException("Nombre de lenguaje no válido: " + text);
    }



    }