package com.study.java.Entity;

import jakarta.persistence.*;

@Entity
public class Booking {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private Integer seatBooked;


    @ManyToOne
    @JoinColumn(name="movie_id")
    private Movie movie;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getSeatBooked() {
        return seatBooked;
    }

    public void setSeatBooked(Integer seatBooked) {
        this.seatBooked = seatBooked;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }
}
