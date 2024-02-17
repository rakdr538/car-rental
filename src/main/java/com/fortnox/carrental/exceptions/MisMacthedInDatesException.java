package com.fortnox.carrental.exceptions;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class MisMacthedInDatesException extends RuntimeException {
    public MisMacthedInDatesException(@NotNull LocalDate from, @NotNull LocalDate to) {
        super(String.format("Cannot rent between %s and %s", from, to));
    }
}
