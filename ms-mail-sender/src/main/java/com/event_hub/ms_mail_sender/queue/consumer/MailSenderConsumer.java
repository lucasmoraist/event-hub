package com.event_hub.ms_mail_sender.queue.consumer;

import com.event_hub.ms_mail_sender.dto.ConfirmEmailData;
import com.event_hub.ms_mail_sender.dto.ConfirmInscriptionData;
import com.event_hub.ms_mail_sender.exceptions.ConsumerException;
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
    public Consumer<Message<ConfirmEmailData>> fromConfirmEmailConsumer() {
        try {
            return orchestratorMessage::orchestratorMessageConfirmEmail;
        } catch (ConsumerException e) {
            log.error("Error to consume and process message", e);
            throw new ConsumerException("Error to consume and process message", e);
        }
    }

    @Bean
    public Consumer<Message<ConfirmInscriptionData>> fromConfirmInscriptionConsumer() {
        try {
            return orchestratorMessage::orchestratorMessageConfirmInscription;
        } catch (ConsumerException e) {
            log.error("Error to consume and process message", e);
            throw new ConsumerException("Error to consume and process message", e);
        }
    }

}
