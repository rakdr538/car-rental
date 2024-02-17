package com.fortnox.carrental.usecases;

import com.fortnox.carrental.dao.RentalDetail;
import com.fortnox.carrental.dao.RentalStatus;
import com.fortnox.carrental.dao.RentingEntity;
import com.fortnox.carrental.dao.Vehicle;
import com.fortnox.carrental.dto.CreateRentalRequest;
import com.fortnox.carrental.dto.Rental;
import com.fortnox.carrental.dto.RentalDetailsMapper;
import com.fortnox.carrental.exceptions.RenterNotFoundException;
import com.fortnox.carrental.exceptions.ReservationFailedException;
import com.fortnox.carrental.service.RentalService;
import com.fortnox.carrental.service.RenterService;
import com.fortnox.carrental.service.VehicleService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.temporal.ChronoUnit;

@Component
@RequiredArgsConstructor
public class CreateRentalContractUseCase {
    private final RentalService rentalService;
    private final VehicleService vehicleService;
    private final RenterService renterService;

    @Transactional
    public Rental doCreate(CreateRentalRequest rentalRequest) {
        var rentalDetails = rentalService.getAllRentalsDetailsByVehicleId(rentalRequest.getVehiclePlateNo(),
                rentalRequest.getFromDate(), rentalRequest.getToDate());
        var vehicle = vehicleService.getVehicleById(rentalRequest.getVehiclePlateNo());
        if (rentalDetails.isEmpty()) {
            var renter = getRenter(rentalRequest);
            var expectedRentalCost = (vehicle.getPricePerDay() * (ChronoUnit.DAYS.between(
                    rentalRequest.getFromDate(), rentalRequest.getToDate())));
            var newRental = getRentalDetail(rentalRequest, vehicle, renter, expectedRentalCost);

            return RentalDetailsMapper.mapToRental(rentalService.saveRental(newRental));
        } else {
            throw new ReservationFailedException(vehicle.getVehicleModel(), vehicle.getManufacturedBy());
        }
    }

    private static RentalDetail getRentalDetail(CreateRentalRequest rentalRequest,
                                                Vehicle vehicle,
                                                RentingEntity renter,
                                                long expectedRentalCost) {
        return RentalDetailsMapper.mapToRentalDetail(
                rentalRequest.getFromDate(),
                rentalRequest.getToDate(),
                vehicle,
                renter,
                expectedRentalCost,
                RentalStatus.RESERVED);
    }

    private RentingEntity getRenter(CreateRentalRequest rentalRequest) {
        try {
            return renterService.getById(rentalRequest.getEmail());
        } catch (RenterNotFoundException e) {
            // log creating new renter
            return renterService.saveRenter(RentingEntity.builder()
                    .name(rentalRequest.getName())
                    .age(rentalRequest.getAge())
                    .email(rentalRequest.getEmail())
                    .build());
        }
    }
}
