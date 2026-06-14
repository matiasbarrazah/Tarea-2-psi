package biblioteca;

import java.util.ArrayList;
import java.util.List;
import biblioteca.excepciones.LibroDuplicadoException;
import biblioteca.excepciones.LibroNoDisponibleException;
import biblioteca.excepciones.LibroNoEncontradoException;
import biblioteca.excepciones.LibroYaDisponibleException;

public class Biblioteca {

    private ArrayList<Libro> libros;

    public Biblioteca() {
        libros = new ArrayList<>();
    }

    public void registrarLibro(Libro libro) throws LibroDuplicadoException {
        if (libro.getIsbn() == null || libro.getIsbn().equals("")) {
            throw new IllegalArgumentException("El ISBN no puede ser vacio");
        }

        for (int i = 0; i < libros.size(); i++) {
            if (libros.get(i).getIsbn().equals(libro.getIsbn())) {
                throw new LibroDuplicadoException("Ya existe un libro con ese ISBN");
            }
        }

        libros.add(libro);
    }

    public Libro buscarPorIsbn(String isbn) throws LibroNoEncontradoException {
        Libro encontrado = null;
        for (int i = 0; i < libros.size(); i++) {
            if (libros.get(i).getIsbn().equals(isbn)) {
                encontrado = libros.get(i);
            }
        }
        if (encontrado == null) {
            throw new LibroNoEncontradoException("No existe libro con ISBN: " + isbn);
        }
        return encontrado;
    }

    public List<Libro> buscarPorTitulo(String titulo) {
        List<Libro> resultado = new ArrayList<>();
        for (int i = 0; i < libros.size(); i++) {
            String tituloLibro = libros.get(i).getTitulo().toLowerCase();
            String tituloBusqueda = titulo.toLowerCase();
            if (tituloLibro.contains(tituloBusqueda)) {
                resultado.add(libros.get(i));
            }
        }
        return resultado;
    }

    public List<Libro> listarDisponibles() {
        List<Libro> disponibles = new ArrayList<>();
        for (int i = 0; i < libros.size(); i++) {
            if (libros.get(i).isDisponible() == true) {
                disponibles.add(libros.get(i));
            }
        }
        return disponibles;
    }

    public void prestarLibro(String isbn) throws LibroNoEncontradoException, LibroNoDisponibleException {
        Libro libro = null;
        for (int i = 0; i < libros.size(); i++) {
            if (libros.get(i).getIsbn().equals(isbn)) {
                libro = libros.get(i);
            }
        }

        if (libro == null) {
            throw new LibroNoEncontradoException("No existe libro con ISBN: " + isbn);
        }

        if (libro.isDisponible() == false) {
            throw new LibroNoDisponibleException("El libro ya esta prestado");
        }

        libro.setDisponible(false);
    }

    public void devolverLibro(String isbn) throws LibroNoEncontradoException, LibroYaDisponibleException {
        Libro libro = null;
        for (int i = 0; i < libros.size(); i++) {
            if (libros.get(i).getIsbn().equals(isbn)) {
                libro = libros.get(i);
            }
        }

        if (libro == null) {
            throw new LibroNoEncontradoException("No existe libro con ISBN: " + isbn);
        }

        if (libro.isDisponible() == true) {
            throw new LibroYaDisponibleException("El libro ya esta disponible");
        }

        libro.setDisponible(true);
    }

    public int getTotalLibros() {
        return libros.size();
    }
}
