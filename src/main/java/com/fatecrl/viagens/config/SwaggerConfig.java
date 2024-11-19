package com.fatecrl.viagens.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI viagensOpenAPI(){
    
        return new OpenAPI().info( new Info()
            .title("API Viagens")
            .summary("Projeto de Desenvolvimento para Servidores 2")
            .description("<h2>Projeto de Desenvolvimento para "
            +"Servidores 2</h2><ul><li>Gabriel Pimentel</li><l"
            +"i>Jose Eduardo Peres</li><li>Lucas Amaral</li></"
            +"ul>")
            .license(new License()
                .name("License (MIT)")
                .identifier("MIT")
                .url("https://github.com/edu9988/IDS002-viagens/blob/main/LICENSE")
            )
            .version("v0.0.1")
        );
    }
}
