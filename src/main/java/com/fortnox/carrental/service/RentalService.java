package com.fortnox.carrental.service;

import com.fortnox.carrental.dao.RentalDetail;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public interface RentalService {
    RentalDetail saveRental(RentalDetail rentalDetail);
    RentalDetail getRentalDetailsById(Integer id);
    List<RentalDetail> getAllRentals();
    List<RentalDetail> getAllRentalsInProgress(LocalDate from, LocalDate to);
}
