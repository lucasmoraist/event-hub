package com.event_hub.ms_inscriptions.domain.model;

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
