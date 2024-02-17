package com.fortnox.carrental.service;

import com.fortnox.carrental.dao.RentingEntity;
import com.fortnox.carrental.exceptions.RenterNotFoundException;
import com.fortnox.carrental.repository.RentingEntityRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RenterServiceImpl implements RenterService{
    private final RentingEntityRepository repository;

    @Override
    public RentingEntity getById(String id) {
        return repository.findById(id).orElseThrow(RenterNotFoundException::new);
    }

    @Override
    @Transactional
    public RentingEntity saveRenter(RentingEntity entity) {
        return repository.saveAndFlush(entity);
    }
}
