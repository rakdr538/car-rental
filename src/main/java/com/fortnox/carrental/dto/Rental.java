package com.fortnox.carrental.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class Rental {
    String name;
    String manufacturedBy;
    String vehicleModel;
    @JsonFormat(pattern="yyyy-MM-dd")
    LocalDate fromDate;
    @JsonFormat(pattern="yyyy-MM-dd")
    LocalDate toDate;
    // this is always rounded to the next whole number
    Integer totalNoOfDays;
    Long expectedTotalPrice;
}
