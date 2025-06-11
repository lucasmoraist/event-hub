package com.event_hub.ms_user.infra.controller;

import com.event_hub.ms_user.infra.controller.request.UserRequest;
import com.event_hub.ms_user.infra.controller.response.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping("/users")
@Tag(name = "Users", description = "Operations related to user management")
public interface UserController {

    @Operation(summary = "Create a new user", description = "Endpoint to create a new user in the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created successfully"),
    })
    @PostMapping
    ResponseEntity<Void> saveUser(@Valid @RequestBody UserRequest request);

    @Operation(summary = "Find all users", description = "Endpoint to retrieve a list of all users.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of users retrieved successfully"),
    })
    @GetMapping
    ResponseEntity<List<UserResponse>> findAllUsers();

    @Operation(summary = "Find user by ID", description = "Endpoint to retrieve a user by their unique ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/{id}")
    ResponseEntity<UserResponse> findById(@PathVariable String id);

    @Operation(summary = "Find user by email", description = "Endpoint to retrieve a user by their email address.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/email")
    ResponseEntity<UserResponse> findByEmail(@RequestParam String email);

    @Operation(summary = "Update user", description = "Endpoint to update an existing user's information.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @PatchMapping("/{id}")
    ResponseEntity<Void> updateUser(@PathVariable String id, @RequestBody UserRequest request);

    @Operation(summary = "Delete user", description = "Endpoint to delete a user from the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User deleted successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteUser(@PathVariable String id, @RequestParam String password);

}
