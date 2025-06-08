package com.lucasmoraist.event_hub.application.users;

import com.lucasmoraist.event_hub.domain.request.UserRequest;
import com.lucasmoraist.event_hub.domain.response.UserResponse;

import java.util.List;
import java.util.UUID;

public interface UserPersistence {
    void saveUser(UserRequest request);
    List<UserResponse> findAllUsers();
    UserResponse findById(UUID id);
    UserResponse findByEmail(String email);
    void updateUser(UUID id, UserRequest request);
    void deleteUser(UUID id, String password);
}
