package com.lab.apis;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class ApisApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void registraYConsultaLibroPorTitulo() throws Exception {
        mockMvc.perform(post("/api/libros")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                      "titulo": "El principito",
                      "autor": "Antoine de Saint-Exupéry",
                      "isbn": "9780156012195",
                      "anioPublicacion": 1943,
                      "estado": "DISPONIBLE"
                    }
                    """))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.datos.id").isNumber());

        mockMvc.perform(get("/api/libros/titulo/{titulo}", "El principito"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.datos.isbn").value("9780156012195"));
    }

    @Test
    void creaYConsultaCursoPorCodigo() throws Exception {
        mockMvc.perform(post("/api/cursos")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                      "nombre": "Programación II",
                      "codigo": "CC-202",
                      "creditos": 4,
                      "estado": "ACTIVO"
                    }
                    """))
            .andExpect(status().isCreated());

        mockMvc.perform(get("/api/cursos/codigo/{codigo}", "CC-202"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.datos.nombre").value("Programación II"));
    }

    @Test
    void creaYCancelaReserva() throws Exception {
        mockMvc.perform(post("/api/reservas")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                      "nombreCliente": "Ana López",
                      "habitacion": "204B",
                      "fechaEntrada": "2099-10-15",
                      "fechaSalida": "2099-10-18",
                      "estado": "CONFIRMADA"
                    }
                    """))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.datos.id").value(1));

        mockMvc.perform(patch("/api/reservas/1/cancelar"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.datos.estado").value("CANCELADA"));
    }

    @Test
    void rechazaReservaConFechasInvertidas() throws Exception {
        mockMvc.perform(post("/api/reservas")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                      "nombreCliente": "Luis Pérez",
                      "habitacion": "101",
                      "fechaEntrada": "2099-12-20",
                      "fechaSalida": "2099-12-19",
                      "estado": "PENDIENTE"
                    }
                    """))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.mensaje").value(
                "La fecha de salida debe ser posterior a la fecha de entrada"));
    }

    @Test
    void devuelve404CuandoNoExisteElRecurso() throws Exception {
        mockMvc.perform(get("/api/reservas/999"))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.codigo").value(404));
    }

    @Test
    void exponeLaDocumentacionOpenApi() throws Exception {
        mockMvc.perform(get("/v3/api-docs"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.info.title").value("Laboratorio 6 - APIs REST"))
            .andExpect(jsonPath("$.paths['/api/libros']").exists())
            .andExpect(jsonPath("$.paths['/api/cursos']").exists())
            .andExpect(jsonPath("$.paths['/api/reservas']").exists());
    }
}
