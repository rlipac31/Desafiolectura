package com.richardlipa.desafioLectura.DTO;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.richardlipa.desafioLectura.model.Libro;

import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)

public record AutorDTO(
               @JsonAlias("name") String nombre,
               @JsonAlias("birth_year") String fechaNacimiento,
               @JsonAlias("death_year")  String anioFallecimiento

               

) {



    @Override
    public String toString() {
        return "AutorDTO{" +
                "nombre='" + nombre + '\'' +
                ", fechaNacimiento='" + fechaNacimiento + '\'' +
                ", anioFallecimiento='" + anioFallecimiento + '\'' +
                '}';
    }
}
