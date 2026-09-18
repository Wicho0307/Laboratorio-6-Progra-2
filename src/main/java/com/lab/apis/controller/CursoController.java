package com.lab.apis.controller;

import com.lab.apis.dto.ApiError;
import com.lab.apis.dto.ApiResponse;
import com.lab.apis.dto.CursoRequest;
import com.lab.apis.model.Curso;
import com.lab.apis.service.CursoService;
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
@RequestMapping("/api/cursos")
@Tag(name = "Cursos", description = "Ejercicios 2 y 5: administración de cursos universitarios")
public class CursoController {

    private final CursoService service;

    public CursoController(CursoService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(summary = "Crear un curso")
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Curso creado"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Datos inválidos",
            content = @Content(schema = @Schema(implementation = ApiError.class))),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "409", description = "Código duplicado",
            content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<ApiResponse<Curso>> crear(@Valid @RequestBody CursoRequest request) {
        Curso curso = service.crear(request);
        return ResponseEntity.created(URI.create("/api/cursos/" + curso.id()))
            .body(new ApiResponse<>("Curso creado correctamente", curso));
    }

    @GetMapping
    @Operation(summary = "Consultar todos los cursos")
    public ResponseEntity<ApiResponse<List<Curso>>> consultarTodos() {
        return ResponseEntity.ok(new ApiResponse<>(
            "Lista de cursos obtenida correctamente", service.consultarTodos()));
    }

    @GetMapping("/codigo/{codigo}")
    @Operation(summary = "Consultar un curso por código")
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Curso encontrado"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Curso no encontrado",
            content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<ApiResponse<Curso>> consultarPorCodigo(
            @PathVariable @NotBlank String codigo) {
        return ResponseEntity.ok(new ApiResponse<>(
            "Curso encontrado", service.consultarPorCodigo(codigo)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar completamente un curso")
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Curso actualizado"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Datos inválidos",
            content = @Content(schema = @Schema(implementation = ApiError.class))),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Curso no encontrado",
            content = @Content(schema = @Schema(implementation = ApiError.class))),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "409", description = "Código duplicado",
            content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<ApiResponse<Curso>> actualizar(
            @PathVariable Long id, @Valid @RequestBody CursoRequest request) {
        return ResponseEntity.ok(new ApiResponse<>(
            "Curso actualizado correctamente", service.actualizar(id, request)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un curso")
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "204", description = "Curso eliminado"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Curso no encontrado",
            content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
