package com.devops.grababargain.config.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    private final String address = System.getenv("ENV_IP");

    @Bean
    public OpenAPI openApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("API Grab a Gargain - Devops 2024")
                        .description("API simples para estudo da dockerização.")
                        .termsOfService("")
                        .version("1.0.0"))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8080")
                                .description("Ambiente de desenvolvimento"),
                        new Server()
                                .url("http://localhost:8081")
                                .description("Ambiente de desenvolvimento"),
                        new Server()
                                .url("http://" + address + ":8080")
                                .description("Ambiente de testes")));
    }
}
