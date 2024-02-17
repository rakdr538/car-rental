package com.fortnox.carrental;

import com.fortnox.carrental.dao.Vehicle;
import com.fortnox.carrental.dto.Rental;
import com.fortnox.carrental.usecases.CreateVehicleUseCase;
import com.fortnox.carrental.usecases.GetAllRentalContracts;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AdminActionsController {
    private final GetAllRentalContracts getAllRentalContracts;
    private final CreateVehicleUseCase createVehicleUseCase;

    // @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/admin")
    @ResponseStatus(HttpStatus.OK)
    List<Rental> getAllRentalContracts() {
        return getAllRentalContracts.doGet();
    }

    // @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/admin/vehicle")
    @ResponseStatus(HttpStatus.CREATED)
    Vehicle createVehicle(@Valid @RequestBody Vehicle inVehicle) {
        // this is to demo that admin functions can be extended
        return createVehicleUseCase.doCreate(inVehicle);
    }

}
