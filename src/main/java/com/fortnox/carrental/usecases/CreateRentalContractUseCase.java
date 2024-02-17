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
import org.springframework.util.CollectionUtils;

import java.time.temporal.ChronoUnit;

@Component
@RequiredArgsConstructor
public class CreateRentalContractUseCase {
    private final GetAllVehiclesFromExistingRentalsUseCase getAllVehiclesFromExistingRentalsUseCase;
    private final RentalService rentalService;
    private final VehicleService vehicleService;
    private final RenterService renterService;

    @Transactional
    public Rental doCreate(CreateRentalRequest rentalRequest) {
        var vehicles = getAllVehiclesFromExistingRentalsUseCase.doGetBetweenDates(rentalRequest.getFromDate(), rentalRequest.getToDate());
        var vehicle = vehicleService.getVehicleById(rentalRequest.getVehiclePlateNo());

        var renter = getRenter(rentalRequest);
        var expectedRentalCost = (vehicle.getPricePerDay() * (ChronoUnit.DAYS.between(rentalRequest.getFromDate(), rentalRequest.getToDate())));
        var newRental = getRentalDetail(rentalRequest, vehicle, renter, expectedRentalCost);

        // if there are no existing reservations then directly create one
        if (CollectionUtils.isEmpty(vehicles)) {
            return RentalDetailsMapper.mapToRental(rentalService.saveRental(newRental));
        }
        // if there are existing reservations then check for vehicle availability
        if (vehicles.stream().map(Vehicle::getVehiclePlateNo).toList().contains(rentalRequest.getVehiclePlateNo())) {
            return RentalDetailsMapper.mapToRental(rentalService.saveRental(newRental));
        } else {
            // log default case when there is conflict
            throw new ReservationFailedException(vehicle.getVehicleModel(), vehicle.getManufacturedBy());
        }
        // log this should never happen.
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
