package org.test.projectjavaservice.modal.dto.res;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
public class BookingResponse {
    private Long id;
    private Long courtId;
    private String courtName;
    private LocalDate bookingDate;
    private Long timeSlotId;
    private String timeSlotDisplay;
    private String status;
    private String customerName;
}
