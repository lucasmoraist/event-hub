package com.lucasmoraist.event_hub.application.service.impl;

import com.lucasmoraist.event_hub.application.service.UserService;
import com.lucasmoraist.event_hub.application.users.UserPersistence;
import com.lucasmoraist.event_hub.domain.request.UserRequest;
import com.lucasmoraist.event_hub.domain.response.UserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Log4j2
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserPersistence userPersistence;

    @Override
    public void saveUser(UserRequest request) {
        this.userPersistence.saveUser(request);
    }

    @Override
    public List<UserResponse> findAllUsers() {
        return this.userPersistence.findAllUsers();
    }

    @Override
    public UserResponse findById(UUID id) {
        return this.userPersistence.findById(id);
    }

    @Override
    public UserResponse findByEmail(String email) {
        return this.userPersistence.findByEmail(email);
    }

    @Override
    public void updateUser(UUID id, UserRequest request) {
        this.userPersistence.updateUser(id, request);
    }

    @Override
    public void deleteUser(UUID id, String password) {
        this.userPersistence.deleteUser(id, password);
    }

}
