package com.example.demo.Principal;

import com.example.demo.model.Autor;
import com.example.demo.model.DatosAutor;
import com.example.demo.model.DatosLibro;
import com.example.demo.model.DatosResultados;
import com.example.demo.model.Libro;
import com.example.demo.repository.AutorRepository;
import com.example.demo.repository.LibroRepository;
import com.example.demo.service.ConsumoAPI;
import com.example.demo.service.ConvierteDatos;

import java.util.Scanner;

public class Principal {

    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConvierteDatos conversor = new ConvierteDatos();
    private final String URL_BASE = "https://gutendex.com/books/?search=";

    private AutorRepository autorRepositorio;
    private LibroRepository libroRepositorio;

    public Principal(AutorRepository autorRepositorio, LibroRepository libroRepositorio) {
        this.autorRepositorio = autorRepositorio;
        this.libroRepositorio = libroRepositorio;
    }

    public void muestraElMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    ---------
                    Elija la opción a través de su número:
                    1- buscar libro por título
                    2- listar libros registrados
                    3- listar autores registrados
                    4- listar autores vivos en un determinado año
                    5- listar libros por idioma
                    0 - salir
                    ---------
                    """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    buscarLibroWeb();
                    break;
                case 2:
                    listarLibrosRegistrados();
                    break;
                case 3:
                    listarAutoresRegistrados();
                    break;
                case 4:
                    listarAutoresVivos();
                    break;
                case 5:
                    listarLibrosPorIdioma();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }
    }

    private void buscarLibroWeb() {
        System.out.println("Escribe el nombre del libro que deseas buscar:");
        var tituloLibro = teclado.nextLine();

        var json = consumoAPI.obtenerDatos(URL_BASE + tituloLibro.replace(" ", "%20"));
        DatosResultados datosBusqueda = conversor.obtenerDatos(json, DatosResultados.class);

        if (datosBusqueda.resultados().isEmpty()) {
            System.out.println("Libro no encontrado en la API.");
            return;
        }

        DatosLibro datosLibro = datosBusqueda.resultados().get(0);
        DatosAutor datosAutor = datosLibro.autores().get(0);

        Autor autor = new Autor(datosAutor);
        autorRepositorio.save(autor);

        Libro libro = new Libro(datosLibro, autor);
        libroRepositorio.save(libro);

        System.out.println("¡Libro guardado exitosamente en tu base de datos!");
        System.out.println("Título: " + libro.getTitulo() + " | Autor: " + autor.getNombre());
    }

    private void listarLibrosRegistrados() {
        var libros = libroRepositorio.findAll();
        System.out.println("\n--- LIBROS REGISTRADOS ---");
        libros.forEach(l -> System.out.println("Título: " + l.getTitulo() + " | Idioma: " + l.getIdioma()));
    }

    private void listarAutoresRegistrados() {
        var autores = autorRepositorio.findAll();
        System.out.println("\n--- AUTORES REGISTRADOS ---");
        autores.forEach(a -> System.out.println("Autor: " + a.getNombre()));
    }

    private void listarAutoresVivos() {
        System.out.println("Ingresa el año que deseas consultar:");
        var anio = teclado.nextInt();
        teclado.nextLine();
        var autores = autorRepositorio.autoresVivosEnAnio(anio);
        if (autores.isEmpty()) {
            System.out.println("No hay autores vivos registrados en ese año.");
        } else {
            System.out.println("\n--- AUTORES VIVOS EN " + anio + " ---");
            autores.forEach(a -> System.out.println("Autor: " + a.getNombre()));
        }
    }

    private void listarLibrosPorIdioma() {
        System.out.println("Ingrese el idioma (ejemplo: es, en, fr, pt):");
        var idioma = teclado.nextLine();
        var libros = libroRepositorio.findByIdioma(idioma);
        if (libros.isEmpty()) {
            System.out.println("No hay libros registrados en ese idioma.");
        } else {
            System.out.println("\n--- LIBROS EN EL IDIOMA '" + idioma + "' ---");
            libros.forEach(l -> System.out.println("Título: " + l.getTitulo() + " | Idioma: " + l.getIdioma()));
        }
    }
}