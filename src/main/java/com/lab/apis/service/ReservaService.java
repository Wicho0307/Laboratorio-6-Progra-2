package com.lab.apis.service;

import com.lab.apis.dto.ReservaRequest;
import com.lab.apis.exception.RecursoNoEncontradoException;
import com.lab.apis.exception.ReglaNegocioException;
import com.lab.apis.model.ejercicio3y6.EstadoReserva;
import com.lab.apis.model.ejercicio3y6.Reserva;
import com.lab.apis.repository.ReservaRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ReservaService {

    private final ReservaRepository repository;

    public ReservaService(ReservaRepository repository) {
        this.repository = repository;
    }

    public Reserva crear(ReservaRequest request) {
        validarFechas(request);
        return repository.guardar(desdeRequest(null, request));
    }

    public List<Reserva> consultarTodas() {
        return repository.buscarTodos();
    }

    public Reserva consultarPorId(Long id) {
        return repository.buscarPorId(id)
            .orElseThrow(() -> new RecursoNoEncontradoException("No existe una reserva con id: " + id));
    }

    public Reserva actualizar(Long id, ReservaRequest request) {
        consultarPorId(id);
        validarFechas(request);
        return repository.guardar(desdeRequest(id, request));
    }

    public Reserva cancelar(Long id) {
        Reserva actual = consultarPorId(id);
        if (actual.estado() == EstadoReserva.FINALIZADA) {
            throw new ReglaNegocioException("Una reserva finalizada no puede cancelarse");
        }
        if (actual.estado() == EstadoReserva.CANCELADA) {
            return actual;
        }
        return repository.guardar(new Reserva(actual.id(), actual.nombreCliente(), actual.habitacion(),
            actual.fechaEntrada(), actual.fechaSalida(), EstadoReserva.CANCELADA));
    }

    private void validarFechas(ReservaRequest request) {
        if (!request.fechaSalida().isAfter(request.fechaEntrada())) {
            throw new ReglaNegocioException("La fecha de salida debe ser posterior a la fecha de entrada");
        }
    }

    private Reserva desdeRequest(Long id, ReservaRequest request) {
        return new Reserva(id, request.nombreCliente().trim(), request.habitacion().trim(),
            request.fechaEntrada(), request.fechaSalida(), request.estado());
    }
}
