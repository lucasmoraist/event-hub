package com.event_hub.ms_inscriptions.infra.producer;

import com.event_hub.ms_inscriptions.domain.model.ConfirmInscriptionData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class InscriptionProducerTest {

    @Mock
    StreamBridge streamBridge;
    @InjectMocks
    InscriptionProducer userMsProducer;

    @Test
    @DisplayName("Should send message to queue")
    void case01() {
        Message<ConfirmInscriptionData> message = new Message<>() {
            @Override
            public ConfirmInscriptionData getPayload() {
                return new ConfirmInscriptionData(
                        "684a03295dde8f0254ebc62a",
                        "John Doe",
                        "lukisures123@gmail.com",
                        "Tech Conference 2023",
                        "2023-10-15",
                        "09:00",
                        "Convention Center, Cityville"
                );
            }

            @Override
            public MessageHeaders getHeaders() {
                return null;
            }
        };

        userMsProducer.sendMessage(message);

        verify(streamBridge, times(1))
                .send(anyString(), any());
    }

}