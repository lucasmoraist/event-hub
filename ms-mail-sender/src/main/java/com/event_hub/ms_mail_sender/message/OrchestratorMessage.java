package com.event_hub.ms_mail_sender.message;

import com.event_hub.ms_mail_sender.dto.ConfirmEmailData;
import com.event_hub.ms_mail_sender.dto.ConfirmInscriptionData;
import org.springframework.messaging.Message;

public interface OrchestratorMessage {

    void orchestratorMessageConfirmEmail(Message<ConfirmEmailData> message);
    void orchestratorMessageConfirmInscription(Message<ConfirmInscriptionData> message);

}
