package com.event_hub.ms_inscriptions.domain.model;

import lombok.Builder;

@Builder
public record UserData(
        String id,
        String name,
        String email,
        String roles
) {

}
