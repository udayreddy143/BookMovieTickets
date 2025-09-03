package com.jjs.Moviebook.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jjs.Moviebook.Entity.User;
import com.jjs.Moviebook.Service.MovieService;

@RestController
@RequestMapping("/user")
public class Moviecontroller {
	@Autowired
	private MovieService movieservice;
	@PostMapping("/signup")
    public User signup(@RequestBody User user) {
        return movieservice.signup(user);
    }
	
	@PostMapping("/login")
	public String login(@RequestBody Map<String, String> body) {
	    String email = body.get("email");
	    String password = body.get("password");
	    return movieservice.login(email, password);
	}

    // Change password
    @PutMapping("/change-password")
    public String changePassword(@RequestParam String email, 
                                 @RequestParam String oldPassword, 
                                 @RequestParam String newPassword) {
        return movieservice.changePassword(email, oldPassword, newPassword);
    }	
    @PutMapping("/update-profile")
    public User updateProfile(@RequestBody User user) {
        return movieservice.updateProfile(user);
    }


    @GetMapping("/fetchUserDetails")
    public List<User> fetchUserDetails(@RequestParam("userIds") List<Integer> userIds) {
        return movieservice.fetchUserDetails(userIds);
    }
}
