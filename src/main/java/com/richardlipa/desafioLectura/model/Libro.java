package com.richardlipa.desafioLectura.model;


import com.richardlipa.desafioLectura.DTO.LibroDTO;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "libros")
public class Libro {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private String titulo;
        @ElementCollection(targetClass = Lenguaje.class, fetch = FetchType.EAGER)// <--- CAMBIAR AQUÍ A EAGER//asi evitamos Erro por Lazy(conexion peresosa)
        @Enumerated(EnumType.STRING)
        @CollectionTable(name = "libro_lenguajes", joinColumns = @JoinColumn(name = "libro_id"))
        @Column(name = "lenguaje")
        private List<Lenguaje> lenguajes = new ArrayList<>();

        @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
        @JoinTable(
                name = "libro_autor",
                joinColumns = @JoinColumn(name = "libro_id"),
                inverseJoinColumns = @JoinColumn(name = "autor_id")
        )

        private Set<Autor> autores = new HashSet<>();
        private Integer totalDescargas;

        // Método para crear Libro desde LibroDTO
        public static Libro fromDTO(LibroDTO libroDTO) {
            Libro libro = new Libro();
            libro.setTitulo(libroDTO.titulo());


            try {
                libro.setTotalDescargas(Integer.parseInt(libroDTO.totalDescargas()));
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Formato inválido para totalDescargas", e);
            }


            libroDTO.idiomas().forEach(idioma -> {
                try {
                    libro.getLenguajes().add(Lenguaje.fromEspaniol(String.valueOf(idioma)));
                } catch (IllegalArgumentException e) {
                    System.err.println("Idioma no reconocido: " + idioma);

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

        public Integer getTotalDescargas() { return totalDescargas; }
        public void setTotalDescargas(Integer totalDescargas) { this.totalDescargas = totalDescargas; }

    public Set<Autor> getAutores() {
        return autores;
    }

    public void setAutores(Set<Autor> autores) {

        this.autores=autores;
    }


    // Método helper para agregar autores
    public void agregarAutor(Autor autor) {

        if (this.autores == null) {
            this.autores = new HashSet<>();
        }

        this.autores.add(autor);


        if (autor.getLibros() == null) {
            autor.setLibros(new HashSet<>());
        }

        autor.getLibros().add(this);
    }


    public void removerAutor(Autor autor) {

        if (this.autores != null) {
            this.autores.remove(autor);
        }


        if (autor.getLibros() != null) {
            autor.getLibros().remove(this);
        }
    }


    @Override
    public String toString() {
        return "Libro{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +

                ", totalDescargas=" + totalDescargas +
                ", idiomasCount=" + (lenguajes != null ? lenguajes.size() : "null") + // Evita el acceso directo a la lista
                ", autoresCount=" + (autores != null ? autores.size() : "null") +  // Evita el acceso directo al Set
                '}';
    }
}