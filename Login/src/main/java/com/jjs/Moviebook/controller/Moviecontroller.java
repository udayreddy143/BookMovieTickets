package com.jjs.Moviebook.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.jjs.Moviebook.util.JsonWebTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
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

    @Autowired
    private JsonWebTokenUtil jsonWebTokenUtil;
	@PostMapping("/signup")
    public User signup(@RequestBody User user) {
        String token = jsonWebTokenUtil.generateToken(user.getEmail());
        System.out.println(token);
        user.setToken(token);
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
    public List<User> fetchUserDetails(@RequestParam("userIds") List<Integer> userIds,
                                       @RequestHeader("Authorization") String token) {

        if(jsonWebTokenUtil.validateToken(token)) {
            return movieservice.fetchUserDetails(userIds);
        }else{
            throw new RuntimeException("Invalid token ");
        }
    }
//	@DeleteMapping("/delete/{userId}")
//    public String deleteUser(@PathVariable Long userId) {
//        // here you delete user from DB
//        return "User with ID " + userId + " deleted successfully!";
//    }
//
//    @PutMapping("/updateProfile")
//    public User updateProfile(@RequestBody User user) {
//        // here you update user in DB
//        user.setName(user.getName() + " (updated)");
//        return user;
//    }
}
