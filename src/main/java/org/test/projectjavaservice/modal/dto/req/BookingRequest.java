package org.test.projectjavaservice.modal.dto.req;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
public class BookingRequest {
    @NotNull(message = "ID Sân không được để trống")
    private Long courtId;

    @NotNull(message = "Ngày đặt không được để trống")
    private LocalDate bookingDate;

    @NotNull(message = "ID khung giờ không được để trống")
    private Long timeSlotId;
}
