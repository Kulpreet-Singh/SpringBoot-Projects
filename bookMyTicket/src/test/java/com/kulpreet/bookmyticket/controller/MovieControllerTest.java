package com.kulpreet.bookmyticket.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
class MovieControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void register() {
        try {
            MvcResult output = mockMvc.perform(post("/movie")
                            .content("{\n" +
                                    "    \"title\": \"The Matrix\",\n" +
                                    "    \"description\": \"A computer hacker learns from mysterious rebels about the true nature of his reality and his role in the war against its controllers.\",\n" +
                                    "    \"duration\": 136,\n" +
                                    "    \"language\": \"Tamil\",\n" +
                                    "    \"releaseDate\": \"1999-03-31\",\n" +
                                    "    \"genre\": \"Action\"\n" +
                                    "}")
                            .contentType("application/json"))
                    .andExpect(status().isCreated()).andReturn();
            log.info(output.getResponse().getContentAsString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void registerDuplicateTitleAndLanguage() {
        try {
            MvcResult output = mockMvc.perform(post("/movie")
                    .content("{\n" +
                            "    \"title\": \"The Matrix\",\n" +
                            "    \"description\": \"A computer hacker learns from mysterious rebels about the true nature of his reality and his role in the war against its controllers.\",\n" +
                            "    \"duration\": 136,\n" +
                            "    \"language\": \"English\",\n" +
                            "    \"releaseDate\": \"1999-03-31\",\n" +
                            "    \"genre\": \"Action\"\n" +
                            "}")
                    .contentType("application/json"))
                    .andExpect(status().isBadRequest()).andReturn();
            log.info(output.getResponse().getContentAsString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void getAllMovies() {
        try {
            MvcResult output = mockMvc.perform(get("/movie"))
                    .andExpect(status().isOk()).andReturn();
            log.info(output.getResponse().getContentAsString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void getMovieById() {
        try {
            MvcResult output = mockMvc.perform(get("/movie/1"))
                    .andExpect(status().isOk()).andReturn();
            log.info(output.getResponse().getContentAsString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void update() {
        try {
            MvcResult output = mockMvc.perform(put("/movie")
                    .content("{\n" +
                            "    \"id\": 1,\n" +
                            "    \"title\": \"The Matrix\",\n" +
                            "    \"description\": \"A computer hacker learns from mysterious rebels about the true nature of his reality and his role in the war against its controllers.\",\n" +
                            "    \"duration\": 136,\n" +
                            "    \"language\": \"English\",\n" +
                            "    \"releaseDate\": \"1999-03-31\",\n" +
                            "    \"genre\": \"Action\"\n" +
                            "}")
                    .contentType("application/json"))
                    .andExpect(status().isOk()).andReturn();
            log.info(output.getResponse().getContentAsString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void updateDuplicateTitleAndLanguage() {
        try {
            MvcResult output = mockMvc.perform(put("/movie")
                            .content("{\n" +
                                    "    \"id\": 4,\n" +
                                    "    \"title\": \"The Matrix\",\n" +
                                    "    \"description\": \"A computer hacker learns from mysterious rebels about the true nature of his reality and his role in the war against its controllers.\",\n" +
                                    "    \"duration\": 136,\n" +
                                    "    \"language\": \"English\",\n" +
                                    "    \"releaseDate\": \"1999-03-31\",\n" +
                                    "    \"genre\": \"Action\"\n" +
                                    "}")
                            .contentType("application/json"))
                    .andExpect(status().isBadRequest()).andReturn();
            log.info(output.getResponse().getContentAsString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}