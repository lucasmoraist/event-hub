package com.event_hub.ms_mail_sender.queue.consumer;

import com.event_hub.ms_mail_sender.dto.ConfirmEmailData;
import com.event_hub.ms_mail_sender.dto.ConfirmInscriptionData;
import com.event_hub.ms_mail_sender.message.OrchestratorMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;

import java.util.function.Consumer;

@Log4j2
@Configuration
@RequiredArgsConstructor
public class MailSenderConsumer {

    private final OrchestratorMessage orchestratorMessage;

    @Bean
    public Consumer<Message<ConfirmEmailData>> confirmEmailConsumer() {
        try {
            return orchestratorMessage::orchestratorMessageConfirmEmail;
        } catch (Exception e) {
            // TODO: Implementar exceção personalizada
            log.error("Error creating consumer for orchestrator messages from gateway sandbox", e);
            throw new RuntimeException("Failed to create consumer for orchestrator messages from gateway sandbox", e);
        }
    }

    @Bean
    public Consumer<Message<ConfirmInscriptionData>> confirmInscriptionConsumer() {
        try {
            return orchestratorMessage::orchestratorMessageConfirmInscription;
        } catch (Exception e) {
            // TODO: Implementar exceção personalizada
            log.error("Error creating consumer for orchestrator messages from inscription service", e);
            throw new RuntimeException("Failed to create consumer for orchestrator messages from inscription service", e);
        }
    }

}
