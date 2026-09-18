package com.lab.apis.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Libro registrado en la biblioteca")
public record Libro(
    @Schema(example = "1") Long id,
    @Schema(example = "Cien años de soledad") String titulo,
    @Schema(example = "Gabriel García Márquez") String autor,
    @Schema(example = "9780307474728") String isbn,
    @Schema(example = "1967") Integer anioPublicacion,
    EstadoLibro estado
) {
}
