package com.lucasmoraist.event_hub.application.email;

import com.lucasmoraist.event_hub.domain.entity.Inscriptions;
import com.lucasmoraist.event_hub.domain.model.EmailData;

public interface EmailService {
    void confirmEmail(EmailData data);
    void confirmInscription(Inscriptions inscriptions);
}
