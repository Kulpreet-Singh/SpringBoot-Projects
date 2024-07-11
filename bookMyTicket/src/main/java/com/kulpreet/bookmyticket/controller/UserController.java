package com.kulpreet.bookmyticket.controller;

import com.kulpreet.bookmyticket.dto.BookingInfo;
import com.kulpreet.bookmyticket.dto.CreateUserDto;
import com.kulpreet.bookmyticket.dto.EditUserDto;
import com.kulpreet.bookmyticket.model.User;
import com.kulpreet.bookmyticket.service.BookingService;
import com.kulpreet.bookmyticket.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private BookingService bookingService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody CreateUserDto user) {
        log.info("Registering user: {}", user);
        Long userId = userService.register(new User(user));
        log.info("User registered with id: {}", userId);
        return ResponseEntity.created(null).body("User registered with id: " + userId);
    }

    @PutMapping("/edit")
    public ResponseEntity<String> edit(@Valid @RequestBody EditUserDto user) {
        log.info("Updating user: {}", user);
        userService.update(new User(user));
        log.info("User updated successfully");
        return ResponseEntity.ok("User updated successfully");
    }

    @GetMapping
    public ResponseEntity<User> getUser(@RequestParam Long userId) {
        log.info("Fetching user with id: {}", userId);
        return ResponseEntity.ok(userService.findById(userId));
    }

    @GetMapping("/bookings")
    public ResponseEntity<List<BookingInfo>> getUserBookings(@RequestParam boolean upcoming, @RequestParam Long userId) {
        log.info("Fetching bookings for user with id: {} and upcoming: {}", userId, upcoming);
        List<BookingInfo> bookings = bookingService.getBookings(upcoming, userId);
        return ResponseEntity.ok(bookings);
    }
}
