package com.lab.apis.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Respuesta exitosa de la API")
public record ApiResponse<T>(String mensaje, T datos) {
}
