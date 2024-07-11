package com.kulpreet.bookmyticket.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetBooking(){
        try {
            MvcResult output = mockMvc.perform(get("/user/bookings")
                    .param("upcoming", "true")
                    .param("userId", "1"))
                    .andExpect(status().isOk()).andReturn();
            log.info(output.getResponse().getContentAsString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testGetUser(){
        try {
            MvcResult output = mockMvc.perform(get("/user")
                    .param("userId", "1"))
                    .andExpect(status().isOk()).andReturn();
            log.info(output.getResponse().getContentAsString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testEditUser() {
        try {
            MvcResult output = mockMvc.perform(put("/user/edit")
                    .content("{\n" +
                            "    \"id\": 1,\n" +
                            "    \"username\": \"admin1\",\n" +
                            "    \"mobile\": \"9213423456\",\n" +
                            "    \"email\": \"admin1@bookmyticket.com\",\n" +
                            "    \"gender\": \"MALE\",\n" +
                            "    \"age\": 22\n" +
                            "}")
                    .contentType("application/json"))
                    .andExpect(status().isOk()).andReturn();
            log.info(output.getResponse().getContentAsString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testEditUserDuplicateDetails() {
        try {
            MvcResult output = mockMvc.perform(put("/user/edit")
                            .content("{\n" +
                                    "    \"id\": 1,\n" +
                                    "    \"username\": \"admin1\",\n" +
                                    "    \"mobile\": \"9213423457\",\n" +
                                    "    \"email\": \"admin1@bookmyticket.com\",\n" +
                                    "    \"gender\": \"MALE\",\n" +
                                    "    \"age\": 22\n" +
                                    "}")
                            .contentType("application/json"))
                    .andExpect(status().isBadRequest()).andReturn();
            log.info(output.getResponse().getContentAsString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testRegisterUserDuplicateDetails() {
        try {
            MvcResult output = mockMvc.perform(post("/user/register")
                            .content("{\n" +
                                    "    \"username\": \"admin4\",\n" +
                                    "    \"mobile\": \"9213423459\",\n" +
                                    "    \"email\": \"admin4@bookmyticket.com\",\n" +
                                    "    \"gender\": \"MALE\",\n" +
                                    "    \"age\": 21\n" +
                                    "}")
                            .contentType("application/json"))
                    .andExpect(status().isBadRequest()).andReturn();
            log.info(output.getResponse().getContentAsString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testRegisterUser() {
        try {
            MvcResult output = mockMvc.perform(post("/user/register")
                            .content("{\n" +
                                    "    \"username\": \"admin6\",\n" +
                                    "    \"mobile\": \"9213423461\",\n" +
                                    "    \"email\": \"admin6@bookmyticket.com\",\n" +
                                    "    \"gender\": \"MALE\",\n" +
                                    "    \"age\": 23\n" +
                                    "}")
                            .contentType("application/json"))
                    .andExpect(status().isCreated()).andReturn();
            log.info(output.getResponse().getContentAsString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}