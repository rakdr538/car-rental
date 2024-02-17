package com.fortnox.carrental.repository;

import com.fortnox.carrental.dao.RentingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentingEntityRepository extends JpaRepository<RentingEntity, String> {
}
