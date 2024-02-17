package com.fortnox.carrental.service;

import com.fortnox.carrental.dao.RentingEntity;

public interface RenterService {
    RentingEntity getById(String id);
    RentingEntity saveRenter(RentingEntity entity);
}
