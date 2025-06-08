package com.lucasmoraist.event_hub.infra.controller.impl;

import com.lucasmoraist.event_hub.application.service.UserService;
import com.lucasmoraist.event_hub.domain.request.UserRequest;
import com.lucasmoraist.event_hub.domain.response.UserResponse;
import com.lucasmoraist.event_hub.infra.controller.UserController;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@Log4j2
@RestController
public class UserControllerImpl implements UserController {

    @Autowired
    private UserService service;

    @Override
    public ResponseEntity<Void> saveUser(UserRequest request) {
        log.info("Saving user with request: {}", request);
        this.service.saveUser(request);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<List<UserResponse>> findAllUsers() {
        log.info("Listing all users");
        return ResponseEntity.ok().body(this.service.findAllUsers());
    }

    @Override
    public ResponseEntity<UserResponse> findById(UUID id) {
        log.info("Finding user by ID: {}", id);
        return ResponseEntity.ok().body(this.service.findById(id));
    }

    @Override
    public ResponseEntity<UserResponse> findByEmail(String email) {
        log.info("Finding user by email: {}", email);
        return ResponseEntity.ok().body(this.service.findByEmail(email));
    }

    @Override
    public ResponseEntity<Void> updateUser(UUID id, UserRequest request) {
        log.info("Updating user with ID: {}", id);
        this.service.updateUser(id, request);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> deleteUser(UUID id, String password) {
        log.info("Deleting user with ID: {}", id);
        this.service.deleteUser(id, password);
        return ResponseEntity.ok().build();
    }

}
