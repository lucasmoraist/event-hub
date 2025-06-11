package com.event_hub.ms_inscriptions.application.utils;

import com.event_hub.ms_inscriptions.domain.entity.Inscriptions;
import com.event_hub.ms_inscriptions.domain.model.EventsData;
import com.event_hub.ms_inscriptions.domain.model.UserData;
import com.event_hub.ms_inscriptions.infra.controller.response.InscriptionResponse;
import lombok.experimental.UtilityClass;

@UtilityClass
public class InscriptionResponseUtils {
    public static InscriptionResponse build(Inscriptions inscription, UserData user, EventsData event) {
        return InscriptionResponse.builder()
                .id(inscription.getId())
                .userName(user.name())
                .userEmail(user.email())
                .eventTitle(event.title())
                .eventDate(event.date())
                .eventTime(event.time())
                .eventLocation(event.location())
                .status(inscription.getStatus().name())
                .build();
    }
}
