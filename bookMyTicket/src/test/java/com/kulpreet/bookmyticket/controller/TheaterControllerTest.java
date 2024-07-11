package com.kulpreet.bookmyticket.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
class TheaterControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void onboardTheater() {
        try {
            MvcResult output = mockMvc.perform(post("/theater/onboard")
                            .content("{\n" +
                                    "    \"cityId\": 6,\n" +
                                    "    \"name\": \"MIRAJ CINEMAS\"\n" +
                                    "}")
                            .contentType("application/json"))
                    .andExpect(status().isCreated()).andReturn();
            log.info(output.getResponse().getContentAsString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void onboardTheaterUniqueFailure() {
        try {
            MvcResult output = mockMvc.perform(post("/theater/onboard")
                    .content("{\n" +
                            "    \"cityId\": 6,\n" +
                            "    \"name\": \"PVR\"\n" +
                            "}")
                    .contentType("application/json"))
                    .andExpect(status().isBadRequest()).andReturn();
            log.info(output.getResponse().getContentAsString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void getTheatersByCity() {
        try {
            MvcResult output = mockMvc.perform(get("/theater")
                    .param("cityId", "6"))
                    .andExpect(status().isOk()).andReturn();
            log.info(output.getResponse().getContentAsString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void getTheatersAll() {
        try {
            MvcResult output = mockMvc.perform(get("/theater"))
                    .andExpect(status().isOk()).andReturn();
            log.info(output.getResponse().getContentAsString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void addHall() {
        try {
            MvcResult output = mockMvc.perform(post("/theater/addHall")
                    .content("{\n" +
                            "    \"theaterId\": 6,\n" +
                            "    \"name\": \"Screen 1\",\n" +
                            "    \"capacityRow\": 10,\n" +
                            "    \"capacityColumn\": 20\n" +
                            "}")
                    .contentType("application/json"))
                    .andExpect(status().isCreated()).andReturn();
            log.info(output.getResponse().getContentAsString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void addHallUniqueFailure() {
        try {
            MvcResult output = mockMvc.perform(post("/theater/addHall")
                            .content("{\n" +
                                    "    \"theaterId\": 3,\n" +
                                    "    \"name\": \"Screen 1\",\n" +
                                    "    \"capacityRow\": 10,\n" +
                                    "    \"capacityColumn\": 20\n" +
                                    "}")
                            .contentType("application/json"))
                    .andExpect(status().isBadRequest()).andReturn();
            log.info(output.getResponse().getContentAsString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void getHalls() {
        try {
            MvcResult output = mockMvc.perform(get("/theater/6/halls"))
                    .andExpect(status().isOk()).andReturn();
            log.info(output.getResponse().getContentAsString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}