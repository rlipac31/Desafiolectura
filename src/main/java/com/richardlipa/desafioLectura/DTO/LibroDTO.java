package com.richardlipa.desafioLectura.DTO;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.richardlipa.desafioLectura.Service.LenguajeDeserializer;
import com.richardlipa.desafioLectura.model.Autor;
import com.richardlipa.desafioLectura.model.Lenguaje;
import com.richardlipa.desafioLectura.model.Libro;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;

import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
public record LibroDTO(
        @JsonAlias("title")  String titulo,
        @JsonAlias("authors")  List<AutorDTO> autores, // ¡Correcto! Una lista de AutorDTOs
        @JsonAlias("languages") List<Lenguaje> idiomas,
        @JsonAlias("download_count")  String totalDescargas


) {


}

