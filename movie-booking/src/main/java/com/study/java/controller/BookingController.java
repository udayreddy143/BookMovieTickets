package com.study.java.controller;

import com.study.java.Entity.Booking;
import com.study.java.dto.MovieDetailsDto;
import com.study.java.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping
    public MovieDetailsDto bookSeats(
            @RequestParam String movieName,
            @RequestParam String userName,
            @RequestParam int seats) {
        return bookingService.bookSeats(movieName, userName, seats);
    }
}
