package com.lab.apis.exception;

import com.lab.apis.dto.ApiError;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RecursoNoEncontradoException.class)
    ResponseEntity<ApiError> manejarNoEncontrado(
            RecursoNoEncontradoException exception, HttpServletRequest request) {
        return respuesta(HttpStatus.NOT_FOUND, exception.getMessage(), request, Map.of());
    }

    @ExceptionHandler(RecursoDuplicadoException.class)
    ResponseEntity<ApiError> manejarDuplicado(
            RecursoDuplicadoException exception, HttpServletRequest request) {
        return respuesta(HttpStatus.CONFLICT, exception.getMessage(), request, Map.of());
    }

    @ExceptionHandler(ReglaNegocioException.class)
    ResponseEntity<ApiError> manejarReglaNegocio(
            ReglaNegocioException exception, HttpServletRequest request) {
        return respuesta(HttpStatus.BAD_REQUEST, exception.getMessage(), request, Map.of());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ApiError> manejarValidacion(
            MethodArgumentNotValidException exception, HttpServletRequest request) {
        Map<String, String> validaciones = new LinkedHashMap<>();
        exception.getBindingResult().getFieldErrors().forEach(error ->
            validaciones.putIfAbsent(error.getField(), error.getDefaultMessage()));
        return respuesta(HttpStatus.BAD_REQUEST, "Los datos enviados no son válidos", request, validaciones);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    ResponseEntity<ApiError> manejarJsonInvalido(
            HttpMessageNotReadableException exception, HttpServletRequest request) {
        return respuesta(HttpStatus.BAD_REQUEST,
            "El cuerpo JSON contiene valores o formatos no válidos", request, Map.of());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    ResponseEntity<ApiError> manejarRestriccion(
            ConstraintViolationException exception, HttpServletRequest request) {
        return respuesta(HttpStatus.BAD_REQUEST,
            "Uno de los parámetros enviados no es válido", request, Map.of());
    }

    private ResponseEntity<ApiError> respuesta(HttpStatus estado, String mensaje,
            HttpServletRequest request, Map<String, String> validaciones) {
        ApiError error = new ApiError(LocalDateTime.now(), estado.value(), estado.getReasonPhrase(),
            mensaje, request.getRequestURI(), validaciones);
        return ResponseEntity.status(estado).body(error);
    }
}
