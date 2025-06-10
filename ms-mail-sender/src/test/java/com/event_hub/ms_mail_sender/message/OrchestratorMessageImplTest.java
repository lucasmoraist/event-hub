package com.event_hub.ms_mail_sender.message;

import com.event_hub.ms_mail_sender.dto.ConfirmEmailData;
import com.event_hub.ms_mail_sender.dto.ConfirmInscriptionData;
import com.event_hub.ms_mail_sender.email.EmailService;
import com.event_hub.ms_mail_sender.message.impl.OrchestratorMessageImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class OrchestratorMessageImplTest {

    @Mock
    private EmailService emailService;

    @InjectMocks
    private OrchestratorMessageImpl orchestratorMessage;

    @Test
    @DisplayName("Test orchestratorMessageConfirmEmail method")
    void case01() {
        ConfirmEmailData data = new ConfirmEmailData(
                "john@example.com",
                "John Doe"
        );
        Message<ConfirmEmailData> message = new Message<>() {
            @Override
            public ConfirmEmailData getPayload() {
                return data;
            }

            @Override
            public MessageHeaders getHeaders() {
                return new MessageHeaders(new HashMap<>());
            }
        };

        orchestratorMessage.orchestratorMessageConfirmEmail(message);

        assertAll("Message",
                () -> assertNotNull(message),
                () -> assertEquals(data.to(), message.getPayload().to()),
                () -> assertEquals(data.name(), message.getPayload().name())
        );
        verify(emailService, times(1))
                .confirmEmail(data);
    }

    @Test
    @DisplayName("Test orchestratorMessageConfirmInscription method")
    void case02() {
        ConfirmInscriptionData data = new ConfirmInscriptionData(
                "1234567890",
                "Test User",
                "user@example.com",
                "Test Event",
                "2023-10-01",
                "10:00",
                "Test Location"
        );
        Message<ConfirmInscriptionData> message = new Message<>() {
            @Override
            public ConfirmInscriptionData getPayload() {
                return data;
            }

            @Override
            public MessageHeaders getHeaders() {
                return new MessageHeaders(new HashMap<>());
            }
        };

        orchestratorMessage.orchestratorMessageConfirmInscription(message);

        assertAll("Message",
                () -> assertNotNull(message),
                () -> assertEquals(data.inscriptionId(), message.getPayload().inscriptionId()),
                () -> assertEquals(data.userName(), message.getPayload().userName()),
                () -> assertEquals(data.userEmail(), message.getPayload().userEmail()),
                () -> assertEquals(data.eventTitle(), message.getPayload().eventTitle()),
                () -> assertEquals(data.eventDate(), message.getPayload().eventDate()),
                () -> assertEquals(data.eventTime(), message.getPayload().eventTime()),
                () -> assertEquals(data.eventLocation(), message.getPayload().eventLocation())
        );

        verify(emailService, times(1))
                .confirmInscription(data);
    }

}