package com.lab.apis.repository;

import com.lab.apis.model.ejercicio1y4.Libro;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Repository;

@Repository
public class LibroRepository {

    private final List<Libro> libros = new ArrayList<>();
    private final AtomicLong secuencia = new AtomicLong();

    public synchronized Libro guardar(Libro libro) {
        Long id = libro.id() == null ? secuencia.incrementAndGet() : libro.id();
        Libro guardado = new Libro(id, libro.titulo(), libro.autor(), libro.isbn(),
            libro.anioPublicacion(), libro.estado());
        libros.removeIf(actual -> actual.id().equals(id));
        libros.add(guardado);
        return guardado;
    }

    public synchronized List<Libro> buscarTodos() {
        return List.copyOf(libros);
    }

    public synchronized Optional<Libro> buscarPorId(Long id) {
        return libros.stream().filter(libro -> libro.id().equals(id)).findFirst();
    }

    public synchronized Optional<Libro> buscarPorTitulo(String titulo) {
        return libros.stream().filter(libro -> libro.titulo().equalsIgnoreCase(titulo)).findFirst();
    }

    public synchronized Optional<Libro> buscarPorIsbn(String isbn) {
        return libros.stream().filter(libro -> libro.isbn().equalsIgnoreCase(isbn)).findFirst();
    }

    public synchronized boolean eliminarPorId(Long id) {
        return libros.removeIf(libro -> libro.id().equals(id));
    }
}
