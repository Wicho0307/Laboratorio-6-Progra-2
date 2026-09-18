package com.lab.apis.model.ejercicio3y6;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;

@Schema(description = "Reserva de habitación de hotel")
public record Reserva(
    @Schema(example = "1") Long id,
    @Schema(example = "Ana López") String nombreCliente,
    @Schema(example = "204B") String habitacion,
    @Schema(example = "2026-10-15") LocalDate fechaEntrada,
    @Schema(example = "2026-10-18") LocalDate fechaSalida,
    EstadoReserva estado
) {
}
