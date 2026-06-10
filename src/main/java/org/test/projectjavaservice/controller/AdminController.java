package org.test.projectjavaservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.test.projectjavaservice.modal.dto.res.UserResponse;
import org.test.projectjavaservice.service.AdminService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    @Autowired
    private AdminService adminService;
    @GetMapping("/search")
    public List<UserResponse> searchUsers(@RequestParam String keyword){

        return adminService.searchAndFilterUsers(keyword);
    }
}
