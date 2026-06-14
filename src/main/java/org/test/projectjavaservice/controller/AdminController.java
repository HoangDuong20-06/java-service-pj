package org.test.projectjavaservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.test.projectjavaservice.modal.dto.req.UpdateUserRequest;
import org.test.projectjavaservice.modal.dto.res.UserResponse;
import org.test.projectjavaservice.service.AdminService;


@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;
    @GetMapping("/users")
    public Page<UserResponse> getUsers(@RequestParam(required = false) String keyword,@PageableDefault(
            size = 5,
            sort = "id",
            direction = Sort.Direction.DESC) Pageable pageable ) {
        return adminService.getUsers(keyword, pageable);
    }
    @PutMapping("/users/{id}")
    public UserResponse updateUser(@PathVariable Long id, @RequestBody UpdateUserRequest request){
        return adminService.updateUser(id, request);
    }
    @PatchMapping("/users/{id}")
    public UserResponse updateUserPartial(@PathVariable Long id, @RequestBody UpdateUserRequest request){
        return adminService.updateUser(id, request);
    }
    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        adminService.softDeleteUser(id);
        return ResponseEntity.ok("User deleted successfully");
    }
}
