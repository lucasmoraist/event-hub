package com.event_hub.ms_user.infra.controller.response;

import com.event_hub.ms_user.domain.entity.User;
import lombok.Builder;

@Builder
public record UserResponse(
        String id,
        String name,
        String email,
        String roles
) {
    public UserResponse(User user) {
        this(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRoles().name()
        );
    }
}
