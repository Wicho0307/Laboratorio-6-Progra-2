package com.lab.apis.repository;

import com.lab.apis.model.ejercicio2y5.Curso;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Repository;

@Repository
public class CursoRepository {

    private final List<Curso> cursos = new ArrayList<>();
    private final AtomicLong secuencia = new AtomicLong();

    public synchronized Curso guardar(Curso curso) {
        Long id = curso.id() == null ? secuencia.incrementAndGet() : curso.id();
        Curso guardado = new Curso(id, curso.nombre(), curso.codigo(), curso.creditos(), curso.estado());
        cursos.removeIf(actual -> actual.id().equals(id));
        cursos.add(guardado);
        return guardado;
    }

    public synchronized List<Curso> buscarTodos() {
        return List.copyOf(cursos);
    }

    public synchronized Optional<Curso> buscarPorId(Long id) {
        return cursos.stream().filter(curso -> curso.id().equals(id)).findFirst();
    }

    public synchronized Optional<Curso> buscarPorCodigo(String codigo) {
        return cursos.stream().filter(curso -> curso.codigo().equalsIgnoreCase(codigo)).findFirst();
    }

    public synchronized boolean eliminarPorId(Long id) {
        return cursos.removeIf(curso -> curso.id().equals(id));
    }
}
