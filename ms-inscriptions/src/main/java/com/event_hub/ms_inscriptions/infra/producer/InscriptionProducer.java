package com.event_hub.ms_inscriptions.infra.producer;

import com.event_hub.ms_inscriptions.domain.model.ConfirmInscriptionData;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Log4j2
@Component
@RequiredArgsConstructor
public class InscriptionProducer {

    public static final String TO_CONFIRM_INSCRIPTION = "toConfirmInscriptionConsumer-out-0";

    private final StreamBridge streamBridge;

    public void sendMessage(Message<ConfirmInscriptionData> data) {
        log.debug("Sending message to queue: {}", data);
        streamBridge.send(TO_CONFIRM_INSCRIPTION, data);
    }

}
