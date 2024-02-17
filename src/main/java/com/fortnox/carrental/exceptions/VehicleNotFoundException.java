package com.fortnox.carrental.exceptions;

public class VehicleNotFoundException extends RuntimeException {
    public VehicleNotFoundException(String licPlate) {
        super("Cannot find vehicle with license plate: " + licPlate);
    }
}
