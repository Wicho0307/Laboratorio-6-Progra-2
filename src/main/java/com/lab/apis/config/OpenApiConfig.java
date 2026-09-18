package com.lab.apis.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    OpenAPI laboratorioOpenAPI() {
        return new OpenAPI().info(new Info()
            .title("Laboratorio 6 - APIs REST")
            .version("1.0.0")
            .description("Administración en memoria de libros, cursos universitarios y reservas de hotel."));
    }
}
