package biblioteca.excepciones;

public class LibroYaDisponibleException extends Exception {

    public LibroYaDisponibleException(String mensaje) {
        super(mensaje);
    }
}
