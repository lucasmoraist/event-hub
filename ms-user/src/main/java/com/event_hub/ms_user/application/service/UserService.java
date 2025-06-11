package com.event_hub.ms_user.application.service;

import com.event_hub.ms_user.infra.controller.request.UserRequest;
import com.event_hub.ms_user.infra.controller.response.UserResponse;

import java.util.List;

public interface UserService {
    void saveUser(UserRequest request);
    List<UserResponse> findAllUsers();
    UserResponse findById(String id);
    UserResponse findByEmail(String email);
    void updateUser(String id, UserRequest request);
    void deleteUser(String id, String password);
}
