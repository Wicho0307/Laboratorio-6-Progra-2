package com.lab.apis.controller;

import com.lab.apis.dto.ApiError;
import com.lab.apis.dto.ApiResponse;
import com.lab.apis.dto.ReservaRequest;
import com.lab.apis.model.Reserva;
import com.lab.apis.service.ReservaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reservas")
@Tag(name = "Reservas", description = "Ejercicios 3 y 6: administración de reservas de hotel")
public class ReservaController {

    private final ReservaService service;

    public ReservaController(ReservaService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(summary = "Crear una reserva")
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Reserva creada"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Datos o fechas inválidos",
            content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<ApiResponse<Reserva>> crear(@Valid @RequestBody ReservaRequest request) {
        Reserva reserva = service.crear(request);
        return ResponseEntity.created(URI.create("/api/reservas/" + reserva.id()))
            .body(new ApiResponse<>("Reserva creada correctamente", reserva));
    }

    @GetMapping
    @Operation(summary = "Consultar todas las reservas")
    public ResponseEntity<ApiResponse<List<Reserva>>> consultarTodas() {
        return ResponseEntity.ok(new ApiResponse<>(
            "Lista de reservas obtenida correctamente", service.consultarTodas()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Consultar una reserva por id")
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Reserva encontrada"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Reserva no encontrada",
            content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<ApiResponse<Reserva>> consultarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(new ApiResponse<>(
            "Reserva encontrada", service.consultarPorId(id)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar completamente una reserva")
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Reserva actualizada"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Datos o fechas inválidos",
            content = @Content(schema = @Schema(implementation = ApiError.class))),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Reserva no encontrada",
            content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<ApiResponse<Reserva>> actualizar(
            @PathVariable Long id, @Valid @RequestBody ReservaRequest request) {
        return ResponseEntity.ok(new ApiResponse<>(
            "Reserva actualizada correctamente", service.actualizar(id, request)));
    }

    @PatchMapping("/{id}/cancelar")
    @Operation(summary = "Cancelar una reserva")
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Reserva cancelada"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "La reserva no puede cancelarse",
            content = @Content(schema = @Schema(implementation = ApiError.class))),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Reserva no encontrada",
            content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<ApiResponse<Reserva>> cancelar(@PathVariable Long id) {
        return ResponseEntity.ok(new ApiResponse<>(
            "Reserva cancelada correctamente", service.cancelar(id)));
    }
}
