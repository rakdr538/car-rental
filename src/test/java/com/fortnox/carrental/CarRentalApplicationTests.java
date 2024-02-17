package com.fortnox.carrental;

import com.fortnox.carrental.dao.Vehicle;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ContainerTest
class CarRentalApplicationIT {
    private static final String BASE_URL = "/api/v1";
	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    @Autowired
    private MockMvc mockMvc;

    @Test
    void contextLoads() {
    }

    @Test
    void testCreateVehicle() throws Exception {

        var json = Vehicle.builder()
                .vehicleModel("CSV123")
                .manufacturedBy("Volvo")
                .vehicleModel("S60")
                .pricePerDay(1500)
				.build();


        var vehicle = mockMvc.perform(post(BASE_URL + "/admin/vehicle")
                        .contentType(MediaType.APPLICATION_JSON).content(OBJECT_MAPPER.writeValueAsString(json)))
                .andExpect(status().isOk())
				.andReturn();

		assertNotNull(vehicle.getResponse().getContentAsString());
        assertEquals(OBJECT_MAPPER.writeValueAsString(json), vehicle.getResponse().getContentAsString());
    }

}
