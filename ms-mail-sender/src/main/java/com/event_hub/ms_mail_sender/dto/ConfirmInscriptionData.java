package com.event_hub.ms_mail_sender.dto;

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
