package com.fortnox.carrental.service;

import com.fortnox.carrental.dao.Vehicle;
import com.fortnox.carrental.exceptions.VehicleNotFoundException;
import com.fortnox.carrental.repository.VehicleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VehicleServiceImpl implements VehicleService{
    private final VehicleRepository vehicleRepository;

    @Override
    @Transactional
    public Vehicle saveVehicle(Vehicle inVehicle) {
        return vehicleRepository.saveAndFlush(inVehicle);
    }

    @Override
    public Vehicle getVehicleById(String licPlate) {
        return vehicleRepository
                .findById(licPlate)
                .orElseThrow(()-> new VehicleNotFoundException(licPlate));
    }

    @Override
    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }
}
