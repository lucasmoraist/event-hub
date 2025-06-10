package com.event_hub.ms_mail_sender.message.impl;

import com.event_hub.ms_mail_sender.dto.ConfirmEmailData;
import com.event_hub.ms_mail_sender.dto.ConfirmInscriptionData;
import com.event_hub.ms_mail_sender.email.EmailService;
import com.event_hub.ms_mail_sender.message.OrchestratorMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class OrchestratorMessageImpl implements OrchestratorMessage {

    private final EmailService emailService;

    @Override
    public void orchestratorMessageConfirmEmail(Message<ConfirmEmailData> message) {
        log.debug("** RECEIVED MESSAGE FROM CONFIRM EMAIL **");
        var payload = message.getPayload();
        this.emailService.confirmEmail(payload);
    }

    @Override
    public void orchestratorMessageConfirmInscription(Message<ConfirmInscriptionData> message) {
        log.debug("** RECEIVED MESSAGE FROM CONFIRM INSCRIPTION **");
        var payload = message.getPayload();
        this.emailService.confirmInscription(payload);
    }

}
