package com.study.java.controller;

import com.study.java.Entity.Movie;
import com.study.java.dto.MovieDetailsDto;
import com.study.java.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;

    // Add a movie
    @PostMapping("/add")
    public Movie addMovie(@RequestBody Movie movie) {
        return movieService.addMovie(movie);
    }

    // Endpoint to get movie details by movie name
    @GetMapping("/details")
    public MovieDetailsDto getMovieDetails(@RequestParam String movieName) {
        return movieService.getMovieDetails(movieName);
    }
}
