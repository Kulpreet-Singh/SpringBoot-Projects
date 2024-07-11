package com.kulpreet.bookmyticket.controller;

import com.kulpreet.bookmyticket.dto.CreateHallDto;
import com.kulpreet.bookmyticket.dto.CreateTheaterDto;
import com.kulpreet.bookmyticket.model.Hall;
import com.kulpreet.bookmyticket.model.Theater;
import com.kulpreet.bookmyticket.service.HallService;
import com.kulpreet.bookmyticket.service.TheaterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/theater")
public class TheaterController {
    @Autowired
    private TheaterService theaterService;
    @Autowired
    private HallService hallService;

    @PostMapping("/onboard")
    public ResponseEntity<String> onboardTheater(@Valid @RequestBody CreateTheaterDto theater) {
        log.info("Onboarding theater: {}", theater);
        Long theaterId = theaterService.onboardTheater(new Theater(theater));
        log.info("Theater onboarded successfully with id: {}", theaterId);
        return ResponseEntity.created(null).body("Theater onboarded successfully with id: " + theaterId);
    }

    @GetMapping
    public ResponseEntity<List<Theater>> getTheaters(@RequestParam(required = false) Long cityId) {
        log.info("Fetching theaters for city with id: {}", cityId);
        if(cityId == null)
            return ResponseEntity.ok(theaterService.findAll());
        return ResponseEntity.ok(theaterService.findByCityId(cityId));
    }

    @PostMapping("/addHall")
    public ResponseEntity<String> addHall(@Valid @RequestBody CreateHallDto hall) {
        log.info("Adding hall: {}", hall.getName());
        Long hallId = hallService.addHall(new Hall(hall));
        log.info("Hall added successfully with id: {}", hallId);
        return ResponseEntity.created(null).body("Hall added successfully with id: " + hallId);
    }

    @GetMapping("/{theaterId}/halls")
    public ResponseEntity<List<Hall>> getHalls(@PathVariable Long theaterId) {
        log.info("Fetching halls for theater with id: {}", theaterId);
        return ResponseEntity.ok(hallService.getHalls(theaterId));
    }
}

