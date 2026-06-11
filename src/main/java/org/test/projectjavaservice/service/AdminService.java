package org.test.projectjavaservice.service;

import org.springframework.stereotype.Service;
import org.test.projectjavaservice.modal.dto.req.UpdateUserRequest;
import org.test.projectjavaservice.modal.dto.res.UserResponse;

import java.util.List;

@Service
public interface AdminService {
    List<UserResponse> searchAndFilterUsers(String nameKeyword);
    UserResponse updateUser(Long id, UpdateUserRequest request);
}
