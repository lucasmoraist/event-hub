package com.event_hub.ms_user.application.service.impl;

import com.event_hub.ms_user.application.usecases.FindUserByIdUseCase;
import com.event_hub.ms_user.application.usecases.ListUsersUseCase;
import com.event_hub.ms_user.application.usecases.SaveUserUseCase;
import com.event_hub.ms_user.application.usecases.UpdateUserUseCase;
import com.event_hub.ms_user.infra.controller.request.UserRequest;
import com.event_hub.ms_user.infra.controller.response.UserResponse;
import com.event_hub.ms_user.application.service.UserService;
import com.event_hub.ms_user.application.usecases.DeleteUserUseCase;
import com.event_hub.ms_user.application.usecases.FindUserByEmailUseCase;
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
