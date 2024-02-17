package com.fortnox.carrental;

import com.fortnox.carrental.exceptions.MisMacthedInDatesException;
import com.fortnox.carrental.exceptions.ReservationFailedException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalCarRentalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleInvalidArgument(MethodArgumentNotValidException exception) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, exception.getMessage());
        problemDetail.setTitle("Method argument not valid");
        exception.getBindingResult().getFieldErrors()
                .forEach(error -> problemDetail
                        .setDetail(error.getField() + ": " + error.getDefaultMessage()));
        return problemDetail;
    }

    @ExceptionHandler(MisMacthedInDatesException.class)
    public ProblemDetail handleCannotRentException(MisMacthedInDatesException e) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ProblemDetail handleConstraintViolationException(ConstraintViolationException e) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(ReservationFailedException.class)
    public ProblemDetail handleReservationFailedException(ReservationFailedException e) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, e.getMessage());
    }
}
