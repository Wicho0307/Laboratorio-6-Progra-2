package com.lab.apis.controller.ejercicio1;

import com.lab.apis.dto.ApiError;
import com.lab.apis.dto.ApiResponse;
import com.lab.apis.dto.LibroRequest;
import com.lab.apis.model.ejercicio1y4.Libro;
import com.lab.apis.service.LibroService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/api/libros")
@Tag(name = "Libros", description = "Ejercicios 1 y 4: administración de libros de biblioteca")
public class LibroController {

    private final LibroService service;

    public LibroController(LibroService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(summary = "Registrar un libro")
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Libro registrado"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Datos inválidos",
            content = @Content(schema = @Schema(implementation = ApiError.class))),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "409", description = "ISBN duplicado",
            content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<ApiResponse<Libro>> registrar(@Valid @RequestBody LibroRequest request) {
        Libro libro = service.registrar(request);
        return ResponseEntity.created(URI.create("/api/libros/" + libro.id()))
            .body(new ApiResponse<>("Libro registrado correctamente", libro));
    }

    @GetMapping
    @Operation(summary = "Consultar todos los libros")
    public ResponseEntity<ApiResponse<List<Libro>>> consultarTodos() {
        return ResponseEntity.ok(new ApiResponse<>(
            "Lista de libros obtenida correctamente", service.consultarTodos()));
    }

    @GetMapping("/titulo/{titulo}")
    @Operation(summary = "Consultar un libro por título")
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Libro encontrado"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Libro no encontrado",
            content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<ApiResponse<Libro>> consultarPorTitulo(
            @PathVariable @NotBlank String titulo) {
        return ResponseEntity.ok(new ApiResponse<>(
            "Libro encontrado", service.consultarPorTitulo(titulo)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar completamente un libro")
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Libro actualizado"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Datos inválidos",
            content = @Content(schema = @Schema(implementation = ApiError.class))),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Libro no encontrado",
            content = @Content(schema = @Schema(implementation = ApiError.class))),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "409", description = "ISBN duplicado",
            content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<ApiResponse<Libro>> actualizar(
            @PathVariable Long id, @Valid @RequestBody LibroRequest request) {
        return ResponseEntity.ok(new ApiResponse<>(
            "Libro actualizado correctamente", service.actualizar(id, request)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un libro")
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "204", description = "Libro eliminado"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Libro no encontrado",
            content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
