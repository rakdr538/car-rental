package com.fortnox.carrental.dto;

import com.fortnox.carrental.dao.RentalDetail;
import com.fortnox.carrental.dao.RentalStatus;
import com.fortnox.carrental.dao.RentingEntity;
import com.fortnox.carrental.dao.Vehicle;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class RentalDetailsMapper {

    public static Rental mapToRental(RentalDetail rentalDetail) {
        return Rental.builder()
                .name(rentalDetail.getRenter().getName())
                .manufacturedBy(rentalDetail.getVehicle().getManufacturedBy())
                .vehicleModel(rentalDetail.getVehicle().getVehicleModel())
                .fromDate(rentalDetail.getCollectedAt())
                .toDate(rentalDetail.getDroppedAt())
                .totalNoOfDays((int) ChronoUnit.DAYS.between(rentalDetail.getCollectedAt(), rentalDetail.getDroppedAt()))
                .expectedTotalPrice(rentalDetail.getTotalPriceInSek())
                .build();

    }

    public static RentalDetail mapToRentalDetail(LocalDate from,
                                                 LocalDate to,
                                                 Vehicle vehicle,
                                                 RentingEntity renter,
                                                 Long amt,
                                                 RentalStatus status) {
        return RentalDetail.builder()
                .vehicle(vehicle)
                .renter(renter)
                .collectedAt(from)
                .droppedAt(to)
                .totalPriceInSek(amt)
                .rentalStatus(status)
                .build();
    }
}
