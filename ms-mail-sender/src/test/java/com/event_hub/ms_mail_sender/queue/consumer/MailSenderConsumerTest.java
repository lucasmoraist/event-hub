package com.event_hub.ms_mail_sender.queue.consumer;

import com.event_hub.ms_mail_sender.dto.ConfirmEmailData;
import com.event_hub.ms_mail_sender.dto.ConfirmInscriptionData;
import com.event_hub.ms_mail_sender.exceptions.ConsumerException;
import com.event_hub.ms_mail_sender.message.OrchestratorMessage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.messaging.Message;

import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MailSenderConsumerTest {

    @Mock
    OrchestratorMessage orchestratorMessage;
    @InjectMocks
    MailSenderConsumer mailSenderConsumer;

    @Test
    @DisplayName("Test conumer queue 'confirmEmail'")
    void case01() {
        Consumer<Message<ConfirmEmailData>> consumer = mailSenderConsumer.fromConfirmEmailConsumer();
        Message<ConfirmEmailData> message = mock(Message.class);
        consumer.accept(message);
        verify(orchestratorMessage, times(1))
                .orchestratorMessageConfirmEmail(message);
    }

    @Test
    @DisplayName("Test conumer queue 'confirmInscription'")
    void case02() {
        Consumer<Message<ConfirmInscriptionData>> consumer = mailSenderConsumer.fromConfirmInscriptionConsumer();
        Message<ConfirmInscriptionData> message = mock(Message.class);
        consumer.accept(message);
        verify(orchestratorMessage, times(1))
                .orchestratorMessageConfirmInscription(message);
    }

    @Test
    @DisplayName("Test consumer queue 'confirmInscription' with exception")
    void case03() {
        doThrow(new ConsumerException("Error to consume and process message", new RuntimeException()))
                .when(orchestratorMessage).orchestratorMessageConfirmInscription(any());

        Consumer<Message<ConfirmInscriptionData>> consumer = mailSenderConsumer.fromConfirmInscriptionConsumer();
        Message<ConfirmInscriptionData> message = mock(Message.class);

        assertThrows(ConsumerException.class, () -> consumer.accept(message));
    }

    @Test
    @DisplayName("Test consumer queue 'confirmEmail' with exception")
    void case04() {
        doThrow(new ConsumerException("Error to consume and process message", new RuntimeException()))
                .when(orchestratorMessage).orchestratorMessageConfirmEmail(any());

        Consumer<Message<ConfirmEmailData>> consumer = mailSenderConsumer.fromConfirmEmailConsumer();
        Message<ConfirmEmailData> message = mock(Message.class);

        assertThrows(ConsumerException.class, () -> consumer.accept(message));
    }

}