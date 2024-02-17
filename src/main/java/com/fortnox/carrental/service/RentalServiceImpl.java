package com.fortnox.carrental.service;

import com.fortnox.carrental.dao.RentalDetail;
import com.fortnox.carrental.dao.RentalStatus;
import com.fortnox.carrental.exceptions.UnregisteredRentalContractException;
import com.fortnox.carrental.repository.RentalDetailsRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RentalServiceImpl implements RentalService{
    private final RentalDetailsRepository rentalDetailsRepository;

    @Override
    @Transactional
    public RentalDetail saveRental(RentalDetail newContract) {
        return rentalDetailsRepository.save(newContract);
    }

    @Override
    public RentalDetail getRentalDetailsById(Integer id) {
        return rentalDetailsRepository
                .findById(id)
                .orElseThrow(() -> new UnregisteredRentalContractException(id));
    }

    @Override
    public List<RentalDetail> getAllRentals() {
        return rentalDetailsRepository.findAll();
    }

    @Override
    public List<RentalDetail> getAllRentalsInProgress(LocalDate from, LocalDate to) {
        return rentalDetailsRepository.getAllRentalsBetween(
                        List.of(RentalStatus.IN_PROGRESS, RentalStatus.RESERVED),
                        from,
                        to);
    }
}
