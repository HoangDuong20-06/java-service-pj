package org.test.projectjavaservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.test.projectjavaservice.modal.dto.req.BookingRequest;
import org.test.projectjavaservice.modal.dto.req.ChangePasswordRequest;
import org.test.projectjavaservice.modal.dto.res.ApiResponse;
import org.test.projectjavaservice.modal.dto.res.BookingResponse;
import org.test.projectjavaservice.service.AuthService;
import org.test.projectjavaservice.service.BookingService;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/customer/")
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
public class UserController {
    private final BookingService bookingService;
    private final AuthService authService;
    @PostMapping("/bookings")
    public ResponseEntity<ApiResponse> bookCourt(
            @Valid @RequestBody BookingRequest request,
            Principal principal) {
        String currentUsername = principal.getName();
        BookingResponse data = bookingService.createBooking(request, currentUsername);
        ApiResponse<BookingResponse> response =
                ApiResponse.<BookingResponse>builder()
                        .status(HttpStatus.CREATED.value())
                        .success(true)
                        .message("Create booking successfully. Please wait for approval.")
                        .error(null)
                        .path("/api/v1/customer/bookings")
                        .timestamp(LocalDateTime.now())
                        .data(data)
                        .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }
    @GetMapping("/history")
    public ResponseEntity<ApiResponse<List<BookingResponse>>> getHistory(
            Principal principal) {
        List<BookingResponse> data = bookingService.getMyBookingHistory(principal.getName());
        ApiResponse<List<BookingResponse>> response =
                ApiResponse.<List<BookingResponse>>builder()
                        .status(HttpStatus.OK.value())
                        .success(true)
                        .message("Get booking history successfully.")
                        .error(null)
                        .path("/api/v1/customer/history")
                        .timestamp(LocalDateTime.now())
                        .data(data)
                        .build();
        return ResponseEntity.ok(response);
    }
    @PatchMapping("/change-password")
    public ResponseEntity<ApiResponse> changePassword(
            @Valid @RequestBody ChangePasswordRequest request, Principal principal) {
        authService.changePassword(request, principal.getName());
        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .status(HttpStatus.OK.value())
                .success(true)
                .message("Change password successfully.")
                .error(null)
                .path("/api/v1/customer/change-password")
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.ok(response);

    }
}
