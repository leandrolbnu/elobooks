package com.elotech.elobooks.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Elobooks API",
                version = "v1",
                description = "Gestão de Biblioteca com Recomendação de Livros"
        )
)
public class OpenApiConfig {}
