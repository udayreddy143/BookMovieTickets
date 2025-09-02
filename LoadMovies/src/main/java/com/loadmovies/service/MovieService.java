package com.loadmovies.service;

import com.loadmovies.entity.Movie;
import com.loadmovies.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    public Movie saveMovie(Movie movie) {
        return movieRepository.save(movie);

    }


    public List<Movie> getMoviesByLocation(String Location){
       return movieRepository.findByMovieLocation(Location);
    }

    public List<Movie> updateMovieNameByLocation(String location, String newMovieName,String newArtist) {
        List<Movie> movies = movieRepository.findByMovieLocation(location);

        for (Movie movie : movies) {
            movie.setMoviename(newMovieName);
            movie.setArtists(newArtist);
        }

        return movieRepository.saveAll(movies); // saves the updated list
    }

}
