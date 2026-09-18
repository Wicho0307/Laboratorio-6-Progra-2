package com.lab.apis.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.Map;

@Schema(description = "Respuesta de error de la API")
public record ApiError(
    LocalDateTime fecha,
    int codigo,
    String error,
    String mensaje,
    String ruta,
    Map<String, String> validaciones
) {
}
