package org.test.projectjavaservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.test.projectjavaservice.modal.dto.req.UpdateBookingStatusRequest;
import org.test.projectjavaservice.modal.dto.res.ApiResponse;
import org.test.projectjavaservice.modal.dto.res.BookingResponse;
import org.test.projectjavaservice.service.BookingService;

@RestController
@RequestMapping("/api/v1/manager")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('MANAGER','ADMIN')")
public class ManagerController {
    private final BookingService bookingService;
    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<BookingResponse>> updateStatus(@PathVariable Long id, @Valid @RequestBody UpdateBookingStatusRequest request) {
        BookingResponse data = bookingService.updateStatus(id, request);
        ApiResponse<BookingResponse> response = ApiResponse.<BookingResponse>builder()
                .success(true)
                .message("Cập nhật trạng thái đơn đặt sân thành công")
                .data(data)
                .build();
        return ResponseEntity.ok(response);
    }
}
