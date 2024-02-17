package com.fortnox.carrental;

import com.fortnox.carrental.dao.Vehicle;
import com.fortnox.carrental.dto.CreateRentalRequest;
import com.fortnox.carrental.dto.Rental;
import com.fortnox.carrental.usecases.GetRentalContractsUseCase;
import com.fortnox.carrental.usecases.CreateRentalContractUseCase;
import com.fortnox.carrental.usecases.GetAllVehiclesFromExistingRentalsUseCase;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class RenterActionsController {

    private final CreateRentalContractUseCase createRentalContractUseCase;
    private final GetRentalContractsUseCase getRentalContractsUseCase;
    private final GetAllVehiclesFromExistingRentalsUseCase getAllVehiclesFromExistingRentalsUseCase;

    // @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/rent")
    @ResponseStatus(HttpStatus.CREATED)
    Rental createNewRentalContract(@Valid @RequestBody CreateRentalRequest rentalRequest){
        return createRentalContractUseCase.doCreate(rentalRequest);
    }

    @GetMapping("/rent/{id}")
    @ResponseStatus(HttpStatus.OK)
    Rental getRentalContractById(@NotNull @PathVariable Integer id) {
        // this is to demo that the functionality can be extended.
        return getRentalContractsUseCase.getRentalContractById(id);
    }

    @GetMapping("/rent")
    @ResponseStatus(HttpStatus.OK)
    List<Vehicle> getAllVehicles(@RequestParam("from") @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate from,
                                 @RequestParam("to") @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate to) {
        return getAllVehiclesFromExistingRentalsUseCase.doGetBetweenDates(from, to);
    }
}
