package com.event_hub.ms_inscriptions.application.service;

import com.event_hub.ms_inscriptions.domain.model.UserData;

public interface UserService {
    UserData getUserById(String userId);
}
