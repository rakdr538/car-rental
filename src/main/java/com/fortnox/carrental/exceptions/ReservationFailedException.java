package com.fortnox.carrental.exceptions;

public class ReservationFailedException extends RuntimeException {
    public ReservationFailedException(String vehicleModel, String manufacturedBy) {
        super(String.format("Unfortunately %s %s, is no longer available for booking!", manufacturedBy, vehicleModel));
    }
}
