package com.fortnox.carrental.dao;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "renting_entity")
public class RentingEntity {
    @NotBlank
    String name;

    @NotBlank
    @Id
    String email;

    @NotNull
    @Min(value = 18, message = "Driver cannot be younger than 18")
    @Max(value = 80, message = "Driver cannot be older than 80")
    Integer age;
}
