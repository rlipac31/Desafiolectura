package com.richardlipa.desafioLectura.DTO;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.richardlipa.desafioLectura.model.Libro;

import java.util.List;
// con jackson se puede listar los datos que uno elija
@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosDTO(
        @JsonAlias("results") List<LibroDTO> libros
) {
}

