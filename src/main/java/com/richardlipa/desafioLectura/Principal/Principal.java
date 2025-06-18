
package com.richardlipa.desafioLectura.Principal;

import com.richardlipa.desafioLectura.DTO.AutorDTO;
import com.richardlipa.desafioLectura.DTO.DatosAutorDTO;
import com.richardlipa.desafioLectura.DTO.DatosDTO;
import com.richardlipa.desafioLectura.DTO.LibroDTO;
import com.richardlipa.desafioLectura.Service.ConsumoAPI;
import com.richardlipa.desafioLectura.Service.ConvierteDatos;
import com.richardlipa.desafioLectura.Service.LibroService;
import com.richardlipa.desafioLectura.model.Autor;
import com.richardlipa.desafioLectura.model.Lenguaje;
import com.richardlipa.desafioLectura.model.Libro;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;


@Component // <--- ¡Añade esta anotación!
public class Principal {

    private final Scanner teclado = new Scanner(System.in);
    private final ConsumoAPI consumoApi = new ConsumoAPI();
    private final ConvierteDatos convertir = new ConvierteDatos();
    private final String BASE_URL = "https://gutendex.com/books/?";
    private final List<DatosDTO> datosResultados = new ArrayList<>();
    private final List<LibroDTO> datosLibros = new ArrayList<>();
    List<Libro> libros;
    Optional<Libro> libroBuscado;
    private final LibroService libroService;//llamamos al servicio para guardar los libros

    public Principal(LibroService libroService) {
        this.libroService = libroService;
    }

