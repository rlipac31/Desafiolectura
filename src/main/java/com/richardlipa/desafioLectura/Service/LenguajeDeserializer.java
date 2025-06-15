package com.richardlipa.desafioLectura.Service;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.richardlipa.desafioLectura.model.Lenguaje;

import java.io.IOException;
public class LenguajeDeserializer extends StdDeserializer<Lenguaje> {

        public LenguajeDeserializer() {
            super(Lenguaje.class);
        }

        @Override
        public Lenguaje deserialize(com.fasterxml.jackson.core.JsonParser p,
                                    DeserializationContext ctxt) throws IOException {
            String value = p.getText();
            try {
                return Lenguaje.fromCodigo(value); // Usa tu método existente
            } catch (IllegalArgumentException e) {
                throw new IOException("Valor de lenguaje no válido: " + value, e);
            }
        }
    }