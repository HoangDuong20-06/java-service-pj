package org.test.projectjavaservice.service;

import org.springframework.stereotype.Service;
import org.test.projectjavaservice.modal.dto.req.BookingRequest;
import org.test.projectjavaservice.modal.dto.req.UpdateBookingStatusRequest;
import org.test.projectjavaservice.modal.dto.res.BookingResponse;

import java.util.List;

@Service
public interface BookingService {
    BookingResponse createBooking(BookingRequest request, String currentUsername);
    List<BookingResponse> getMyBookingHistory(String currentUsername);
    BookingResponse updateStatus(Long bookingId, UpdateBookingStatusRequest request);
}
