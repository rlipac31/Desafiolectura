package com.richardlipa.desafioLectura.model;


import com.richardlipa.desafioLectura.DTO.LibroDTO;
import jakarta.persistence.*;

import java.util.*;

@Entity
@Table(name = "libros")
public class Libro {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
         @Column(nullable = false, unique = true)
        private String titulo;
        @ElementCollection(targetClass = Lenguaje.class, fetch = FetchType.EAGER)// <--- CAMBIAR AQUÍ A EAGER//asi evitamos Erro por Lazy(conexion peresosa)
        @Enumerated(EnumType.STRING)
        @CollectionTable(name = "libro_lenguajes", joinColumns = @JoinColumn(name = "libro_id"))
        @Column(name = "lenguaje")
        private List<Lenguaje> lenguajes = new ArrayList<>();
        // Relación ManyToOne con Autor: Muchos libros pueden ser de un solo autor.
        // @JoinColumn(name = "autor_id") especifica el nombre de la columna FK en la tabla 'libros'.
        // Esto creará la columna 'autor_id' en la tabla 'libros'.
        @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL) // EAGER para facilitar pruebas, LAZY es mejor para producción
        @JoinColumn(name = "nombreAutor", nullable = false) // Columna de clave foránea en la tabla libros
        private Autor autor;
        private Integer totalDescargas;

        // Método para crear Libro desde LibroDTO
        public static Libro fromDTO(LibroDTO libroDTO) {
            Libro libro = new Libro();
            libro.setTitulo(libroDTO.titulo());
           // libro.setAutor(Autor.fromDTO(libroDTO.autores().get(0)));


            // Convertir totalDescargas de String a Integer
            try {
                libro.setTotalDescargas(Integer.parseInt(libroDTO.totalDescargas()));
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Formato inválido para totalDescargas", e);
            }

            // Convertir idiomas usando tu enum Lenguaje
            libroDTO.idiomas().forEach(idioma -> {
                try {
                    libro.getLenguajes().add(Lenguaje.fromEspaniol(String.valueOf(idioma)));
                } catch (IllegalArgumentException e) {
                    System.err.println("Idioma no reconocido: " + idioma);
                    // Opcional: puedes agregar un lenguaje por defecto o ignorar
                }
            });

            return libro;
        }

        // Getters y setters
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public String getTitulo() { return titulo; }
        public void setTitulo(String titulo) { this.titulo = titulo; }
        public List<Lenguaje> getLenguajes() { return lenguajes; }
        public void setLenguajes(List<Lenguaje> lenguajes) { this.lenguajes = lenguajes; }
        public Integer getTotalDescargas() { return totalDescargas; }
        public void setTotalDescargas(Integer totalDescargas) { this.totalDescargas = totalDescargas; }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    // Intentar encontrar el autor por nombre en la DB para evitar duplicados






    // En tu entidad Libro.java
    @Override
    public String toString() {
        return "Libro{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                // No incluyas directamente "idiomas" ni "autores" aquí si son LAZY.
                // En su lugar, imprime el tamaño o una representación sencilla.
                ", totalDescargas=" + totalDescargas +
                ", idiomasCount=" + (lenguajes != null ? lenguajes.size() : "null") + // Evita el acceso directo a la lista
                ", autoresCount=" + (autor  != null ? autor: "null") +  // Evita el acceso directo al Set
                '}';
    }
}