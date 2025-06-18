package com.richardlipa.desafioLectura.Service;

import com.richardlipa.desafioLectura.DTO.AutorDTO;
import com.richardlipa.desafioLectura.DTO.LibroDTO;
import com.richardlipa.desafioLectura.Repository.AutorRepository;
import com.richardlipa.desafioLectura.Repository.LibroRepository;
import com.richardlipa.desafioLectura.model.Autor;
import com.richardlipa.desafioLectura.model.Libro;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class LibroService {

        private final LibroRepository libroRepository;
        private final AutorRepository autorRepository;

        public LibroService(LibroRepository libroRepository, AutorRepository autorRepository) {
            this.libroRepository = libroRepository;
            this.autorRepository = autorRepository;
        }

        public Libro guardarLibroDesdeDTO(LibroDTO libroDTO) {
            // Convertir LibroDTO a entidad Libro
            Libro libro = Libro.fromDTO(libroDTO);
            Optional<Libro> libroExiste = libroRepository.findByTituloIgnoreCase(libro.getTitulo());
                if(libroExiste.isPresent()){
                    System.out.println("Libro con el titulo: "+ libroExiste.get().getTitulo() +" ya esta registrado en la BD");
                    return null;
                }
            Autor autor = null;
            if (libroDTO.autores() != null && !libroDTO.autores().isEmpty()) {
                AutorDTO autorDTO = libroDTO.autores().get(0); // Tomamos el primer autor de la lista
                // Buscar autor por nombre (asumiendo que el nombre es único)
                Optional<Autor> autorExistente = autorRepository.findByNombreAutor(autorDTO.nombre());


                if (autorExistente.isPresent()) {
                    // Actualizar autor existente
                    autor = autorExistente.get();
                    System.out.println("Autor existente encontrado: " + autor.getNombreAutor());


                }else {
                    AutorDTO miautorDTO = new AutorDTO(autorDTO.nombre(), autorDTO.fechaNacimiento(), autorDTO.anioFallecimiento());
                    // Crear nuevo autor
                  //  autor = new Autor(miautorDTO);
                    Autor nuevoAutor = Autor.fromDTO(miautorDTO);
                    libro.setAutor(nuevoAutor);
                }

            }// Procesar cada autor del DTO




            return libroRepository.save(libro);
        }

       /* public   List<Object[]> listarLibrosBD(){
           // return  libroRepository.findAll();
            return  libroRepository.listarLibros();
        }*/

    public  List<Libro> listarLibrosBD(){
        return  libroRepository.findAll();
     }

     //Autores

    public  List<Autor> listarAutoresBD(){
        return autorRepository.findAll();
        }

    public  List<Autor> listarAutoresVivosPorAnioBD(Integer anioBusqueda){
        return autorRepository.listarAutoresVivos(anioBusqueda);
    }
/*
    public List<Object[]> listarLibroPorAutor(String nombreAutor){
        return libroRepository.librosPorAutor(nombreAutor);
    }


