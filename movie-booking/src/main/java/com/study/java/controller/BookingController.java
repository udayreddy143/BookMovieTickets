package com.study.java.controller;

import com.study.java.Entity.Booking;
import com.study.java.dto.MovieDetailsDto;
import com.study.java.dto.User;
import com.study.java.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping
    public MovieDetailsDto bookSeats(
            @RequestParam String movieName,
            @RequestParam String userId,
            @RequestParam int seats) {
        return bookingService.bookSeats(movieName, userId, seats);
    }


@GetMapping
public List<User> getAllBookingDetails( ){
    return bookingService.getAllBookilngDetails();
}
//
//    @GetMapping
//    public User getBYBookId(@RequestParam Long bookiungId ){
//        return bookingService.getAllBookilngDetails();
//    }

    @PostMapping("/saveUserDetails")
    public User saveUserDetails(@RequestBody  User user){
        return bookingService.saveUserDetails(user);

    }
    @DeleteMapping("/delete/{userId}")
    public String deleteUser(@PathVariable Long userId) {
        // here you delete user from DB
        return "User with ID " + userId + " deleted successfully!";
    }

    @PutMapping("/updateProfile")
    public User updateProfile(@RequestBody User user) {
        // here you update user in DB
       // user.setName(user.getName() + " (updated)");
        return user;
    }
}
