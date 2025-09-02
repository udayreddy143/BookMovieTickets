package com.loadmovies.controller;

import com.loadmovies.entity.Movie;
import com.loadmovies.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies")

public class MovieController {

    @Autowired
    private MovieService movieService;


    @PostMapping("/save")
    public Movie saveData(@RequestBody Movie movie){

       return movieService.saveMovie(movie);
    }

    @GetMapping("/Location/{Location}")
    public List<Movie> getMovies(@PathVariable String Location){

       return movieService.getMoviesByLocation(Location);
    }

    @PutMapping("/update-name-by-location")
    public List<Movie> updateMovieNameByLocation(
            @RequestParam String location,
            @RequestParam String newName,
            @RequestParam String newArtist) {
        return movieService.updateMovieNameByLocation(location, newName,newArtist);
    }
}
