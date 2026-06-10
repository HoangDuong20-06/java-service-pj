package org.test.projectjavaservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.test.projectjavaservice.modal.dto.req.BookingRequest;
import org.test.projectjavaservice.modal.dto.res.ApiResponse;
import org.test.projectjavaservice.modal.dto.res.BookingResponse;
import org.test.projectjavaservice.service.BookingService;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/customer/")
@PreAuthorize("hasRole('CUSTOMER')")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;

    @PostMapping("bookings")
    public ResponseEntity<ApiResponse<BookingResponse>> bookCourt(@Valid @RequestBody BookingRequest request, Principal principal) {

        String currentUsername = principal.getName();
        BookingResponse data = bookingService.createBooking(request, currentUsername);

        ApiResponse<BookingResponse> response = ApiResponse.<BookingResponse>builder()
                .success(true)
                .message("Create booking successfully . Please wait for approval.")
                .data(data)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
