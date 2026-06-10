package org.test.projectjavaservice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.test.projectjavaservice.modal.Booking;
import org.test.projectjavaservice.modal.Court;
import org.test.projectjavaservice.modal.TimeSlot;
import org.test.projectjavaservice.modal.User;
import org.test.projectjavaservice.modal.dto.req.BookingRequest;
import org.test.projectjavaservice.modal.dto.res.BookingResponse;
import org.test.projectjavaservice.repository.BookingRepository;
import org.test.projectjavaservice.repository.CourtRepository;
import org.test.projectjavaservice.repository.TimeSlotRepository;
import org.test.projectjavaservice.repository.UserRepository;
import org.test.projectjavaservice.service.BookingService;

import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CourtRepository courtRepository;
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private TimeSlotRepository timeSlotRepository;
    @Override
    public BookingResponse createBooking(BookingRequest request, String currentUsername) {
        User user = userRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Username not exist"));

        Court court = courtRepository.findById(request.getCourtId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Badminton court not exists"));

        TimeSlot timeSlot = timeSlotRepository.findById(request.getTimeSlotId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Time slot not exist"));
        boolean isSlotOccupied = bookingRepository.existsByCourtIdAndBookingDateAndTimeSlotIdAndStatusIn(
                request.getCourtId(),
                request.getBookingDate(),
                request.getTimeSlotId(),
                List.of("PENDING", "CONFIRMED")
        );

        if (isSlotOccupied) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Time slot is occupied or in pending");
        }
        Booking booking = new Booking();
        booking.setUser(user);
        booking.setCourt(court);
        booking.setBookingDate(request.getBookingDate());
        booking.setTimeSlot(timeSlot);
        booking.setStatus("PENDING");

        Booking savedBooking = bookingRepository.save(booking);
        BookingResponse dto = new BookingResponse();
        dto.setId(savedBooking.getId());
        dto.setCourtId(court.getId());
        dto.setCourtName(court.getCourtName());
        dto.setBookingDate(savedBooking.getBookingDate());
        dto.setTimeSlotId(timeSlot.getId());
        dto.setTimeSlotDisplay(timeSlot.getStartTime() + " - " + timeSlot.getEndTime());
        dto.setStatus(savedBooking.getStatus());
        dto.setCustomerName(user.getFullName());

        return dto;
    }
}
