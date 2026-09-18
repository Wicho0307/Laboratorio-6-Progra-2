package com.lab.apis.dto;

import com.lab.apis.model.EstadoCurso;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(description = "Datos necesarios para crear o actualizar un curso")
public record CursoRequest(
    @NotBlank @Size(max = 120) @Schema(example = "Programación II") String nombre,
    @NotBlank @Size(max = 20) @Schema(example = "CC-202") String codigo,
    @NotNull @Min(1) @Max(30) @Schema(example = "4") Integer creditos,
    @NotNull EstadoCurso estado
) {
}
