package com.fortnox.carrental.exceptions;

public class UnregisteredRentalContractException extends RuntimeException {
    public UnregisteredRentalContractException(Integer id) {
        super("Cannot find a rental contract by: " + id);
    }
}
