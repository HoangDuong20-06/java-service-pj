package org.test.projectjavaservice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.test.projectjavaservice.modal.dto.req.UpdateUserRequest;
import org.test.projectjavaservice.modal.dto.res.UserResponse;

@Service
public interface AdminService {
    UserResponse updateUser(Long id, UpdateUserRequest request);
    Page<UserResponse> getUsers(String keyword, Pageable pageable);
    void softDeleteUser(Long id);

}
