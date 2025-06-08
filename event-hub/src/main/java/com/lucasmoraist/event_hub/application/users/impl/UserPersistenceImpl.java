package com.lucasmoraist.event_hub.application.users.impl;

import com.lucasmoraist.event_hub.application.users.UserPersistence;
import com.lucasmoraist.event_hub.domain.entity.User;
import com.lucasmoraist.event_hub.domain.request.UserRequest;
import com.lucasmoraist.event_hub.domain.response.UserResponse;
import com.lucasmoraist.event_hub.infra.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Log4j2
@Service
@RequiredArgsConstructor
public class UserPersistenceImpl implements UserPersistence {

    private final UserRepository repository;

    @Override
    public void saveUser(UserRequest request) {
        User user = new User(request);
        this.repository.save(user);
        log.debug("User with email {} created successfully", request.email());
    }

    @Override
    public List<UserResponse> findAllUsers() {
        log.info("Fetching all users");
        return this.repository.findAll()
                .stream()
                .map(UserResponse::new)
                .toList();
    }

    @Override
    public UserResponse findById(UUID id) {
        log.debug("Fetching user with id {}", id);
        return this.repository.findById(id)
                .map(UserResponse::new)
                .orElseThrow(() -> {
                    log.error("User with id {} not found", id);
                    return new RuntimeException("User not found");
                });
    }

    @Override
    public UserResponse findByEmail(String email) {
        log.debug("Fetching user with email {}", email);
        return this.repository.findByEmail(email)
                .map(UserResponse::new)
                .orElseThrow(() -> {
                    log.error("User with email {} not found", email);
                    return new RuntimeException("User not found");
                });
    }

    @Override
    public void updateUser(UUID id, UserRequest request) {
        log.debug("Updating user with id {}", id);
        User user = this.getUserById(id);

        user.updateUser(request);
        this.repository.save(user);
        log.debug("User with id {} updated successfully", id);
    }

    @Override
    public void deleteUser(UUID id, String password) {
        log.debug("Deleting user with id {}", id);
        User user = this.getUserById(id);

        if (!user.getPassword().equals(password)) {
            log.error("Password does not match for user with id {}", id);
            throw new RuntimeException("Password does not match");
        }

        this.repository.delete(user);
        log.debug("User with id {} deleted successfully", id);
    }

    private User getUserById(UUID id) {
        return this.repository.findById(id)
                .orElseThrow(() -> {
                    log.error("User with id {} not found", id);
                    return new RuntimeException("User not found");
                });
    }

}
