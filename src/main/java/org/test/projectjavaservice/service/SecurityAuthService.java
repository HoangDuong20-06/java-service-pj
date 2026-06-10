package org.test.projectjavaservice.service;

import org.springframework.stereotype.Service;
import org.test.projectjavaservice.modal.dto.req.LoginRequest;
import org.test.projectjavaservice.modal.dto.res.JwtResponse;
@Service
public interface SecurityAuthService {
    JwtResponse authenticateUser(LoginRequest request);
}
