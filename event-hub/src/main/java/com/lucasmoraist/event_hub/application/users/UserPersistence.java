package com.lucasmoraist.event_hub.application.users;

import com.lucasmoraist.event_hub.domain.request.UserRequest;
import com.lucasmoraist.event_hub.domain.response.UserResponse;

import java.util.List;

public interface UserPersistence {
    void saveUser(UserRequest request);
    List<UserResponse> findAllUsers();
    UserResponse findById(String id);
    UserResponse findByEmail(String email);
    void updateUser(String id, UserRequest request);
    void deleteUser(String id, String password);
}
