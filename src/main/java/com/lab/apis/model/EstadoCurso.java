package com.lab.apis.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Estado actual del curso")
public enum EstadoCurso {
    ACTIVO,
    INACTIVO
}
