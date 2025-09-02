package com.jjs.Moviebook.Service;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jjs.Moviebook.Entity.User;
import com.jjs.Moviebook.Repository.MovieRepository;

@Service
public class MovieService {
	
	@Autowired
	private MovieRepository movierepository;
	
	public User signup(User user) {
        return movierepository.save(user);
    }
	
	public String login(String email, String password) {
	    Optional<User> user = movierepository.findByEmail(email);
	    if (user.isEmpty()) {
	        return "User not found!";
	    }
	    if (!user.get().getPassword().equals(password)) {
	        return "Invalid password!";
	    }
	    return "Login successful!";
	}

	
	
	public String changePassword(String email, String oldPassword, String newPassword) {
        Optional<User> user = movierepository.findByEmail(email);
        if(user.isPresent() && user.get().getPassword().equals(oldPassword)) {
            user.get().setPassword(newPassword);
            movierepository.save(user.get());
            return "Password updated successfully!";
        }
        return "Invalid email or old password!";
    }

	
	public User updateProfile(User updatedUser) {
        return movierepository.save(updatedUser);
    }

}
