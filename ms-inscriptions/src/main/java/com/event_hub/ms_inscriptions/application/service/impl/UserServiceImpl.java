package com.event_hub.ms_inscriptions.application.service.impl;

import com.event_hub.ms_inscriptions.application.service.UserService;
import com.event_hub.ms_inscriptions.domain.model.UserData;
import com.event_hub.ms_inscriptions.infra.client.UserClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;

@Log4j2
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserClient userClient;
    private final ObjectMapper objectMapper;

    @Override
    public UserData getUserById(String userId) {
        log.debug("Fetching user data for userId: {}", userId);
        try {
            Response response = this.userClient.getUserById(userId);
            return parseUserData(response);
        } catch (Exception e) {
            log.error("Error fetching user data for userId: {}", userId, e);
            throw new RuntimeException("Failed to fetch user data", e);
        }
    }

    private UserData parseUserData(Response response) {
        if (response == null || response.body() == null) {
            log.warn("Received null response or body for user data");
            return null;
        }

        try (InputStream in = response.body().asInputStream()) {
            UserData userData = objectMapper.readValue(in, UserData.class);
            log.info("Successfully parsed user data: {}", userData);
            return userData;
        } catch (IOException e) {
            log.error("Error parsing user data from response", e);
            throw new RuntimeException(e);
        }
    }

}
