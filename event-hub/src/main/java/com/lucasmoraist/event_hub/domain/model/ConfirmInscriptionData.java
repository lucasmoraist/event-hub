package com.lucasmoraist.event_hub.domain.model;

public record ConfirmInscriptionData(
        String inscriptionId,
        String userName,
        String userEmail,
        String eventTitle,
        String eventDate,
        String eventTime,
        String eventLocation
) {

}
