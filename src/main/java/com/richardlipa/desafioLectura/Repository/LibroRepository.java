package com.richardlipa.desafioLectura.Repository;

import com.richardlipa.desafioLectura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LibroRepository extends JpaRepository<Libro, Long> {
    Optional<Libro> findByTituloIgnoreCase(String titulo );
    @Query(value = """
        SELECT
            l.id AS idLibro,
            l.titulo AS tituloLibro,
            a.nombre AS nombreAutor,
            ll.lenguaje AS idioma
        FROM libros l
        JOIN libro_autor la ON l.id = la.libro_id
        JOIN autores a ON la.autor_id = a.id
        JOIN libro_lenguajes ll ON l.id = ll.libro_id
        """, nativeQuery = true)
    List<Object[]> listarLibros();
/*
    @Query(value = """
            SELECT
                l.id,
                l.titulo,
                a.nombre,
                lang
            FROM
                Libro l
            JOIN
                l.autores a
            JOIN
                l.lenguajes lang
            WHERE
                a.nombre ILIKE :nombreAutor
            """)*/
    @Query(value = """
              SELECT
                        l.id AS idLibro,
                        l.titulo AS tituloLibro,
                        a.nombre AS nombreAutor,
                        ll.lenguaje AS idioma
                    FROM libros l
                    JOIN libro_autor la ON l.id = la.libro_id
                    JOIN autores a ON la.autor_id = a.id
                    JOIN libro_lenguajes ll ON l.id = ll.libro_id
            		WHERE
            		 a.nombre ILIKE 'dickens%';
            """, nativeQuery = true)
    List<Object[]> librosPorAutor(String nombreAutor);
}
