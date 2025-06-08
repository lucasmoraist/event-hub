package com.lucasmoraist.event_hub.domain.response;

import com.lucasmoraist.event_hub.domain.entity.User;

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
