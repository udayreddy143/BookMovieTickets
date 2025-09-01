package com.study.java.service;

import com.study.java.Entity.Booking;
import com.study.java.Entity.Movie;
import com.study.java.dto.MovieDetailsDto;
import com.study.java.repository.BookingRepository;
import com.study.java.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private BookingRepository bookingRepository;

    public MovieDetailsDto  bookSeats(String movieName, String userName, int seats) {

        Movie movie= movieRepository.findByMovieName(movieName)
                                     .orElseThrow(()->new RuntimeException("movie not found"));
        List<Booking> bookings = bookingRepository.findByMovie(movie);
        int bookedSeats = 0;


        for (Booking booking : bookings) {
            bookedSeats += booking.getSeatsBooked();
        }

        // Step 3: Calculate available seats
        int availableSeats = movie.getTotalSeats() - bookedSeats;


        if (availableSeats < seats) {
            throw new RuntimeException("Not enough seats available");
        }

        // Step 5: Create a new booking
        Booking booking = new Booking();
        booking.setUserName(userName);
        booking.setSeatsBooked(seats);
        booking.setMovie(movie);
        bookingRepository.save(booking);

        // Step 6: Create and return the MovieDetailsDto
        MovieDetailsDto dto = new MovieDetailsDto();
        dto.setMovieName(movie.getMovieName());
        dto.setDate(movie.getDate());
        dto.setTime(movie.getTime());
        dto.setPrice(movie.getPrice());
        dto.setTotalSeats(movie.getTotalSeats());
        dto.setBookedSeats(bookedSeats + seats);  // Include the new booking's seats
        dto.setAvailableSeats(availableSeats - seats);  // Subtract the booked seats

        return dto;
    }
}
