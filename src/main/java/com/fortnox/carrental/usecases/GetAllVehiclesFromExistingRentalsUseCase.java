package com.fortnox.carrental.usecases;

import com.fortnox.carrental.dao.RentalDetail;
import com.fortnox.carrental.dao.Vehicle;
import com.fortnox.carrental.exceptions.MisMacthedInDatesException;
import com.fortnox.carrental.service.RentalService;
import com.fortnox.carrental.service.VehicleService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class GetAllVehiclesFromExistingRentalsUseCase {
    private final RentalService rentalService;
    private final VehicleService vehicleService;

    /**
     * Returns all available vehicles between
     * @param from LocalDate
     * @param to LocalDate
     * @return Vehicle List
     */
    public List<Vehicle> doGetBetweenDates(@NotNull LocalDate from, @NotNull LocalDate to) {
        var today = LocalDate.now();
        if (today.isAfter(from)
                || today.isAfter(to)
                || from.isAfter(to)) {
            throw new MisMacthedInDatesException(from, to);
        }

        var vehicles = vehicleService.getAllVehicles();
        var rentals = rentalService.getAllRentalsInProgress(from, to);
        var occupiedVehicles = rentals.stream().map(RentalDetail::getVehicle).toList();
        if (!occupiedVehicles.isEmpty()) {
            vehicles.removeAll(occupiedVehicles);
        }
        return vehicles;
    }
}
