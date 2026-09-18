package com.lab.apis.dto;

import com.lab.apis.model.EstadoLibro;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(description = "Datos necesarios para registrar o actualizar un libro")
public record LibroRequest(
    @NotBlank @Size(max = 150) @Schema(example = "Cien años de soledad") String titulo,
    @NotBlank @Size(max = 120) @Schema(example = "Gabriel García Márquez") String autor,
    @NotBlank @Size(min = 10, max = 17) @Schema(example = "9780307474728") String isbn,
    @NotNull @Min(1) @Max(2100) @Schema(example = "1967") Integer anioPublicacion,
    @NotNull EstadoLibro estado
) {
}
