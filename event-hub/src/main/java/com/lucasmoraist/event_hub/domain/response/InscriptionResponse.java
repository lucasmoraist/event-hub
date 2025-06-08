package com.lucasmoraist.event_hub.domain.response;

import lombok.Builder;

@Builder
public record InscriptionResponse(
    String id,
    String userName,
    String userEmail,
    String eventTitle,
    String eventDate,
    String eventTime,
    String eventLocation,
    String status
) {

}
