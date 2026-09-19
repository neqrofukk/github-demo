package com.neqrofukk.githubrepo.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI repoOpenApi() {
        return new OpenAPI().info(
                new Info().title("Repo API")
                        .version("1.0")
                        .description("Local and GitHub Repo API")
        );
    }
}
