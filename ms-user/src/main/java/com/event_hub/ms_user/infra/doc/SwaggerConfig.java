package com.event_hub.ms_user.infra.doc;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Microservice User Management API")
                        .version("1.0.0")
                        .description("""
                                Essa é uma API RESTful para gerenciamento de usuários,
                                permitindo operações como criação, atualização, exclusão e listagem de usuários.
                                """)
                        .contact(new Contact()
                                .name("Lucas de Morais Nascimento Taguchi")
                                .email("luksmnt1101@gmail.com")
                                .url("https://github.com/lucasmoraist")
                        )
//                        .license(new License()
//                                .name("MIT License")
//                                .url("https://opensource.org/licenses/MIT")
//                        )
                );
    }

}
