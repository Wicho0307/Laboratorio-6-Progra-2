package com.lab.apis.model.ejercicio1y4;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Estado actual del libro")
public enum EstadoLibro {
    DISPONIBLE,
    PRESTADO,
    INACTIVO
}
