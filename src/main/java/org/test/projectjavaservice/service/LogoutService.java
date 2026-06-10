package org.test.projectjavaservice.service;

import org.springframework.stereotype.Service;

@Service
public interface LogoutService {
    void logout(String authorizationHeader);
}
