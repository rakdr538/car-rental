package com.fortnox.carrental;

import com.fortnox.carrental.dao.RentalDetail;
import com.fortnox.carrental.dao.RentalStatus;
import com.fortnox.carrental.dao.RentingEntity;
import com.fortnox.carrental.dao.Vehicle;
import com.fortnox.carrental.repository.RentalDetailsRepository;
import com.fortnox.carrental.repository.RentingEntityRepository;
import com.fortnox.carrental.repository.VehicleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(properties = { "spring.liquibase.enabled=false" })
@ContainerTest
public class CarRentalApplicationPostgresqlIT {
    @Autowired
    private RentalDetailsRepository rentalDetailsRepository;
    @Autowired
    private VehicleRepository vehicleRepository;
    @Autowired
    private RentingEntityRepository rentingEntityRepository;

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final List<RentalDetail> rentalDetails = new ArrayList<>();
    private static final List<Vehicle> vehicles = new ArrayList<>();
    private static final List<RentingEntity> renters = new ArrayList<>();

    @Test
    void testConnectionsToDb() {
        assertNotNull(rentalDetailsRepository);
        assertNotNull(vehicleRepository);
        assertNotNull(rentingEntityRepository);
    }

    @Test
    void testInsertionsToDb() {
        createSomeData();
        insertData();
    }

    @Test
    void testFetchAllRentalsWithDates() {
        if (rentalDetails.isEmpty()) {
            createSomeData();
            insertData();
        }

        var rentals = rentalDetailsRepository.getAllRentalsBetween(
                List.of(RentalStatus.IN_PROGRESS, RentalStatus.RESERVED),
                LocalDate.of(2024, 2, 16),
                LocalDate.of(2024, 2, 20));

        assertFalse(rentals.isEmpty());
    }

    @Test
    void testAdminGetAllRentals() {
        if (rentalDetails.isEmpty()) {
            createSomeData();
            insertData();
        }
    }

    private void createSomeData() {
        if (vehicles.isEmpty()) {
            creatingAndAddingVehicles();
        }
        if (renters.isEmpty()) {
            creatingAndAddingRenters();
        }
        if (rentalDetails.isEmpty()) {
            creatingAndAddingRentalDetails();
        }
    }

    private void creatingAndAddingRentalDetails() {
        assertEquals(4, vehicles.size());
        assertEquals(4, renters.size());

        rentalDetails.add(getRentalDetail(renters.get(0),
                vehicles.get(0),
                LocalDate.of(2024, 2, 15),
                LocalDate.of(2024, 2, 17),
                12345L,
                RentalStatus.IN_PROGRESS));

        rentalDetails.add(getRentalDetail(renters.get(1),
                vehicles.get(1),
                LocalDate.of(2024, 2, 17),
                LocalDate.of(2024, 2, 20),
                12345L,
                RentalStatus.COMPLETED));

        rentalDetails.add(getRentalDetail(renters.get(2),
                vehicles.get(2),
                LocalDate.of(2024, 2, 20),
                LocalDate.of(2024, 2, 21),
                12346L,
                RentalStatus.RESERVED));

        rentalDetails.add(getRentalDetail(renters.get(3),
                vehicles.get(3),
                LocalDate.of(2024, 2, 21),
                LocalDate.of(2024, 2, 24),
                12347L,
                RentalStatus.RESERVED));
    }

    private void creatingAndAddingRenters() {
        renters.add(getRenter("some@test.com", "my_name", 24));
        renters.add(getRenter("some_email@test.com", "my_name_surname", 30));
        renters.add(getRenter("some_test@test.com", "my_name2", 20));
        renters.add(getRenter("sample@test.com", "my_name'd", 54));
    }

    private void creatingAndAddingVehicles() {
        vehicles.add(getTestVehicle("CSX123", "Volvo", "S60", 1500));
        vehicles.add(getTestVehicle("CSX133", "Volkswagen", "Golf", 1333));
        vehicles.add(getTestVehicle("CSX143", "Ford", "Mustang", 3000));
        vehicles.add(getTestVehicle("CSX153", "Ford", "Transit", 2400));
    }

    private RentalDetail getRentalDetail(
            RentingEntity renter,
            Vehicle vehicle,
            LocalDate from,
            LocalDate to,
            Long amt,
            RentalStatus status) {
        return RentalDetail.builder()
                .renter(renter)
                .vehicle(vehicle)
                .collectedAt(from)
                .droppedAt(to)
                .totalPriceInSek(amt)
                .rentalStatus(status)
                .build();
    }

    private RentingEntity getRenter(String email, String name, Integer age) {
        return RentingEntity.builder()
                .email(email)
                .name(name)
                .age(age)
                .build();
    }

    private Vehicle getTestVehicle(String plate, String comp, String model, Integer amt) {
        return Vehicle.builder()
                .vehiclePlateNo(plate)
                .manufacturedBy(comp)
                .vehicleModel(model)
                .pricePerDay(amt)
                .build();
    }

    private void insertData() {
        vehicles
                .stream()
                .map(vehicle -> vehicleRepository.save(vehicle))
                .toList()
                .forEach(vehicle -> assertNotNull(vehicle.getVehiclePlateNo()));
        renters
                .stream()
                .map(renter -> rentingEntityRepository.save(renter))
                .toList()
                .forEach(renter -> assertNotNull(renter.getEmail()));
        rentalDetails
                .stream()
                .map(rental -> rentalDetailsRepository.save(rental))
                .toList()
                .forEach(rental -> assertNotNull(rental.getId()));

        rentalDetailsRepository.save(getRentalDetail(renters.get(3),
                vehicles.get(3),
                LocalDate.of(2024, 3, 21),
                LocalDate.of(2024, 2, 24),
                12347L,
                RentalStatus.RESERVED));
    }
}
