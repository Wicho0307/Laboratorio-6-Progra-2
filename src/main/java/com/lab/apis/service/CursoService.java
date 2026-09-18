package com.lab.apis.service;

import com.lab.apis.dto.CursoRequest;
import com.lab.apis.exception.RecursoDuplicadoException;
import com.lab.apis.exception.RecursoNoEncontradoException;
import com.lab.apis.model.Curso;
import com.lab.apis.repository.CursoRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class CursoService {

    private final CursoRepository repository;

    public CursoService(CursoRepository repository) {
        this.repository = repository;
    }

    public Curso crear(CursoRequest request) {
        validarCodigoDisponible(request.codigo(), null);
        return repository.guardar(desdeRequest(null, request));
    }

    public List<Curso> consultarTodos() {
        return repository.buscarTodos();
    }

    public Curso consultarPorCodigo(String codigo) {
        return repository.buscarPorCodigo(codigo)
            .orElseThrow(() -> new RecursoNoEncontradoException(
                "No existe un curso con el código: " + codigo));
    }

    public Curso actualizar(Long id, CursoRequest request) {
        obtenerPorId(id);
        validarCodigoDisponible(request.codigo(), id);
        return repository.guardar(desdeRequest(id, request));
    }

    public void eliminar(Long id) {
        if (!repository.eliminarPorId(id)) {
            throw new RecursoNoEncontradoException("No existe un curso con id: " + id);
        }
    }

    private Curso obtenerPorId(Long id) {
        return repository.buscarPorId(id)
            .orElseThrow(() -> new RecursoNoEncontradoException("No existe un curso con id: " + id));
    }

    private void validarCodigoDisponible(String codigo, Long idActual) {
        repository.buscarPorCodigo(codigo).filter(curso -> !curso.id().equals(idActual)).ifPresent(curso -> {
            throw new RecursoDuplicadoException("Ya existe un curso con el código: " + codigo);
        });
    }

    private Curso desdeRequest(Long id, CursoRequest request) {
        return new Curso(id, request.nombre().trim(), request.codigo().trim().toUpperCase(),
            request.creditos(), request.estado());
    }
}
