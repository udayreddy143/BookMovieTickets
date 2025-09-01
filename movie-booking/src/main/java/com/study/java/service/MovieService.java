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
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private BookingRepository bookingRepository;

    public Movie addMovie(Movie movie) {

        movieRepository.save(movie);
        return movie;
    }


    public MovieDetailsDto getMovieDetails(String movieName) {
        // Find movie by movieName
        Movie movie = movieRepository.findByMovieName(movieName)
                .orElseThrow(() -> new RuntimeException("Movie not found"));

        // Get all bookings for this movie
        List<Booking> bookings = bookingRepository.findByMovie(movie);

        // Calculate booked seats (using loop instead of streams)
        int bookedSeats = 0;
        for (Booking booking : bookings) {
            bookedSeats += booking.getSeatsBooked();
        }

        int availableSeats = movie.getTotalSeats() - bookedSeats;

        // Return DTO
        MovieDetailsDto dto = new MovieDetailsDto();
        dto.setMovieName(movie.getMovieName());
        dto.setDate(movie.getDate());
        dto.setTime(movie.getTime());
        dto.setPrice(movie.getPrice());
        dto.setTotalSeats(movie.getTotalSeats());
        dto.setBookedSeats(bookedSeats);
        dto.setAvailableSeats(availableSeats);

        return dto;
    }

}
