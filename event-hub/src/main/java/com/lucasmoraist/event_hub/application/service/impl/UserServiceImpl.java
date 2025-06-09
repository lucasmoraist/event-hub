package com.lucasmoraist.event_hub.application.service.impl;

import com.lucasmoraist.event_hub.application.service.UserService;
import com.lucasmoraist.event_hub.application.usecases.users.DeleteUserUseCase;
import com.lucasmoraist.event_hub.application.usecases.users.FindUserByEmailUseCase;
import com.lucasmoraist.event_hub.application.usecases.users.FindUserByIdUseCase;
import com.lucasmoraist.event_hub.application.usecases.users.ListUsersUseCase;
import com.lucasmoraist.event_hub.application.usecases.users.SaveUserUseCase;
import com.lucasmoraist.event_hub.application.usecases.users.UpdateUserUseCase;
import com.lucasmoraist.event_hub.domain.request.UserRequest;
import com.lucasmoraist.event_hub.domain.response.UserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final DeleteUserUseCase deleteUserUseCase;
    private final FindUserByEmailUseCase findUserByEmailUseCase;
    private final FindUserByIdUseCase findUserByIdUseCase;
    private final ListUsersUseCase listUsersUseCase;
    private final SaveUserUseCase saveUserUseCase;
    private final UpdateUserUseCase updateUserUseCase;

    @Override
    public void saveUser(UserRequest request) {
        this.saveUserUseCase.execute(request);
    }

    @Override
    public List<UserResponse> findAllUsers() {
        return this.listUsersUseCase.execute();
    }

    @Override
    public UserResponse findById(String id) {
        return this.findUserByIdUseCase.execute(id);
    }

    @Override
    public UserResponse findByEmail(String email) {
        return this.findUserByEmailUseCase.execute(email);
    }

    @Override
    public void updateUser(String id, UserRequest request) {
        this.updateUserUseCase.execute(id, request);
    }

    @Override
    public void deleteUser(String id, String password) {
        this.deleteUserUseCase.execute(id, password);
    }

}
