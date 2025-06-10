package com.lucasmoraist.event_hub.infra.queue.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Log4j2
@Component
@RequiredArgsConstructor
public class EventHubProducer {

    public static final String TO_CONFIRM_EMAIL = "toConfirmEmailConsumer-out-0";
    public static final String TO_CONFIRM_INSCRIPTION = "toConfirmInscriptionConsumer-out-0";

    private final StreamBridge streamBridge;

    public void sendMessage(Message<?> data, String binding) {
        log.debug("Sending message to queue: {}", data);
        streamBridge.send(binding, data);
    }

}
