package com.fortnox.carrental.dao;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "vehicle")
public class Vehicle {
    @Id
    @NotEmpty
    @Size(min = 6, message = "Registration number should have at least 6 characters.")
    @Pattern(regexp = "^[a-zA-Z0-9\\s]+$", message = "Registration number should be only alphanumeric.")
    @Column(name = "vehicle_plate_no")
    String vehiclePlateNo;

    @Column(name = "manufactured_by")
    String manufacturedBy;

    @Column(name = "vehicle_model")
    String vehicleModel;

    @NotNull
    @Column(name = "price_per_day")
    Integer pricePerDay;
}
