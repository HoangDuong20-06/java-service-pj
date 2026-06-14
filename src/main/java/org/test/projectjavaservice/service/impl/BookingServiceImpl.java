package org.test.projectjavaservice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.test.projectjavaservice.modal.*;
import org.test.projectjavaservice.modal.dto.req.BookingRequest;
import org.test.projectjavaservice.modal.dto.req.UpdateBookingStatusRequest;
import org.test.projectjavaservice.modal.dto.res.BookingResponse;
import org.test.projectjavaservice.repository.BookingRepository;
import org.test.projectjavaservice.repository.CourtRepository;
import org.test.projectjavaservice.repository.TimeSlotRepository;
import org.test.projectjavaservice.repository.UserRepository;
import org.test.projectjavaservice.service.BookingService;

import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public List<BookingResponse> getMyBookingHistory(String currentUsername) {
        User user = userRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Account not exist"));
        List<Booking> myBookings = bookingRepository.findAllByUserIdOrderByBookingDateDesc(user.getId());
        return myBookings.stream()
                .map(booking -> {
                    BookingResponse dto = new BookingResponse();
                    dto.setId(booking.getId());
                    dto.setCourtId(booking.getCourt().getId());
                    dto.setCourtName(booking.getCourt().getCourtName());
                    dto.setBookingDate(booking.getBookingDate());
                    dto.setTimeSlotId(booking.getTimeSlot().getId());
                    dto.setTimeSlotDisplay(booking.getTimeSlot().getStartTime() + " - " + booking.getTimeSlot().getEndTime());
                    dto.setStatus(booking.getStatus());
                    dto.setCustomerName(user.getFullName());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public BookingResponse updateStatus(Long bookingId, UpdateBookingStatusRequest request) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found booking"));
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User currentUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found"));
        if (currentUser.getRole() == Role.MANAGER) {
            Long managerId = booking.getCourt()
                    .getCluster()
                    .getManager()
                    .getId();
            if (!managerId.equals(currentUser.getId())) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Bạn không quản lý sân này");
            }
        }

        String newStatus = request.getStatus().toUpperCase();
        if (!newStatus.equals("CONFIRMED") && !newStatus.equals("CANCELLED")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Status must be CONFIRMED or CANCELLED");
        }
        if (!booking.getStatus().equals("PENDING")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Booking status must be PENDING");
        }
        booking.setStatus(newStatus);
        Booking updatedBooking = bookingRepository.save(booking);
        BookingResponse dto = new BookingResponse();
        dto.setId(updatedBooking.getId());
        dto.setCourtId(updatedBooking.getCourt().getId());
        dto.setCourtName(updatedBooking.getCourt().getCourtName());
        dto.setBookingDate(updatedBooking.getBookingDate());
        dto.setStatus(updatedBooking.getStatus());
        dto.setCustomerName(updatedBooking.getUser().getFullName());
        return dto;
    }
}
