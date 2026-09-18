package com.lab.apis.model.ejercicio3y6;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Estado actual de la reserva")
public enum EstadoReserva {
    PENDIENTE,
    CONFIRMADA,
    CANCELADA,
    FINALIZADA
}
