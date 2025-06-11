package com.event_hub.ms_inscriptions.domain.model;

public record UserData(
        String id,
        String name,
        String email,
        String roles
) {

}
