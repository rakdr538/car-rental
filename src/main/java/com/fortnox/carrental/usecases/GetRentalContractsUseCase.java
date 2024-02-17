package com.fortnox.carrental.usecases;

import com.fortnox.carrental.service.RentalService;
import com.fortnox.carrental.dto.Rental;
import com.fortnox.carrental.dto.RentalDetailsMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetRentalContractsUseCase {
    private final RentalService service;


    public Rental getRentalContractById(Integer id) {
        var rentalContract = service.getRentalDetailsById(id);
        return RentalDetailsMapper.mapToRental(rentalContract);
    }
}
