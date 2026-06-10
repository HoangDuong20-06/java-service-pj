package org.test.projectjavaservice.service;

import org.springframework.stereotype.Service;
import org.test.projectjavaservice.modal.User;
import org.test.projectjavaservice.modal.dto.req.RegisterRequest;
import org.test.projectjavaservice.modal.dto.res.UserResponse;

@Service
public interface AuthService {
    UserResponse register(RegisterRequest request);
    UserResponse convertToResponseDTO(User user);
}
