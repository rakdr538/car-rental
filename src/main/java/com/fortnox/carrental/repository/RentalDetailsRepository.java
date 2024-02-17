package com.fortnox.carrental.repository;

import com.fortnox.carrental.dao.RentalDetail;
import com.fortnox.carrental.dao.RentalStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RentalDetailsRepository extends JpaRepository<RentalDetail, Integer> {

    @Query( value = """
                select * from rental_details
                where rental_status in :statuses
                and (collected_at between :from and :to
                or dropped_at between :from and :to)
                """,
            nativeQuery = true)
    List<RentalDetail> getAllRentalsBetween(@Param("statuses") List<RentalStatus> statuses,
                                            @Param("from") LocalDate from,
                                            @Param("to") LocalDate to);
}
