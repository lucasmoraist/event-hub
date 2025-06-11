package com.event_hub.ms_user.application.usecases;

import com.event_hub.ms_user.infra.controller.response.UserResponse;
import com.event_hub.ms_user.infra.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class ListUsersUseCase {

    private final UserRepository repository;

    public List<UserResponse> execute() {
        log.info("Fetching all users");
        return this.repository.findAll()
                .stream()
                .map(UserResponse::new)
                .toList();
    }

}
