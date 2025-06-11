package com.event_hub.ms_user.infra.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Schema(
        description = "Request object for creating or updating a user",
        requiredProperties = {"name", "email", "password", "roles"},
        example = """
                {
                        "name": "John Doe",
                        "email": "john@doe.com",
                        "password": "securePassword123"
                }
                """
)
@Builder
public record UserRequest(
        @NotBlank(message = "Name is required")
        String name,
        @Email(message = "Email should be valid")
        @NotBlank(message = "Email is required")
        String email,
        @NotBlank(message = "Password is required")
        String password
) {

}
