package com.loadmovies.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDate;
import java.util.Date;

@Entity
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String Moviename;
    private String movieLocation;
    private LocalDate ReleaseDate;
    private String Artists;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMoviename() {
        return Moviename;
    }

    public void setMoviename(String moviename) {
        Moviename = moviename;
    }

    public String getMovieLocation() {
        return movieLocation;
    }

    public void setMovieLocation(String movieLocation) {
        this.movieLocation = movieLocation;
    }

    public LocalDate getReleaseDate() {
        return ReleaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        ReleaseDate = releaseDate;
    }

    public String getArtists() {
        return Artists;
    }

    public void setArtists(String artists) {
        Artists = artists;
    }
}
