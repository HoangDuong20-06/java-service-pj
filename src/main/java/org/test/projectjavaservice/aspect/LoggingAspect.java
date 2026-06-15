package org.test.projectjavaservice.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.test.projectjavaservice.modal.dto.res.BookingResponse;

@Aspect
@Component
@Slf4j
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
    @Around("execution(* org.test.projectjavaservice.service..*(..))")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        try {
            Object result = joinPoint.proceed();
            long executionTime = System.currentTimeMillis() - start;
            log.info(
                    "[SUCCESS] Method={} Time={}ms",
                    joinPoint.getSignature().getName(),
                    executionTime
            );
            return result;
        } catch (Exception e) {
            long executionTime = System.currentTimeMillis() - start;
            log.error(
                    "[FAILED] Method={} Time={}ms Error={}",
                    joinPoint.getSignature().getName(),
                    executionTime,
                    e.getMessage()
            );
            throw e;
        }
    }
}
