package com.lab.apis.dto;

import com.lab.apis.model.ejercicio3y6.EstadoReserva;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

@Schema(description = "Datos necesarios para crear o actualizar una reserva")
public record ReservaRequest(
    @NotBlank @Size(max = 120) @Schema(example = "Ana López") String nombreCliente,
    @NotBlank @Size(max = 20) @Schema(example = "204B") String habitacion,
    @NotNull @Schema(example = "2026-10-15") LocalDate fechaEntrada,
    @NotNull @Schema(example = "2026-10-18") LocalDate fechaSalida,
    @NotNull EstadoReserva estado
) {
}
