package org.test.projectjavaservice.modal.dto.req;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
public class BookingRequest {
    @NotNull(message = "ID court can not be blank")
    private Long courtId;

    @NotNull(message = "Booking date can not be blank")
    @FutureOrPresent(message = "Booking date must be today or in the future")
    private LocalDate bookingDate;

    @NotNull(message = "ID time slot can not be blank")
    private Long timeSlotId;
}
