package org.test.projectjavaservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.test.projectjavaservice.modal.Booking;

import java.time.LocalDate;
import java.util.List;
@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    boolean existsByCourtIdAndBookingDateAndTimeSlotIdAndStatusIn(
            Long courtId, LocalDate bookingDate, Long timeSlotId, List<String> statuses
    );
    List<Booking> findAllByUserIdOrderByBookingDateDesc(Long userId);


}
