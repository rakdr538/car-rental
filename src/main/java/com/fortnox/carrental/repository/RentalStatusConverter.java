package com.fortnox.carrental.repository;

import com.fortnox.carrental.dao.RentalStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class RentalStatusConverter implements AttributeConverter<RentalStatus, Integer> {
    @Override
    public Integer convertToDatabaseColumn(RentalStatus rentalStatus) {
        return rentalStatus.getValue();
    }

    @Override
    public RentalStatus convertToEntityAttribute(Integer integer) {
        return RentalStatus.fromRental(integer);
    }
}
