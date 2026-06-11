package org.test.projectjavaservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.test.projectjavaservice.modal.dto.req.UpdateUserRequest;
import org.test.projectjavaservice.modal.dto.res.UserResponse;
import org.test.projectjavaservice.service.AdminService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/")
public class AdminController {
    @Autowired
    private AdminService adminService;
    @GetMapping("/search")
    public List<UserResponse> findUsers(@RequestParam String keyword){
        return adminService.searchAndFilterUsers(keyword);
    }
    @PutMapping("/update/{id}")
    public UserResponse updateUser(@PathVariable Long id, @RequestBody UpdateUserRequest request){
        return adminService.updateUser(id, request);
    }
    @PatchMapping("/update/{id}")
    public UserResponse updateUserPartial(@PathVariable Long id, @RequestBody UpdateUserRequest request){
        return adminService.updateUser(id, request);
    }
}
