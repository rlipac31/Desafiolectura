package com.richardlipa.desafioLectura.Interface;

public interface IConvierteDatos {
    <T> T obtenerDatos(String json, Class<T> clase);//creamos un tipo de lista generico

}
