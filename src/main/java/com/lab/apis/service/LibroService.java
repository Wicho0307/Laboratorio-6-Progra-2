package com.lab.apis.service;

import com.lab.apis.dto.LibroRequest;
import com.lab.apis.exception.RecursoDuplicadoException;
import com.lab.apis.exception.RecursoNoEncontradoException;
import com.lab.apis.model.Libro;
import com.lab.apis.repository.LibroRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class LibroService {

    private final LibroRepository repository;

    public LibroService(LibroRepository repository) {
        this.repository = repository;
    }

    public Libro registrar(LibroRequest request) {
        validarIsbnDisponible(request.isbn(), null);
        return repository.guardar(desdeRequest(null, request));
    }

    public List<Libro> consultarTodos() {
        return repository.buscarTodos();
    }

    public Libro consultarPorTitulo(String titulo) {
        return repository.buscarPorTitulo(titulo)
            .orElseThrow(() -> new RecursoNoEncontradoException(
                "No existe un libro con el título: " + titulo));
    }

    public Libro actualizar(Long id, LibroRequest request) {
        obtenerPorId(id);
        validarIsbnDisponible(request.isbn(), id);
        return repository.guardar(desdeRequest(id, request));
    }

    public void eliminar(Long id) {
        if (!repository.eliminarPorId(id)) {
            throw new RecursoNoEncontradoException("No existe un libro con id: " + id);
        }
    }

    private Libro obtenerPorId(Long id) {
        return repository.buscarPorId(id)
            .orElseThrow(() -> new RecursoNoEncontradoException("No existe un libro con id: " + id));
    }

    private void validarIsbnDisponible(String isbn, Long idActual) {
        repository.buscarPorIsbn(isbn).filter(libro -> !libro.id().equals(idActual)).ifPresent(libro -> {
            throw new RecursoDuplicadoException("Ya existe un libro con el ISBN: " + isbn);
        });
    }

    private Libro desdeRequest(Long id, LibroRequest request) {
        return new Libro(id, request.titulo().trim(), request.autor().trim(), request.isbn().trim(),
            request.anioPublicacion(), request.estado());
    }
}
