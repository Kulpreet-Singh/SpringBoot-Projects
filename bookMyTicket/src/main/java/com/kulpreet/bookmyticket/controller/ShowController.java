package com.kulpreet.bookmyticket.controller;

import com.kulpreet.bookmyticket.dto.BookSeatsDto;
import com.kulpreet.bookmyticket.dto.CreateShowDto;
import com.kulpreet.bookmyticket.dto.ShowInfo;
import com.kulpreet.bookmyticket.model.Seat;
import com.kulpreet.bookmyticket.model.Show;
import com.kulpreet.bookmyticket.service.BookingService;
import com.kulpreet.bookmyticket.service.SeatService;
import com.kulpreet.bookmyticket.service.ShowService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/show")
public class ShowController {
    @Autowired
    private SeatService seatService;
    @Autowired
    private ShowService showService;
    @Autowired
    private BookingService bookingService;

    @PostMapping("/host")
    public ResponseEntity<String> hostShow(@Valid @RequestBody CreateShowDto show) {
        log.info("Hosting show: {}", show);
        Long showId = showService.hostShow(new Show(show));
        log.info("Show hosted successfully with id: {}", showId);
        return ResponseEntity.created(null).body("Show hosted successfully with id: " + showId);
    }

    @GetMapping
    public ResponseEntity<List<ShowInfo>> getShows(@RequestParam boolean running,
                                                   @RequestParam(required = false) Long cityId,
                                                   @RequestParam(required = false) Long theaterId,
                                                   @RequestParam(required = false) Long movieId) {
        log.info("Fetching shows for city with id: {} theater with id: {} movie with id: {}", cityId, theaterId, movieId);
        return ResponseEntity.ok(showService.findShowInfo(running, cityId, theaterId, movieId, null));
    }

    @GetMapping("/{showId}/seats")
    public ResponseEntity<List<Seat>> getSeats(@RequestParam boolean running, @PathVariable Long showId, @RequestParam Long userId) {
        log.info("Fetching seats for show with id: {}", showId);
        return ResponseEntity.ok(seatService.getSeats(running, showId, userId));
    }

    @PostMapping("/book")
    public ResponseEntity<String> bookSeats(@RequestBody BookSeatsDto req) {
        log.info("Booking seats: {}", req);
        Long bookingId = bookingService.bookSeats(req.getUserId(), req.getShowId(), req.getSeatIds());
        log.info("Booking created with bookingId: {}", bookingId);
        return ResponseEntity.created(null).body("Booking created successfully with bookingId: " + bookingId);
    }


}

