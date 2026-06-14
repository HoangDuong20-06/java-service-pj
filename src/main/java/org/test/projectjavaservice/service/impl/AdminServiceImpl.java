package org.test.projectjavaservice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.test.projectjavaservice.modal.Role;
import org.test.projectjavaservice.modal.User;
import org.test.projectjavaservice.modal.dto.req.UpdateUserRequest;
import org.test.projectjavaservice.modal.dto.res.UserResponse;
import org.test.projectjavaservice.repository.UserRepository;
import org.test.projectjavaservice.service.AdminService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public Page<UserResponse> getUsers(String keyword, Pageable pageable) {

        Page<User> users;

        if (keyword == null || keyword.isBlank()) {
            users = userRepository.findByIsEnabledTrue(pageable);
        } else {
            users = userRepository.findByFullNameContainingIgnoreCaseAndIsEnabledTrue(keyword, pageable);
        }
        return users.map(user -> UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole())
                .isEnabled(user.getIsEnabled())
                .fullName(user.getFullName())
                .phoneNumber(user.getPhoneNumber())
                .build());
    }

    @Override
    public void softDeleteUser(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (Boolean.FALSE.equals(user.getIsEnabled())) {
            throw new RuntimeException("User already disabled");
        }
        user.setIsEnabled(false);
        userRepository.save(user);
    }

    @Override
    public UserResponse updateUser(Long id, UpdateUserRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        if (request.getFullName() != null) {
            user.setFullName(request.getFullName());
        }

        if (request.getEmail() != null) {
            user.setEmail(request.getEmail());
        }

        if (request.getPhoneNumber() != null) {
            user.setPhoneNumber(request.getPhoneNumber());
        }
        if (request.getIsEnabled() != null) {
            user.setIsEnabled(request.getIsEnabled());
        }
        User saved = userRepository.save(user);
        UserResponse response = new UserResponse();
        response.setId(saved.getId());
        response.setUsername(saved.getUsername());
        response.setFullName(saved.getFullName());
        response.setEmail(saved.getEmail());
        response.setPhoneNumber(saved.getPhoneNumber());
        response.setIsEnabled(saved.getIsEnabled());
        return response;
    }

}
