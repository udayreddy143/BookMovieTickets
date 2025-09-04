package com.study.java.feignclient;

import com.study.java.dto.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "registrationservice",url = "http://localhost:9091/user")
@Component
public interface RegistrationFeignClient {

    //get


    @GetMapping(value = "/fetchUserDetails")
    List<User> fetchuserDetails(@RequestParam("userIds") List<Long> userids);

   // String url="http://localhost:9091/user/signup";
//        RestTemplate template =new RestTemplate();
//        ResponseEntity<User> userInfo = template.exchange(url,HttpMethod.POST,entity,User.class);
    @PostMapping("/signup")
    User saveUserDetails(@RequestBody User user);

    @DeleteMapping("/delete")
    String  deleteuserDetails(@RequestParam Long userId);

    @PutMapping("/update-profile")
    User updateUserDetails(@RequestBody User user);
}
