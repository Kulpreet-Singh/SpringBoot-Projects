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
class CityControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void addCity() {
        try {
            MvcResult output = mockMvc.perform(post("/city")
                            .content("{\n" +
                                    "    \"name\": \"Pune\",\n" +
                                    "    \"state\": \"Maharashtra\"\n" +
                                    "}")
                            .contentType("application/json"))
                    .andExpect(status().isCreated()).andReturn();
            log.info(output.getResponse().getContentAsString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void addCityDuplicateName() {
        try {
            MvcResult output = mockMvc.perform(post("/city")
                            .content("{\n" +
                                    "    \"name\": \"Noida 98\",\n" +
                                    "    \"state\": \"Uttar Pradesh\"\n" +
                                    "}")
                            .contentType("application/json"))
                    .andExpect(status().isBadRequest()).andReturn();
            log.info(output.getResponse().getContentAsString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void getAllCities() {
        try {
            MvcResult output = mockMvc.perform(get("/city"))
                    .andExpect(status().isOk()).andReturn();
            log.info(output.getResponse().getContentAsString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void getCityById() {
        try {
            MvcResult output = mockMvc.perform(get("/city/1"))
                    .andExpect(status().isOk()).andReturn();
            log.info(output.getResponse().getContentAsString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}