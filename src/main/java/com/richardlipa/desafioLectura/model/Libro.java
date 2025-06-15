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
      //  private List<Autor> autores = new ArrayList<>();
        private Set<Autor> autores = new HashSet<>();
        private Integer totalDescargas;

        // Método para crear Libro desde LibroDTO
        public static Libro fromDTO(LibroDTO libroDTO) {
            Libro libro = new Libro();
            libro.setTitulo(libroDTO.titulo());

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
      //  public List<Autor> getAutores() { return autores; }
      //  public void setAutores(List<Autor> autores) { this.autores = autores; }
        public Integer getTotalDescargas() { return totalDescargas; }
        public void setTotalDescargas(Integer totalDescargas) { this.totalDescargas = totalDescargas; }

    public Set<Autor> getAutores() {
        return autores;
    }

    public void setAutores(Set<Autor> autores) {

        this.autores=autores;
    }

/*    // Método helper para agregar autores /// cuando se una List<Autor> autores
    public void agregarAutor(Autor autor) {
        this.autores.add(autor);
        autor.getLibros().add(this);
    }

    // Método helper para remover autores
    public void removerAutor(Autor autor) {
        this.autores.remove(autor);
        autor.getLibros().remove(this);
    }*/

    //cuando se unsa Set<Autor> aurtores;

    // Método helper para agregar autores
    public void agregarAutor(Autor autor) {
        // Asegurarse de que el Set 'autores' esté inicializado (aunque el 'new HashSet<>()' ya lo hace)
        if (this.autores == null) {
            this.autores = new HashSet<>();
        }
        // Añade el autor al Set. Si ya existe, el Set no hará nada (garantía de unicidad).
        this.autores.add(autor);

        // Asegurarse de que el Set 'libros' del autor esté inicializado
        if (autor.getLibros() == null) {
            autor.setLibros(new HashSet<>());
        }
        // Añade este libro al Set de libros del autor.
        // Si ya existe, el Set no hará nada.
        autor.getLibros().add(this);
    }

    // Método helper para remover autores
    public void removerAutor(Autor autor) {
        // Solo remueve si el Set 'autores' no es nulo
        if (this.autores != null) {
            this.autores.remove(autor);
        }

        // Solo remueve si el Set 'libros' del autor no es nulo
        if (autor.getLibros() != null) {
            autor.getLibros().remove(this);
        }
    }

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
                ", autoresCount=" + (autores != null ? autores.size() : "null") +  // Evita el acceso directo al Set
                '}';
    }
}