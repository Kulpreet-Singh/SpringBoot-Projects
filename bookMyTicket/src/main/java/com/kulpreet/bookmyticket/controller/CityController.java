package com.kulpreet.bookmyticket.controller;

import com.kulpreet.bookmyticket.dto.CreateCityDto;
import com.kulpreet.bookmyticket.model.City;
import com.kulpreet.bookmyticket.service.CityService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/city")
public class CityController {
    @Autowired
    private CityService cityService;


    @PostMapping
    public ResponseEntity<String> addCity(@Valid @RequestBody CreateCityDto city) {
        log.info("Adding city: {}", city);
        Long cityId = cityService.save(new City(city));
        log.info("City added successfully with id: {}", cityId);
        return ResponseEntity.created(null).body("City added successfully with id: " + cityId);
    }

    @GetMapping
    public ResponseEntity<List<City>> getAllCities() {
        log.info("Fetching all cities");
        return ResponseEntity.ok(cityService.findAll());
    }

    @GetMapping("/{cityId}")
    public ResponseEntity<City> getCityById(@PathVariable Long cityId) {
        log.info("Fetching city with id: {}", cityId);
        return ResponseEntity.ok(cityService.findById(cityId));
    }

}
