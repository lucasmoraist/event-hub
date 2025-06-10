package com.event_hub.ms_mail_sender.email;

import com.event_hub.ms_mail_sender.dto.ConfirmEmailData;
import com.event_hub.ms_mail_sender.dto.ConfirmInscriptionData;

public interface EmailService {
    void confirmEmail(ConfirmEmailData data);
    void confirmInscription(ConfirmInscriptionData inscriptions);
}
