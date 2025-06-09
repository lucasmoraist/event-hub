package com.lucasmoraist.event_hub.domain.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

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
