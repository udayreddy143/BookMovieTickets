package com.study.java.repository;

import com.study.java.Entity.Booking;
import com.study.java.Entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface MovieRepository extends JpaRepository<Movie,Long> {


    // Custom query to find a movie by its name
    Optional<Movie> findByMovieName(String movieName);
}
