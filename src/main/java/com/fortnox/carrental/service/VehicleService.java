package com.fortnox.carrental.service;

import com.fortnox.carrental.dao.Vehicle;

import java.util.List;

public interface VehicleService {
    Vehicle saveVehicle(Vehicle inVehicle);
    Vehicle getVehicleById(String licPlate);
    List<Vehicle> getAllVehicles();
}
