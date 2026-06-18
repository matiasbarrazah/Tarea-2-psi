# Tarea 2 - Sistema de Biblioteca con TDD

Implementación de un sistema de gestión de biblioteca usando TDD con JUnit 5.

## Requisitos

- Java 11 o superior
- Maven 3.6+

## Cómo ejecutar las pruebas

```bash
mvn test
```

## Estructura del proyecto

```
src/
  main/java/biblioteca/
    Libro.java
    Biblioteca.java
    excepciones/
      LibroNoEncontradoException.java
      LibroNoDisponibleException.java
      LibroYaDisponibleException.java
      LibroDuplicadoException.java
  test/java/biblioteca/
    BibliotecaTest.java
```

## Clases principales

**Libro**: representa un libro con isbn, titulo, autor, año y disponibilidad.

**Biblioteca**: gestiona la colección de libros. Permite registrar, buscar, prestar y devolver libros.

## Excepciones

- `LibroNoEncontradoException`: cuando se busca un libro que no existe
- `LibroNoDisponibleException`: cuando se intenta prestar un libro ya prestado
- `LibroYaDisponibleException`: cuando se intenta devolver un libro que no está prestado
- `LibroDuplicadoException`: cuando se registra un libro con ISBN repetido

## Metodología TDD aplicada

Se siguió el ciclo Red-Green-Refactor:
1. Primero se escribieron las pruebas para cada funcionalidad
2. Se implementó el código mínimo para que pasen
3. Se revisó el código para mejorarlo sin romper los tests

### Ventajas que noté

- Obliga a pensar en los casos borde antes de implementar
- Cuando algo falla, sabes exactamente qué parte rompiste
- Da más confianza al modificar código

### Desventajas que noté

- Al principio es lento porque tienes que escribir los tests antes de entender bien qué vas a implementar
- Cuesta acostumbrarse a escribir el test primero cuando uno quiere ir directo al código
- Algunos tests terminan siendo muy similares entre sí
