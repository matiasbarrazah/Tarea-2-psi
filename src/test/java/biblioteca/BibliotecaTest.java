package biblioteca;

import biblioteca.excepciones.LibroDuplicadoException;
import biblioteca.excepciones.LibroNoDisponibleException;
import biblioteca.excepciones.LibroNoEncontradoException;
import biblioteca.excepciones.LibroYaDisponibleException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BibliotecaTest {

    private Biblioteca biblioteca;

    @BeforeEach
    void setUp() {
        biblioteca = new Biblioteca();
    }

    @Test
    void testRegistrarLibroOk() throws LibroDuplicadoException {
        Libro libro = new Libro("123", "El Quijote", "Cervantes", 1605);
        biblioteca.registrarLibro(libro);
        assertEquals(1, biblioteca.getTotalLibros());
    }

    @Test
    void testRegistrarLibroIsbnVacio() {
        Libro libro = new Libro("", "Sin ISBN", "Autor", 2020);
        assertThrows(IllegalArgumentException.class, () -> {
            biblioteca.registrarLibro(libro);
        });
    }

    @Test
    void testRegistrarLibroIsbnNulo() {
        Libro libro = new Libro(null, "Sin ISBN", "Autor", 2020);
        assertThrows(IllegalArgumentException.class, () -> {
            biblioteca.registrarLibro(libro);
        });
    }

    @Test
    void testRegistrarLibroDuplicado() throws LibroDuplicadoException {
        Libro libro1 = new Libro("123", "Libro A", "Autor A", 2000);
        Libro libro2 = new Libro("123", "Libro B", "Autor B", 2001);
        biblioteca.registrarLibro(libro1);
        assertThrows(LibroDuplicadoException.class, () -> {
            biblioteca.registrarLibro(libro2);
        });
    }

    @Test
    void testBuscarPorIsbnExistente() throws LibroDuplicadoException, LibroNoEncontradoException {
        Libro libro = new Libro("456", "Cien Anos", "Garcia Marquez", 1967);
        biblioteca.registrarLibro(libro);
        Libro encontrado = biblioteca.buscarPorIsbn("456");
        assertEquals("456", encontrado.getIsbn());
        assertEquals("Cien Anos", encontrado.getTitulo());
    }

    @Test
    void testBuscarPorIsbnNoExistente() {
        assertThrows(LibroNoEncontradoException.class, () -> {
            biblioteca.buscarPorIsbn("999");
        });
    }

    @Test
    void testBuscarPorTituloParcia() throws LibroDuplicadoException {
        Libro libro1 = new Libro("1", "El Principito", "Saint-Exupery", 1943);
        Libro libro2 = new Libro("2", "El Alquimista", "Coelho", 1988);
        Libro libro3 = new Libro("3", "Cien Anos de Soledad", "Garcia Marquez", 1967);
        biblioteca.registrarLibro(libro1);
        biblioteca.registrarLibro(libro2);
        biblioteca.registrarLibro(libro3);

        List<Libro> resultado = biblioteca.buscarPorTitulo("el");
        assertEquals(2, resultado.size());
    }

    @Test
    void testBuscarPorTituloMayusculas() throws LibroDuplicadoException {
        Libro libro = new Libro("1", "El Principito", "Saint-Exupery", 1943);
        biblioteca.registrarLibro(libro);

        List<Libro> resultado = biblioteca.buscarPorTitulo("PRINCIPITO");
        assertEquals(1, resultado.size());
    }

    @Test
    void testBuscarPorTituloSinResultados() throws LibroDuplicadoException {
        Libro libro = new Libro("1", "El Principito", "Saint-Exupery", 1943);
        biblioteca.registrarLibro(libro);

        List<Libro> resultado = biblioteca.buscarPorTitulo("xyz");
        assertEquals(0, resultado.size());
    }

    @Test
    void testListarDisponibles() throws LibroDuplicadoException, LibroNoEncontradoException, LibroNoDisponibleException {
        Libro libro1 = new Libro("1", "Libro A", "Autor", 2000);
        Libro libro2 = new Libro("2", "Libro B", "Autor", 2001);
        biblioteca.registrarLibro(libro1);
        biblioteca.registrarLibro(libro2);
        biblioteca.prestarLibro("1");

        List<Libro> disponibles = biblioteca.listarDisponibles();
        assertEquals(1, disponibles.size());
        assertEquals("2", disponibles.get(0).getIsbn());
    }

    @Test
    void testPrestarLibroOk() throws LibroDuplicadoException, LibroNoEncontradoException, LibroNoDisponibleException {
        Libro libro = new Libro("1", "Libro A", "Autor", 2000);
        biblioteca.registrarLibro(libro);
        biblioteca.prestarLibro("1");
        assertFalse(libro.isDisponible());
    }

    @Test
    void testPrestarLibroNoExiste() {
        assertThrows(LibroNoEncontradoException.class, () -> {
            biblioteca.prestarLibro("999");
        });
    }

    @Test
    void testPrestarLibroYaPrestado() throws LibroDuplicadoException, LibroNoEncontradoException, LibroNoDisponibleException {
        Libro libro = new Libro("1", "Libro A", "Autor", 2000);
        biblioteca.registrarLibro(libro);
        biblioteca.prestarLibro("1");
        assertThrows(LibroNoDisponibleException.class, () -> {
            biblioteca.prestarLibro("1");
        });
    }

    @Test
    void testDevolverLibroOk() throws LibroDuplicadoException, LibroNoEncontradoException, LibroNoDisponibleException, LibroYaDisponibleException {
        Libro libro = new Libro("1", "Libro A", "Autor", 2000);
        biblioteca.registrarLibro(libro);
        biblioteca.prestarLibro("1");
        biblioteca.devolverLibro("1");
        assertTrue(libro.isDisponible());
    }

    @Test
    void testDevolverLibroNoExiste() {
        assertThrows(LibroNoEncontradoException.class, () -> {
            biblioteca.devolverLibro("999");
        });
    }

    @Test
    void testDevolverLibroYaDisponible() throws LibroDuplicadoException {
        Libro libro = new Libro("1", "Libro A", "Autor", 2000);
        biblioteca.registrarLibro(libro);
        assertThrows(LibroYaDisponibleException.class, () -> {
            biblioteca.devolverLibro("1");
        });
    }

    @Test
    void testLibroDisponibleAlCrear() throws LibroDuplicadoException {
        Libro libro = new Libro("1", "Libro A", "Autor", 2000);
        biblioteca.registrarLibro(libro);
        assertTrue(libro.isDisponible());
    }

    @Test
    void testListarDisponiblesTodosDisponibles() throws LibroDuplicadoException {
        Libro libro1 = new Libro("10", "Libro X", "Autor", 2000);
        Libro libro2 = new Libro("11", "Libro Y", "Autor", 2001);
        biblioteca.registrarLibro(libro1);
        biblioteca.registrarLibro(libro2);
        assertEquals(2, biblioteca.listarDisponibles().size());
    }

    @Test
    void testBibliotecaVaciaAlInicio() {
        assertEquals(0, biblioteca.getTotalLibros());
    }
}
