package com.fortnox.carrental.usecases;

import com.fortnox.carrental.dao.Vehicle;
import com.fortnox.carrental.service.VehicleService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateVehicleUseCase {
    private final VehicleService service;

    @Transactional
    public Vehicle doCreate(Vehicle inVehicle) {
        return service.saveVehicle(inVehicle);
    }
}
