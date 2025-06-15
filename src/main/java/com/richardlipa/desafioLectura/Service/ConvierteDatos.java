package com.richardlipa.desafioLectura.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.richardlipa.desafioLectura.Interface.IConvierteDatos;

public class ConvierteDatos implements IConvierteDatos {
private ObjectMapper objectMapper = new ObjectMapper();// indispensable para mapear y leero un objeto json

    @Override
    public <T> T obtenerDatos(String json, Class<T> clase) {
        try{
            return  objectMapper.readValue(json, clase);
        }catch (JsonProcessingException e){
            throw  new RuntimeException(e.getMessage());
        }

    }



}
