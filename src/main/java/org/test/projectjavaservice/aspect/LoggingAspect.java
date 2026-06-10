package org.test.projectjavaservice.aspect;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.test.projectjavaservice.modal.dto.res.BookingResponse;

@Aspect
@Component
public class LoggingAspect {
    @AfterReturning(
            pointcut = "execution(* org.test.projectjavaservice.service.BookingService.createBooking(..))",
            returning = "result"
    )
    public void logBookingSuccess(Object result) {
        if (result instanceof BookingResponse) {
            BookingResponse dto = (BookingResponse) result;
            System.out.printf("[AUDIT - SUCCESS] Customer %s booking success: %s in  %s, Time slot: %s%n",
                    dto.getCustomerName(), dto.getCourtName(), dto.getBookingDate(), dto.getTimeSlotDisplay());
        }
    }

    @AfterThrowing(
            pointcut = "execution(* org.test.projectjavaservice.service.BookingService.createBooking(..))",
            throwing = "exception"
    )
    public void logBookingFailed(Exception exception) {
        System.err.printf("[AUDIT - FAILED] The request to book the field failed due to an error.: %s%n", exception.getMessage());
    }
}
