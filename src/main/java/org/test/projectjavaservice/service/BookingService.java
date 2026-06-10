package org.test.projectjavaservice.service;

import org.springframework.stereotype.Service;
import org.test.projectjavaservice.modal.dto.req.BookingRequest;
import org.test.projectjavaservice.modal.dto.res.BookingResponse;
@Service
public interface BookingService {
    BookingResponse createBooking(BookingRequest request, String currentUsername);
}
