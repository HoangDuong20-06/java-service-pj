package org.test.projectjavaservice.service;

import org.springframework.stereotype.Service;
import org.test.projectjavaservice.modal.User;
import org.test.projectjavaservice.modal.dto.req.ChangePasswordRequest;
import org.test.projectjavaservice.modal.dto.req.ForgotPasswordRequest;
import org.test.projectjavaservice.modal.dto.req.RegisterRequest;
import org.test.projectjavaservice.modal.dto.res.UserResponse;

@Service
public interface AuthService {
    UserResponse register(RegisterRequest request);
    UserResponse convertToResponseDTO(User user);
    void changePassword(ChangePasswordRequest request, String currentUsername);
    void resetPassword(ForgotPasswordRequest request);
}
