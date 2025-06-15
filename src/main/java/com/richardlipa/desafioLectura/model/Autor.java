package com.richardlipa.desafioLectura.model;

import com.richardlipa.desafioLectura.DTO.AutorDTO;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "autores")
public class Autor {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private String nombre;
        private Integer fechaNacimiento;
        private Integer anioFallecimiento;

        @ManyToMany(mappedBy = "autores", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
       // private List<Libro> libros = new ArrayList<>();
        private  Set<Libro> libros = new HashSet<>();

        // Método para crear Autor desde AutorDTO
        public static Autor fromDTO(AutorDTO autorDTO) {
            Autor autor = new Autor();
            autor.setNombre(autorDTO.nombre());

            // Convertir fechas de String a Integer
            try {
                if (autorDTO.fechaNacimiento() != null && !autorDTO.fechaNacimiento().isEmpty()) {
                    autor.setFechaNacimiento(Integer.parseInt(autorDTO.fechaNacimiento()));
                }
                if (autorDTO.anioFallecimiento() != null && !autorDTO.anioFallecimiento().isEmpty()) {
                    autor.setAnioFallecimiento(Integer.parseInt(autorDTO.anioFallecimiento()));
                }
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Formato de fecha inválido", e);
            }

            return autor;
        }

        // Getters y setters
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public String getNombre() { return nombre; }
        public void setNombre(String nombre) { this.nombre = nombre; }
        public Integer getFechaNacimiento() { return fechaNacimiento; }
        public void setFechaNacimiento(Integer fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }
        public Integer getAnioFallecimiento() { return anioFallecimiento; }
        public void setAnioFallecimiento(Integer anioFallecimiento) { this.anioFallecimiento = anioFallecimiento; }
       /* public List<Libro> getLibros() { return libros; }
        public void setLibros(List<Libro> libros) { this.libros = libros; }*/

    public Set<Libro> getLibros() {
        return libros;
    }

    public void setLibros(Set<Libro> libros) {
        this.libros = libros;
    }


    @Override
    public String toString() {
        return
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", fechaNacimiento=" + fechaNacimiento +
                ", anioFallecimiento=" + anioFallecimiento
                ;
    }
}