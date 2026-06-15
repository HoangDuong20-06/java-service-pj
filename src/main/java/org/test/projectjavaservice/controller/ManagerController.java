package org.test.projectjavaservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.test.projectjavaservice.modal.dto.req.UpdateBookingStatusRequest;
import org.test.projectjavaservice.modal.dto.res.ApiResponse;
import org.test.projectjavaservice.modal.dto.res.BookingResponse;
import org.test.projectjavaservice.service.BookingService;
import org.test.projectjavaservice.service.ManagerSevice;

import java.util.List;

@RestController
@RequestMapping("/api/v1/manager")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('MANAGER','ADMIN')")
public class ManagerController {
    private final BookingService bookingService;
    private final ManagerSevice managerSevice;
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
    @PostMapping("/courts/{courtId}/images")
    public ResponseEntity<ApiResponse<List<String>>> uploadCourtImages(@PathVariable Long courtId, @RequestParam("files") List<MultipartFile> files) {
        List<String> urls = managerSevice.uploadCourtImages(courtId, files);
        ApiResponse<List<String>> response = ApiResponse.<List<String>>builder()
                .success(true)
                .message("Upload ảnh thành công")
                .data(urls)
                .build();

        return ResponseEntity.ok(response);
    }
}
