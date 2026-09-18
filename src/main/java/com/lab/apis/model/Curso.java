package com.lab.apis.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Curso universitario")
public record Curso(
    @Schema(example = "1") Long id,
    @Schema(example = "Programación II") String nombre,
    @Schema(example = "CC-202") String codigo,
    @Schema(example = "4") Integer creditos,
    EstadoCurso estado
) {
}
