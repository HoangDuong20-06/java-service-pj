package org.test.projectjavaservice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.test.projectjavaservice.modal.Role;
import org.test.projectjavaservice.modal.User;
import org.test.projectjavaservice.modal.dto.req.RegisterRequest;
import org.test.projectjavaservice.modal.dto.res.UserResponse;
import org.test.projectjavaservice.repository.UserRepository;
import org.test.projectjavaservice.service.AuthService;
@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserResponse register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "username has exist");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "email has exist");
        }
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setRole(Role.CUSTOMER);
        user.setIsEnabled(true);

        User savedUser = userRepository.save(user);
        return convertToResponseDTO(savedUser);
    }

    @Override
    public UserResponse convertToResponseDTO(User user) {
        UserResponse dto = new UserResponse();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setFullName(user.getFullName());
        dto.setEmail(user.getEmail());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setRole(user.getRole());
        dto.setIsEnabled(user.getIsEnabled());
        return dto;
    }
}
