package com.richardlipa.desafioLectura.Repository;

import com.richardlipa.desafioLectura.DTO.AutorDTO;
import com.richardlipa.desafioLectura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Long> {

    Optional<Autor> findByNombre(String nombre);

    @Query( "SELECT a FROM Autor a WHERE a.fechaNacimiento <= :anioBusqueda AND (a.anioFallecimiento IS NULL OR a.anioFallecimiento > :anioBusqueda)")
    List<Autor> listarAutoresVivos(Integer anioBusqueda);
}
