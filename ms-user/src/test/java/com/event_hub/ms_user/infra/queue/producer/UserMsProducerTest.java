package com.event_hub.ms_user.infra.queue.producer;

import com.event_hub.ms_user.domain.model.ConfirmEmailData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserMsProducerTest {

    @Mock
    StreamBridge streamBridge;
    @InjectMocks
    UserMsProducer userMsProducer;

    @Test
    @DisplayName("Should send message to queue")
    void case01() {
        Message<ConfirmEmailData> message = new Message<>() {
            @Override
            public ConfirmEmailData getPayload() {
                return new ConfirmEmailData(
                        "john@example.com",
                        "John Doe"
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