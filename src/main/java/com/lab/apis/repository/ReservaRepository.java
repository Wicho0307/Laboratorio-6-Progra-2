package com.lab.apis.repository;

import com.lab.apis.model.ejercicio3y6.Reserva;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Repository;

@Repository
public class ReservaRepository {

    private final List<Reserva> reservas = new ArrayList<>();
    private final AtomicLong secuencia = new AtomicLong();

    public synchronized Reserva guardar(Reserva reserva) {
        Long id = reserva.id() == null ? secuencia.incrementAndGet() : reserva.id();
        Reserva guardada = new Reserva(id, reserva.nombreCliente(), reserva.habitacion(),
            reserva.fechaEntrada(), reserva.fechaSalida(), reserva.estado());
        reservas.removeIf(actual -> actual.id().equals(id));
        reservas.add(guardada);
        return guardada;
    }

    public synchronized List<Reserva> buscarTodos() {
        return List.copyOf(reservas);
    }

    public synchronized Optional<Reserva> buscarPorId(Long id) {
        return reservas.stream().filter(reserva -> reserva.id().equals(id)).findFirst();
    }
}
