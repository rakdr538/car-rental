package com.fortnox.carrental.dao;

import lombok.Getter;

@Getter
public enum RentalStatus {
    COMPLETED(1), RESERVED(2), IN_PROGRESS(3);
    private final int value;
    RentalStatus(int value){
        this.value=value;
    }

    public static RentalStatus fromRental(Integer in) {
        return  switch (in) {
            case 1 -> RentalStatus.COMPLETED;
            case 2 -> RentalStatus.RESERVED;
            case 3 -> RentalStatus.IN_PROGRESS;
            default -> throw new IllegalArgumentException("From Rental [" + in + "] not supported");
        };
    }
}
