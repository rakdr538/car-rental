package com.fortnox.carrental.usecases;

import com.fortnox.carrental.service.RentalService;
import com.fortnox.carrental.dto.Rental;
import com.fortnox.carrental.dto.RentalDetailsMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class GetAllRentalContracts {
    private final RentalService service;


    public List<Rental> doGet() {
        var allContracts = service.getAllRentals();
        return allContracts
                .stream()
                .map(RentalDetailsMapper::mapToRental)
                .collect(Collectors.toList());
    }
}
