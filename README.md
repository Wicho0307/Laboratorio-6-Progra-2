# Laboratorio 6 - Diseño y desarrollo de APIs REST

Proyecto de Spring Boot que resuelve los seis ejercicios del laboratorio mediante tres APIs:

- biblioteca (ejercicios 1 y 4);
- cursos universitarios (ejercicios 2 y 5);
- reservas de hotel (ejercicios 3 y 6).

Los datos se intercambian como JSON y se guardan temporalmente en listas en memoria. Al reiniciar la aplicación se eliminan todos los registros.

## Tecnologías

- Java 25
- Spring Boot 4.1.1
- Spring Web MVC
- Bean Validation
- Springdoc OpenAPI 3.1.1 y Swagger UI
- Maven

## Estructura

```text
src/main/java/com/lab/apis
├── config       Configuración de OpenAPI
├── controller   Endpoints REST
├── dto          Requests y respuestas HTTP
├── exception    Excepciones y manejo global de errores
├── model        Entidades y estados
├── repository   Listas en memoria
├── service      Reglas de negocio
└── ApisApplication.java
```

## Ejecutar el proyecto

Desde la raíz del proyecto:

```powershell
.\mvnw.cmd spring-boot:run
```

La aplicación queda disponible en `http://localhost:8080`.

Documentación:

- Swagger UI: `http://localhost:8080/swagger-ui.html`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`
- OpenAPI YAML: `http://localhost:8080/v3/api-docs.yaml`

## Diseño de endpoints

### Libros

| Método | Ruta | Request | Response | Estados |
|---|---|---|---|---|
| POST | `/api/libros` | `LibroRequest` | `ApiResponse<Libro>` | 201, 400, 409 |
| GET | `/api/libros` | — | `ApiResponse<List<Libro>>` | 200 |
| GET | `/api/libros/titulo/{titulo}` | parámetro `titulo` | `ApiResponse<Libro>` | 200, 404 |
| PUT | `/api/libros/{id}` | `LibroRequest` | `ApiResponse<Libro>` | 200, 400, 404, 409 |
| DELETE | `/api/libros/{id}` | parámetro `id` | sin contenido | 204, 404 |

Ejemplo de request:

```json
{
  "titulo": "Cien años de soledad",
  "autor": "Gabriel García Márquez",
  "isbn": "9780307474728",
  "anioPublicacion": 1967,
  "estado": "DISPONIBLE"
}
```

Estados admitidos: `DISPONIBLE`, `PRESTADO`, `INACTIVO`.

### Cursos

| Método | Ruta | Request | Response | Estados |
|---|---|---|---|---|
| POST | `/api/cursos` | `CursoRequest` | `ApiResponse<Curso>` | 201, 400, 409 |
| GET | `/api/cursos` | — | `ApiResponse<List<Curso>>` | 200 |
| GET | `/api/cursos/codigo/{codigo}` | parámetro `codigo` | `ApiResponse<Curso>` | 200, 404 |
| PUT | `/api/cursos/{id}` | `CursoRequest` | `ApiResponse<Curso>` | 200, 400, 404, 409 |
| DELETE | `/api/cursos/{id}` | parámetro `id` | sin contenido | 204, 404 |

Ejemplo de request:

```json
{
  "nombre": "Programación II",
  "codigo": "CC-202",
  "creditos": 4,
  "estado": "ACTIVO"
}
```

Estados admitidos: `ACTIVO`, `INACTIVO`.

### Reservas

| Método | Ruta | Request | Response | Estados |
|---|---|---|---|---|
| POST | `/api/reservas` | `ReservaRequest` | `ApiResponse<Reserva>` | 201, 400 |
| GET | `/api/reservas` | — | `ApiResponse<List<Reserva>>` | 200 |
| GET | `/api/reservas/{id}` | parámetro `id` | `ApiResponse<Reserva>` | 200, 404 |
| PUT | `/api/reservas/{id}` | `ReservaRequest` | `ApiResponse<Reserva>` | 200, 400, 404 |
| PATCH | `/api/reservas/{id}/cancelar` | — | `ApiResponse<Reserva>` | 200, 400, 404 |

Ejemplo de request:

```json
{
  "nombreCliente": "Ana López",
  "habitacion": "204B",
  "fechaEntrada": "2026-10-15",
  "fechaSalida": "2026-10-18",
  "estado": "CONFIRMADA"
}
```

Estados admitidos: `PENDIENTE`, `CONFIRMADA`, `CANCELADA`, `FINALIZADA`. La fecha de salida debe ser posterior a la fecha de entrada.

## Formato de respuesta

Respuesta exitosa:

```json
{
  "mensaje": "Libro registrado correctamente",
  "datos": {
    "id": 1,
    "titulo": "Cien años de soledad"
  }
}
```

Respuesta de error:

```json
{
  "fecha": "2026-09-18T12:00:00",
  "codigo": 404,
  "error": "Not Found",
  "mensaje": "No existe una reserva con id: 99",
  "ruta": "/api/reservas/99",
  "validaciones": {}
}
```

## Pruebas

Ejecutar:

```powershell
.\mvnw.cmd test
```

La colección `Postman/Laboratorio 6.postman_collection.json` contiene solicitudes listas para probar las tres APIs.
