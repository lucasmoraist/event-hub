package com.lucasmoraist.event_hub.infra.doc;

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
                        .title("Event Hub - API de Gerenciamento de Eventos e Inscrições")
                        .version("1.0.0")
                        .description("""
                                Esta API RESTful foi desenvolvida em Java com Spring Boot no formato monolito, com integração a MongoDB, Redis e envio de e-mails via Java Mail Sender.
                                                    
                                ### 🧾 Funcionalidades principais
                                - Cadastro de usuários com validações (nome, e-mail único, senha)
                                - Criação e listagem de eventos com capacidade limitada, data e hora
                                - Inscrição de usuários em eventos com controle de limite e lista de espera
                                - Confirmação e cancelamento de inscrições com envio automático de e-mail
                                - Cache com Redis para otimização da listagem de eventos e usuários

                                ### ✅ Validações aplicadas
                                - E-mail único para cadastro de usuário
                                - Campos obrigatórios: nome, e-mail, senha (usuário); título, data e limite (evento)
                                - Inscrição não permitida em eventos passados ou lotados
                                - Não é permitido o mesmo usuário se inscrever duas vezes no mesmo evento

                                ### 📦 Armazenamento
                                - **MongoDB**: utilizado como banco principal para documentos `User`, `Event` e `Inscription`
                                - **Redis**: utilizado como cache de leitura para melhorar performance nas listagens

                                ### 💌 Notificações por e-mail
                                - Confirmação de inscrição
                                - Cancelamento de inscrição
                                - Confirmação de vaga para usuários em lista de espera
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