    public void mostrarMenu() throws UnsupportedEncodingException {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                     1 - Buscar Libro por Titulo\s
                     2 - Listar Libros registrados en la BD \s
                     3 - Listar Autores registrados en la BD\s
                     4 - Listar Autores vivos en el mismo anio o intervalo de tiempo en API GutenDex\s
                     5 - Listar Autores vivos en el mismo anio en BD \s
                     6 - Listar libros registrados en la BD  por idioma  \s
                     7 - Listar libros por nombre e Autor  \s
                    
                     0 - Salir
                    \s""";
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();
            //

            switch (opcion) {
                case 1:
                    buscarLibroPorTitulo();
                    break;
                case 2:
                    listarLibrosReGegistrados();
                    break;
                case 3:
                    liostarAutoresRegistrados();
                    break;
                case 4:
                    listarAutoresVivosEnAnio();
                    break;
                case 5:
                    autoresVivosPorAnio();
                    break;
                case 6:
                    listarLibroPorIdioma();
                    break;
                case 7:
                    listarLibroPorNombreAutor();
                    break;

                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }
    }


    @Transactional
    public void buscarLibroPorTitulo() throws UnsupportedEncodingException {
        System.out.println("Escribe el título del libro que deseas buscar:");
        String nombreLibro = teclado.nextLine();
        String encodedString = URLEncoder.encode(nombreLibro, StandardCharsets.UTF_8);
        var json = consumoApi.obtenerDatos(BASE_URL + "search=" + encodedString);

        DatosDTO datos = convertir.obtenerDatos(json, DatosDTO.class);


        if (datos.libros() != null && !datos.libros().isEmpty()) {
            LibroDTO libroDTO = datos.libros().get(0);
            System.out.println("libro dto " + libroDTO);
            Libro libroParaGuardar = Libro.fromDTO(datos.libros().get(0));

            Libro libroSave = libroService.guardarLibroDesdeDTO(libroDTO);
            if (libroSave != null) {

                System.out.println("----------Libro-----------\n");
                System.out.println("Titulo: " + libroSave.getTitulo());

            // --- ¡CORRECCIÓN AQUÍ! ---
            // Recopila los nombres de los autores en una sola cadena, separados por ", "
            String nombresAutores = libroSave.getAutores().stream()
                    .map(a -> a.getNombre())
                    .collect(Collectors.joining(" y "));
            System.out.println("Autor/Autores: " + nombresAutores);
            // --- FIN DE LA CORRECCIÓN ---

            // También corrige Idioma si Lenguajes es una lista de enums y quieres los nombres legibles
            String nombresIdiomas = libroSave.getLenguajes().stream()
                    .map(lenguaje -> lenguaje.toString()) // Asumiendo que Lenguaje es un enum
                    .collect(Collectors.joining(", "));
            System.out.println("Idioma: " + nombresIdiomas);


                System.out.println("Total Descargas: " + libroSave.getTotalDescargas());
                System.out.println("----------Libro-----------");
            } else {
                System.out.println("Libro ya está registrado en la BD. Intenta guardar otro libro.");
            }
        }
    }


@Transactional // Asegúrate de que tu método principal esté @Transactional
private void listarLibrosReGegistrados() {
    List<Libro> listaDeLibrosEnBD = libroService.listarLibrosBD();
   System.out.println("--- Detalles Completos de Libros, Autores e Idiomas ---");
    listaDeLibrosEnBD.stream()
            .forEach(l -> { // Cambiado a forEach ya que map + collect toList no es necesario para efectos secundarios (imprimir)
                // Obtiene los nombres de los autores y los une en una sola cadena separados por ", "
                String autoresNombres = l.getAutores().stream()
                        .map(autor -> autor.getNombre())
                        .collect(Collectors.joining(" y "));

                // Obtiene los nombres de los lenguajes (idiomas) y los une en una sola cadena
                // Asumiendo que 'Lenguaje' es un enum, toString() te dará su nombre
                String lenguajesNombres = l.getLenguajes().stream()
                        .map(lenguaje -> lenguaje.toString())
                        .collect(Collectors.joining(", "));

                // Imprime la información formateada del libro
                // %n asegura un salto de línea compatible con diferentes sistemas operativos
                String linea = "\n------------ Libro ----------";
                System.out.printf(" %s \nId: %s  Titulo: %s  \nAutor: %s \nIdioma: %s \nTotalDescargas %s %s%n",
                        linea,
                        l.getId(),
                        l.getTitulo(),
                        autoresNombres,
                        lenguajesNombres,
                        l.getTotalDescargas(),
                        linea
                );
            });

    //  List<Object[]> resultados = libroService.listarLibrosBD();

   /* System.out.println("--- Detalles Completos de Libros, Autores e Idiomas ---");
    for (Object[] fila : resultados) {
        Long idLibro = (Long) fila[0];
        String titulo = (String) fila[1];
        String nombreAutor = (String) fila[2];
        String idioma = (String) fila[3]; // O Lenguaje, dependiendo de cómo lo devuelva la BD

        System.out.printf("Libro ID: %d, Titulo: %s, Autor: %s, Idioma: %s%n",
                idLibro, titulo, nombreAutor, idioma);
    }
    System.out.println("-------------------------------------------------------");*/
}


    @Transactional // Asegúrate de que tu método principal esté @Transactional
        private void liostarAutoresRegistrados() {
           List<Autor> listaDeAutores=libroService.listarAutoresBD();
            System.out.println("--- Autores Registrados en la BD  ---");
            listaDeAutores.stream()
                    .forEach(a -> {
                        String linea = "\n------------ Autor ----------";
                        System.out.printf(" %s \nId: %s  Nombre: %s  \nNacimiento: %s \nFallecimiento: %s  %s%n",
                                linea,
                                a.getId(),
                                a.getNombre(),
                                a.getFechaNacimiento(),
                                a.getAnioFallecimiento(),
                                linea
                        );
                    });
        }

        private void listarAutoresVivosEnAnio() {
            //System.out.println("******* Buscando Autores vivos por intervalo de tiempo,  Ejemplo: 1850 - 1900");
            System.out.println("******* Buscando Autores vivos en un anio determinado en la Api Gutendex,  Ejemplo: 1851");
            System.out.println("Ingresa el primer anio ...solo se aceptan numeros");
            int intervalo1 = teclado.nextInt();
          /*  System.out.println("Ingresa el segundo  anio ...solo se aceptan numeros");
            int intervalo2 = teclado.nextInt();*/
            var json = consumoApi.obtenerDatos(BASE_URL + "author_year_start" + intervalo1 + "&author_year_end" + intervalo1);
            System.out.println("Json Autores " + json);

            DatosDTO datos = convertir.obtenerDatos(json, DatosDTO.class);
            System.out.println("Datos DTO obtenidos: " + datos.libros());

            System.out.println("--- Detalles Completos de Libros, Autores e Idiomas ---");

            /// formato tabla
            System.out.println("---------------------------------------------------------------------------------------------------");
            System.out.printf("%-20s %-20s %-10s%n", "Autor", "Fecha nacimiento", "Fecha de Muerte");
            System.out.println("---------------------------------------------------------------------------------------------------");
            datos.libros().forEach(l -> { // Cambiado a forEach ya que map + collect toList no es necesario para efectos secundarios (imprimir)
                // Obtiene los nombres de los autores y los une en una sola cadena separados por ", "
                String autoresNombres = l.autores().stream()
                        .map(autor -> autor.nombre())
                        .collect(Collectors.joining(" , "));
                String autoresNacimiento = l.autores().stream()
                        .map(autor -> autor.fechaNacimiento())
                        .collect(Collectors.joining(" , "));
                String autoresMuerte = l.autores().stream()
                        .map(autor -> autor.anioFallecimiento())
                        .collect(Collectors.joining(" , "));
                // Definir un ancho fijo para las columnas


                System.out.printf("%-60s %-40s %-10s%n",
                        autoresNombres, autoresNacimiento, autoresMuerte);

        });

    }

    private void autoresVivosPorAnio() {
        System.out.println("******* Buscando Autores vivos en un anio determinado en la Base de Datos,  Ejemplo: 1851");
        System.out.println("Ingresa el primer anio ...solo se aceptan numeros");
        Integer anioBusqueda = teclado.nextInt();

        List<Autor> autoresVivos = libroService.listarAutoresVivosPorAnioBD( anioBusqueda);
        autoresVivos.stream()
                .forEach(a -> {
                    String linea = "\n------------ Autor ----------";
                    System.out.printf(" %s \nId: %s  Nombre: %s  \nNacimiento: %s \nFallecimiento: %s  %s%n",
                            linea,
                            a.getId(),
                            a.getNombreAutor(),
                            a.getFechaNacimiento(),
                            a.getAnioFallecimiento()

                    );
                });
        System.out.println("\n---------------------------------------------------------------------------------------------------");
    }

    private void listarLibroPorIdioma() {
        System.out.println("******* Buscando Libros por idioma *******");
        System.out.println("Por favor, selecciona un idioma de la siguiente lista:");

        int i = 1;
        for (Lenguaje lenguaje : Lenguaje.values()) {
            System.out.println(i + ". " + lenguaje.toString() + " (" + lenguaje.getCodigo() + ")");
            i++;
        }
        System.out.println("\n------------------------------------------");
        System.out.print("Ingresa el número de tu opción: ");

        try {
            int opcionIdioma = teclado.nextInt();
            teclado.nextLine();

            if (opcionIdioma < 1 || opcionIdioma > Lenguaje.values().length) {
                System.out.println("Opción no válida. Por favor, ingresa un número entre 1 y " + Lenguaje.values().length);
                return;
            }

            Lenguaje lenguajeSeleccionado = Lenguaje.values()[opcionIdioma - 1];
            String codigoIdioma = lenguajeSeleccionado.getCodigo();

            System.out.println("Buscando libros en " + lenguajeSeleccionado.toString() + "...");


            var json = consumoApi.obtenerDatos(BASE_URL + "language=" + codigoIdioma);
            System.out.println("JSON de libros en " + lenguajeSeleccionado.toString() + ": " + json);

            DatosDTO datosLibrosPorIdioma = convertir.obtenerDatos(json, DatosDTO.class);

                if (datosLibrosPorIdioma != null && datosLibrosPorIdioma.libros() != null && !datosLibrosPorIdioma.libros().isEmpty()) {
                    System.out.println("\n--- Libros encontrados en " + lenguajeSeleccionado.toString() + " ---");
                 //   String linea = "\n************ Libro ***********\n";
                   /* datosLibrosPorIdioma.libros().forEach(libro -> {
                        String autores = libro.autores().stream()
                                .map(a -> a.nombre())
                                .collect(Collectors.joining(", "));
                        System.out.printf("%s Titulo: %s | Autor(es): %s | Descargas: %s  %s%n",
                               linea, libro.titulo(), autores, libro.totalDescargas(), linea);

                    });*/

                /// formato tabla
                System.out.println("---------------------------------------------------------------------------------------------------");
                System.out.printf("%-60s %-40s %-10s%n", "Titulo", "Autor(es)", "Descargas");
                System.out.println("---------------------------------------------------------------------------------------------------");
                datosLibrosPorIdioma.libros().forEach(libro -> {
                    String autores = libro.autores().stream()
                            .map(a -> a.nombre())
                            .collect(Collectors.joining(", "));


                    System.out.printf("%-60s %-40s %-10s%n",
                            libro.titulo(), autores, libro.totalDescargas());
                });

                System.out.println("\n--- Libros encontrados en " + lenguajeSeleccionado.toString() + " ---");


            } else {
                System.out.println("No se encontraron libros en " + lenguajeSeleccionado.toString() + ".");
            }

        } catch (InputMismatchException e) {
            System.out.println("Entrada no válida. Por favor, ingresa un número.");
            teclado.nextLine();
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Ocurrió un error al buscar los libros: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Transactional
    private void listarLibroPorNombreAutor() {
        System.out.println("Ingrese el nombre del autor para buscar sus libros ");
        String nombreAutor = teclado.nextLine();
        List<Object[]> libros = libroService.listarLibroPorAutor(nombreAutor);
        System.out.println("--- Detalles Completos de Libros, Autores e Idiomas ---");
        System.out.println("libros bd Autor "+libros);


    System.out.println("--- Detalles Completos de Libros, Autores e Idiomas ---");
    for (Object[] fila : libros) {
        Long idLibro = (Long) fila[0];
        String titulo = (String) fila[1];
        String autor = (String) fila[2];
        String idioma = (String) fila[3]; // O Lenguaje, dependiendo de cómo lo devuelva la BD

        System.out.printf("Libro ID: %d, Titulo: %s, Autor: %s, Idioma: %s%n",
                idLibro, titulo, autor, idioma);
    }
    System.out.println("-------------------------------------------------------");


    }
}



