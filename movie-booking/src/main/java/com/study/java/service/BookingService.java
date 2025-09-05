package com.study.java.service;

import com.study.java.Entity.Booking;
import com.study.java.Entity.Movie;
import com.study.java.dto.MovieDetailsDto;
import com.study.java.dto.User;
import com.study.java.feignclient.RegistrationFeignClient;
import com.study.java.repository.BookingRepository;
import com.study.java.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

//@Service
public class BookingService {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private RegistrationFeignClient registrationFeignClient;

    private Map<String,List<User>> cache = new HashMap<>();

    @Autowired
    RedisTemplate<String, List<User>> redisTemplate;

    public MovieDetailsDto  bookSeats(String movieName, String userId, int seats) {

        Movie movie= movieRepository.findByMovieName(movieName)
                                     .orElseThrow(()->new RuntimeException("movie not found"));
        List<Booking> bookings = bookingRepository.findByMovie(movie);
        int bookedSeats = 0;


        for (Booking booking : bookings) {
            bookedSeats += booking.getSeatBooked();
        }

        // Step 3: Calculate available seats
        int availableSeats = movie.getTotalSeats() - bookedSeats;


        if (availableSeats < seats) {
            throw new RuntimeException("Not enough seats available");
        }

        // Step 5: Create a new booking
        Booking booking = new Booking();
        booking.setUserId(Long.valueOf(userId));
        booking.setSeatBooked(seats);
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


    public List<User> getAllBookilngDetails(){

       List<Booking> bookings = bookingRepository.findAll();

       // iterate all booking people  and collect all userIds and pass users id to the login service

        List<Long> userList = new ArrayList<>();
        for(Booking booking: bookings) {
            userList.add(booking.getUserId());
        }

        StringBuilder builder = new StringBuilder();
        int count =0;
        for(Long id: userList) {

            builder.append(id);
            if (count < userList.size() - 1) {  // size=4
                builder.append(",");
            }

            count++;
        }

        //1,2,3,4
        // retrive data from cache  if exist return from there itself
        if(Objects.nonNull(redisTemplate.opsForHash().get("userDetails",builder.toString()))) {
           // List<User> userListcache =cache.get(builder.toString());
            return (List<User>) redisTemplate.opsForHash().get("userDetails",builder.toString());
           // return userListcache;
        }else{
            List<User> userlist = registrationFeignClient.fetchuserDetails(userList);
            // store daytay into the cache
           // cache.put(builder.toString(),userlist);
            redisTemplate.opsForHash().put("userDetails",builder.toString(),userlist);
            redisTemplate.expire("userDetails",1, TimeUnit.MINUTES);
            return userlist;
        }


        // if i will middle component called cache

        //inmemeory == once we restrat the server(java aplication) then cache data will vanish
        //DFistributed cache = data will store seperate service  here once we restart the java app also
        //cache data will not vanish









        // call the login service and pass all uyserids using rest templare

        //url: http://localhost:9091/user/fetchUserDetails?userIds=userList
        //GET
        //response tuyp[e is List<User>
      //  RestTemplate template = new RestTemplate();


//        StringBuilder builder = new StringBuilder();
//        int count =0;
//        for(Long id: userList) {
//
//            builder.append(id);
//            if(count<userList.size()-1) {  // size=4
//               builder .append(",");
//            }
//
//                count++;
//        }  //1
     //   String url = "http://localhost:9091/user/fetchUserDetails?userIds="+builder.toString();


//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Accept","application/json");
//        HttpEntity<String> entity = new HttpEntity<>(headers);
//        ResponseEntity<List> response = template.exchange(url, HttpMethod.GET,entity,List.class);

        // call feign service
      //  return userlist;
    }

    // i will userdetails and call the singnup api to register the user

    public User saveUserDetails(User user) {

        //writer restemplate to call registration service to push data from this service
        ////url: http://localhost:9091/user/signup
        // Post
        // userData
        // expecting userINFO
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Accept","application/json");
//        HttpEntity<User> entity = new HttpEntity<>(user,headers);
//        String url="http://localhost:9091/user/signup";
//        RestTemplate template =new RestTemplate();
//        ResponseEntity<User> userInfo = template.exchange(url,HttpMethod.POST,entity,User.class);


        //call feign service

        return registrationFeignClient.saveUserDetails(user);
        //  return userInfo.getBody();
        // Delete user info
    }
    public String deleteUser(Long userId) {
        return registrationFeignClient.deleteuserDetails(userId);
    }

    // Update user profile
    public User updateUserProfile(User user) {
        return registrationFeignClient.updateUserDetails(user);
    }
    }

