package com.richardlipa.desafioLectura.DTO;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;


@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosAutorDTO(
        @JsonAlias("results") List<AutorDTO> listaAutores
) {


}
