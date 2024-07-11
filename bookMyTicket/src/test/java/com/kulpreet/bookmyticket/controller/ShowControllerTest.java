package com.kulpreet.bookmyticket.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
class ShowControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testConcurrentBooking() throws ExecutionException, InterruptedException {
        Long showId = 14L;
        List<Long> seatIds1 = Arrays.asList(1L, 3L);
        List<Long> seatIds2 = Arrays.asList(2L, 3L);
        Long userId1 = 1L;
        Long userId2 = 4L;

        CompletableFuture<MvcResult> booking1 = CompletableFuture.supplyAsync(() -> {
            try {
                MvcResult output = mockMvc.perform(post("/show/book")
                                .content("{\n" +
                                        "    \"showId\": " + showId + ",\n" +
                                        "    \"seatIds\": " + seatIds1 + ",\n" +
                                        "    \"userId\": " + userId1 + "\n" +
                                        "}")
                                .contentType("application/json"))
                        .andReturn();
                log.info("HTTP Status: " + output.getResponse().getStatus() + " - " + output.getResponse().getContentAsString());
                return output;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        CompletableFuture<MvcResult> booking2 = CompletableFuture.supplyAsync(() -> {
            try {
                MvcResult output = mockMvc.perform(post("/show/book")
                                .param("seatIds", StringUtils.join(seatIds2, ","))
                                .param("userId", userId2.toString())
                                .content("{\n" +
                                        "    \"showId\": " + showId + ",\n" +
                                        "    \"seatIds\": " + seatIds2 + ",\n" +
                                        "    \"userId\": " + userId2 + "\n" +
                                        "}")
                                .contentType("application/json"))
                        .andReturn();
                log.info("HTTP Status: " + output.getResponse().getStatus() + " - " + output.getResponse().getContentAsString());
                return output;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        CompletableFuture.allOf(booking1, booking2).get();
    }

    @Test
    public void getShowsAll(){
        try {
            MvcResult output = mockMvc.perform(get("/show")
                    .param("running", "false"))
                    .andExpect(status().isOk()).andReturn();
            log.info(output.getResponse().getContentAsString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void getShowsRunning(){
        try {
            MvcResult output = mockMvc.perform(get("/show")
                            .param("running", "true"))
                    .andExpect(status().isOk()).andReturn();
            log.info(output.getResponse().getContentAsString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void getShowInfoByCityIdAndMovieId(){
        try {
            MvcResult output = mockMvc.perform(get("/show")
                    .param("running", "true")
                    .param("cityId", "1")
                    .param("movieId", "1"))
                    .andExpect(status().isOk()).andReturn();
            log.info(output.getResponse().getContentAsString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void getShowInfoByTheaterId(){
        try {
            MvcResult output = mockMvc.perform(get("/show")
                    .param("running", "true")
                    .param("theaterId", "1"))
                    .andExpect(status().isOk()).andReturn();
            log.info(output.getResponse().getContentAsString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void getShowInfoByTheaterIdAndMovieId(){
        try {
            MvcResult output = mockMvc.perform(get("/show")
                    .param("running", "true")
                    .param("theaterId", "1")
                    .param("movieId", "1"))
                    .andExpect(status().isOk()).andReturn();
            log.info(output.getResponse().getContentAsString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void getShowSeatsPast(){
        try {
            MvcResult output = mockMvc.perform(get("/show/{showId}/seats", 14)
                            .param("running", "false")
                            .param("userId", "1"))
                    .andExpect(status().isOk()).andReturn();
            log.info(output.getResponse().getContentAsString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void getShowSeatsRunning(){
        try {
            MvcResult output = mockMvc.perform(get("/show/{showId}/seats", 10)
                            .param("running", "true")
                            .param("userId", "1"))
                    .andExpect(status().isBadRequest()).andReturn();
            log.info(output.getResponse().getContentAsString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void hostShowDuplicateSlot(){
        try {
            MvcResult output = mockMvc.perform(post("/show/host")
                    .content("{\n" +
                            "    \"hallId\": 2,\n" +
                            "    \"movieId\": 1,\n" +
                            "    \"showTime\": \"2024-07-09 21:00:00\"\n" +
                            "}")
                    .contentType("application/json"))
                    .andExpect(status().isBadRequest()).andReturn();
            log.info(output.getResponse().getContentAsString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void hostShow(){
        try {
            MvcResult output = mockMvc.perform(post("/show/host")
                    .content("{\n" +
                            "    \"hallId\": 2,\n" +
                            "    \"movieId\": 1,\n" +
                            "    \"showTime\": \"2024-07-20 13:30:00\"\n" +
                            "}")
                    .contentType("application/json"))
                    .andExpect(status().isCreated()).andReturn();
            log.info(output.getResponse().getContentAsString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void bookSeatsShowAlreadyStarted(){
        try {
            MvcResult output = mockMvc.perform(post("/show/book")
                    .content("{\n" +
                            "    \"showId\": 10,\n" +
                            "    \"seatIds\": [43, 44],\n" +
                            "    \"userId\": 1\n" +
                            "}")
                    .contentType("application/json"))
                    .andExpect(status().isBadRequest()).andReturn();
            log.info(output.getResponse().getContentAsString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void bookSeats(){
        try {
            MvcResult output = mockMvc.perform(post("/show/book")
                    .content("{\n" +
                            "    \"showId\": 14,\n" +
                            "    \"seatIds\": [44, 45],\n" +
                            "    \"userId\": 1\n" +
                            "}")
                    .contentType("application/json"))
                    .andExpect(status().isCreated()).andReturn();
            log.info(output.getResponse().getContentAsString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void bookSeatsAlreadyBooked(){
        try {
            MvcResult output = mockMvc.perform(post("/show/book")
                            .content("{\n" +
                                    "    \"showId\": 14,\n" +
                                    "    \"seatIds\": [44, 45],\n" +
                                    "    \"userId\": 1\n" +
                                    "}")
                            .contentType("application/json"))
                    .andExpect(status().isBadRequest()).andReturn();
            log.info(output.getResponse().getContentAsString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}