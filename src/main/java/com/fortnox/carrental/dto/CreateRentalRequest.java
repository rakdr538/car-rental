package com.fortnox.carrental.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class CreateRentalRequest {

    @NotBlank
    @Size(min = 6, message = "Registration number should have at least 6 characters.")
    @Pattern(regexp = "^[a-zA-Z0-9\\s]+$", message = "Registration number should be only alphanumeric.")
    String vehiclePlateNo;

    @NotNull
    @Min(value = 18, message = "Driver cannot be younger than 18 year")
    @Max(value = 80, message = "Driver cannot be older than 80 years")
    Integer age;

    @NotBlank
    String name;

    @NotBlank
    String email;

    @NotNull
    @JsonFormat(pattern="yyyy-MM-dd")
    LocalDate fromDate;

    @NotNull
    @JsonFormat(pattern="yyyy-MM-dd")
    LocalDate toDate;
}
