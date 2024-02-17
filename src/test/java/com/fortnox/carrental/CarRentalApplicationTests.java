package com.fortnox.carrental;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fortnox.carrental.dao.Vehicle;
import com.fortnox.carrental.dto.CreateRentalRequest;
import com.fortnox.carrental.dto.Rental;
import com.fortnox.carrental.repository.RentalDetailsRepository;
import com.fortnox.carrental.repository.VehicleRepository;
import com.fortnox.carrental.service.RenterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ContainerTest
class CarRentalApplicationIT {
    private static final String BASE_URL = "/api/v1";
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final List<Vehicle> vehicles = new ArrayList<>();
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private RenterService renterService;
    @Autowired
    RentalDetailsRepository rentalDetailsRepository;
    @Autowired
    VehicleRepository vehicleRepository;

    @BeforeEach
    void setUp() {
        OBJECT_MAPPER.registerModule(new JavaTimeModule());

        vehicles.add(Vehicle.builder()
                .vehiclePlateNo("CSV123")
                .manufacturedBy("Volvo")
                .vehicleModel("S60")
                .pricePerDay(1500)
                .build());
        vehicles.add(Vehicle.builder()
                .vehiclePlateNo("CSV133")
                .manufacturedBy("Volkswagen")
                .vehicleModel("Golf")
                .pricePerDay(1333)
                .build());
        vehicles.add(Vehicle.builder()
                .vehiclePlateNo("CSV143")
                .manufacturedBy("Ford")
                .vehicleModel("Mustang")
                .pricePerDay(3000)
                .build());
        vehicles.add(Vehicle.builder()
                .vehiclePlateNo("CSV153")
                .manufacturedBy("Ford")
                .vehicleModel("Transit")
                .pricePerDay(2400)
                .build());
    }

    @Test
    void contextLoads() {
    }

    @Test
    void testCreateVehicle() throws Exception {
        for (Vehicle vehicle : vehicles) {
            var json = OBJECT_MAPPER.writeValueAsString(vehicle);
            var created = mockMvc.perform(post(BASE_URL + "/admin/vehicle")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(json))
                    .andExpect(status().isCreated())
                    .andReturn();

            assertNotNull(created.getResponse().getContentAsString());
            assertEquals(json, created.getResponse().getContentAsString());
        }
    }

    @Test
    void testCreateRentalContracts() throws Exception {
        // first insert all vehicles
        vehicleRepository.saveAll(vehicles);

        final List<CreateRentalRequest> createRequests = new ArrayList<>();
        var plates = vehicles.stream().map(Vehicle::getVehiclePlateNo).toList();

        createRequests.add(getCreateReq(plates.get(0),
                "some1@test.com",
                24,
                "myName",
                LocalDate.of(2024, 3, 18),
                LocalDate.of(2024, 3, 20)));

        createRequests.add(getCreateReq(plates.get(1),
                "some2@test.com",
                42,
                "myNameWithSurname",
                LocalDate.of(2024, 3, 19),
                LocalDate.of(2024, 3, 23)));

        createRequests.add(getCreateReq(plates.get(2),
                "some3@test.com",
                54,
                "myNameWithoutSurname",
                LocalDate.of(2024, 3, 23),
                LocalDate.of(2024, 3, 25)));

        createRequests.add(getCreateReq(plates.get(3),
                "some4@test.com",
                20,
                "onlySurname",
                LocalDate.of(2024, 3, 25),
                LocalDate.of(2024, 4, 7)));

        // now try to create rentals
        for (CreateRentalRequest createRequest : createRequests) {
            var contract = mockMvc.perform(post(BASE_URL + "/rent")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(OBJECT_MAPPER.writeValueAsString(createRequest)))
                    .andExpect(status().isCreated())
                    .andReturn();

            assertNotNull(contract.getResponse().getContentAsString());
            var rental = OBJECT_MAPPER.readValue(contract.getResponse().getContentAsString(), Rental.class);

            assertEquals(ChronoUnit.DAYS.between(createRequest.getFromDate(), createRequest.getToDate()),
                    Long.valueOf(rental.getTotalNoOfDays()));
            assertNotNull(rental.getExpectedTotalPrice());
            assertNotNull(renterService.getById(createRequest.getEmail()));
        }

        // also verify admin end point
        var result = mockMvc.perform(get(BASE_URL + "/admin"))
                .andExpect(status().isOk())
                .andReturn();

        assertNotNull(result.getResponse().getContentAsString());
        var allContracts = OBJECT_MAPPER.readValue(result.getResponse().getContentAsString(),
                new TypeReference<List<Rental>>() {
                });
        assertEquals(8, allContracts.size());
    }

    @Test
    void testGetAllVehiclesBetweenDates() throws Exception {
        // first insert all vehicles
        vehicleRepository.saveAll(vehicles);
        final List<CreateRentalRequest> createRequests = new ArrayList<>();
        var plates = vehicles.stream().map(Vehicle::getVehiclePlateNo).toList();

        createRequests.add(getCreateReq(plates.get(0),
                "some@test.com",
                24,
                "myName",
                LocalDate.of(2024, 2, 18),
                LocalDate.of(2024, 2, 20)));

        createRequests.add(getCreateReq(plates.get(1),
                "some_one@test.com",
                42,
                "myNameWithSurname",
                LocalDate.of(2024, 2, 19),
                LocalDate.of(2024, 2, 23)));

        createRequests.add(getCreateReq(plates.get(2),
                "some_one_else@test.com",
                54,
                "myNameWithoutSurname",
                LocalDate.of(2024, 2, 23),
                LocalDate.of(2024, 2, 25)));

        createRequests.add(getCreateReq(plates.get(3),
                "some_other@test.com",
                20,
                "onlySurname",
                LocalDate.of(2024, 2, 25),
                LocalDate.of(2024, 3, 7)));

        // then insert all rentalContracts
        for (CreateRentalRequest createRequest : createRequests) {
            mockMvc.perform(post(BASE_URL + "/rent")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(OBJECT_MAPPER.writeValueAsString(createRequest)))
                    .andExpect(status().isCreated());
        }

        var result = mockMvc.perform(get(BASE_URL + "/rent")
                        .param("from", "2024-02-20")
                        .param("to", "2024-02-24"))
                .andExpect(status().isOk())
                .andReturn();

        assertNotNull(result.getResponse().getContentAsString());
        var vehicles = OBJECT_MAPPER.readValue(result.getResponse().getContentAsString(),
                new TypeReference<List<Vehicle>>() {
                });
        assertEquals(1, vehicles.size());
    }

    private CreateRentalRequest getCreateReq(String plate,
                                             String email,
                                             Integer age,
                                             String name,
                                             LocalDate from,
                                             LocalDate to) {
        return CreateRentalRequest.builder()
                .vehiclePlateNo(plate)
                .email(email)
                .age(age)
                .name(name)
                .fromDate(from)
                .toDate(to)
                .build();
    }

}
