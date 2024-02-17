package com.fortnox.carrental.dao;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "rental_details")
public class RentalDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id", referencedColumnName = "vehicle_plate_no")
    Vehicle vehicle;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "renter_id", referencedColumnName = "email")
    RentingEntity renter;

    @NotNull
    @JsonFormat(pattern="yyyy-MM-dd")
    @Column(name = "collected_at")
    LocalDate collectedAt;

    @NotNull
    @JsonFormat(pattern="yyyy-MM-dd")
    @Column(name = "dropped_at")
    LocalDate droppedAt;

    @NotNull
    @Column(name = "total_price_in_sek")
    Long totalPriceInSek;

    @NotNull
    @Column(name="rental_status")
    private RentalStatus rentalStatus;
}
