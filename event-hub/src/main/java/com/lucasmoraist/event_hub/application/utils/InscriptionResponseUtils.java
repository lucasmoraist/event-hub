package com.lucasmoraist.event_hub.application.utils;

import com.lucasmoraist.event_hub.domain.entity.Events;
import com.lucasmoraist.event_hub.domain.entity.Inscriptions;
import com.lucasmoraist.event_hub.domain.entity.User;
import com.lucasmoraist.event_hub.domain.response.InscriptionResponse;
import lombok.experimental.UtilityClass;

@UtilityClass
public class InscriptionResponseUtils {
    public static InscriptionResponse build(Inscriptions inscription, User user, Events event) {
        return InscriptionResponse.builder()
                .id(inscription.getId())
                .userName(user.getName())
                .userEmail(user.getEmail())
                .eventTitle(event.getTitle())
                .eventDate(event.getDate().toString())
                .eventTime(event.getTime().toString())
                .eventLocation(event.getLocation())
                .status(inscription.getStatus().name())
                .build();
    }
}
