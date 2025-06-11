package com.event_hub.ms_user.infra.controller.impl;

import com.event_hub.ms_user.infra.controller.UserController;
import com.event_hub.ms_user.infra.controller.request.UserRequest;
import com.event_hub.ms_user.infra.controller.response.UserResponse;
import com.event_hub.ms_user.application.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    public ResponseEntity<UserResponse> findById(String id) {
        log.info("Finding user by ID: {}", id);
        return ResponseEntity.ok().body(this.service.findById(id));
    }

    @Override
    public ResponseEntity<UserResponse> findByEmail(String email) {
        log.info("Finding user by email: {}", email);
        return ResponseEntity.ok().body(this.service.findByEmail(email));
    }

    @Override
    public ResponseEntity<Void> updateUser(String id, UserRequest request) {
        log.info("Updating user with ID: {}", id);
        this.service.updateUser(id, request);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> deleteUser(String id, String password) {
        log.info("Deleting user with ID: {}", id);
        this.service.deleteUser(id, password);
        return ResponseEntity.ok().build();
    }

}
