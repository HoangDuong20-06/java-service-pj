package org.test.projectjavaservice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.test.projectjavaservice.modal.Role;
import org.test.projectjavaservice.modal.User;
import org.test.projectjavaservice.modal.dto.res.UserResponse;
import org.test.projectjavaservice.repository.UserRepository;
import org.test.projectjavaservice.service.AdminService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<UserResponse> searchAndFilterUsers(String nameKeyword) {
        return userRepository
                .findByFullNameContainingIgnoreCase(nameKeyword, Pageable.unpaged()
                )
                .getContent()
                .stream()
                .map(user -> {
                    UserResponse dto = new UserResponse();
                    dto.setId(user.getId());
                    dto.setUsername(user.getUsername());
                    dto.setEmail(user.getEmail());
                    dto.setRole(user.getRole());
                    dto.setIsEnabled(user.getIsEnabled());
                    dto.setFullName(user.getFullName());
                    dto.setPhoneNumber(user.getPhoneNumber());
                    return dto;
                })
                .toList();
    }
}
