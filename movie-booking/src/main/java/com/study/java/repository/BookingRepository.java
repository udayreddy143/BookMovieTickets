package com.study.java.repository;

import com.study.java.Entity.Booking;
import com.study.java.Entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking,Long> {
    List<Booking> findByMovie(Movie movie);

}
