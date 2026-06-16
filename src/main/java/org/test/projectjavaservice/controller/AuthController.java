package org.test.projectjavaservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.test.projectjavaservice.modal.dto.req.ForgotPasswordRequest;
import org.test.projectjavaservice.modal.dto.req.LoginRequest;
import org.test.projectjavaservice.modal.dto.req.RegisterRequest;
import org.test.projectjavaservice.modal.dto.req.TokenRefreshRequest;
import org.test.projectjavaservice.modal.dto.res.ApiResponse;
import org.test.projectjavaservice.modal.dto.res.JwtResponse;
import org.test.projectjavaservice.modal.dto.res.UserResponse;
import org.test.projectjavaservice.service.AuthService;
import org.test.projectjavaservice.service.LogoutService;
import org.test.projectjavaservice.service.SecurityAuthService;
import org.test.projectjavaservice.service.TokenRefreshService;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final SecurityAuthService securityAuthService;
    private final TokenRefreshService tokenRefreshService;
    private final LogoutService logoutService;
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserResponse>> register(
            @Valid @RequestBody RegisterRequest request) {
        UserResponse data = authService.register(request);
        ApiResponse<UserResponse> response = ApiResponse.<UserResponse>builder()
                .status(HttpStatus.CREATED.value())
                .success(true)
                .message("Register successfully")
                .error(null)
                .path("/api/v1/auth/register")
                .timestamp(LocalDateTime.now())
                .data(data)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<JwtResponse>> login(
            @Valid @RequestBody LoginRequest request) {
        JwtResponse data = securityAuthService.authenticateUser(request);
        ApiResponse<JwtResponse> response = ApiResponse.<JwtResponse>builder()
                .status(HttpStatus.OK.value())
                .success(true)
                .message("Login successfully")
                .error(null)
                .path("/api/v1/auth/login")
                .timestamp(LocalDateTime.now())
                .data(data)
                .build();
        return ResponseEntity.ok(response);
    }
    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(
            @Valid @RequestBody TokenRefreshRequest request) {
        JwtResponse data = tokenRefreshService.refreshAccessToken(request);
        Map<String,String> mp = new HashMap<>();
        mp.put("accessToken", data.getAccessToken());
        ApiResponse response = ApiResponse .builder()
                .status(HttpStatus.OK.value())
                .success(true)
                .message("Refresh token successfully")
                .error(null)
                .path("/api/v1/auth/refresh")
                .timestamp(LocalDateTime.now())
                .data(mp)
                .build();
        return ResponseEntity.ok(response);
    }
    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout(
            @RequestHeader("Authorization") String authHeader) {
        logoutService.logout(authHeader);
        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .status(HttpStatus.OK.value())
                .success(true)
                .message("Logout successfully")
                .error(null)
                .path("/api/v1/auth/logout")
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.ok(response);
    }
    @PostMapping("/forgot-password")
    public ResponseEntity<ApiResponse<Void>> forgotPassword(
            @Valid @RequestBody ForgotPasswordRequest request) {
        authService.resetPassword(request);
        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .status(HttpStatus.OK.value())
                .success(true)
                .message("New password has been set successfully.")
                .error(null)
                .path("/api/v1/auth/forgot-password")
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.ok(response);
    }

}
