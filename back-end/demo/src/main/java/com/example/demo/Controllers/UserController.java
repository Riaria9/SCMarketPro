package com.example.demo.Controllers;


import java.util.Collections;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Services.UserService;
import com.example.demo.Tables.User;

@RestController
@RequestMapping("/users")
public class UserController 
{
    private final UserService userService;
    User user = null;
    public UserController(UserService userService)
    {
        this.userService = userService;
    }

    @GetMapping("/get-all")
    public Iterable<User> findAll()
    {
        return userService.findAll();
    }

    @GetMapping("/get-user")
    public User findById(@RequestParam Long userId)
    {
        return userService.findById(userId);
    }

    @GetMapping("/get-user-by-email")
    public User findByEmail(@RequestParam String email)
    {
      return userService.findByEmail(email);
    }
    
    // We can send specific response codes like this, not found is 404 and ok is 200
    // when we request this endpoint in JS, we can do response.status to easily know what we got back
    @GetMapping("/get-user-by-username")
    public ResponseEntity<User> findByUsername(@RequestParam String username)
    {
        User user = userService.findByUsername(username);
        if (user == null)
            return ResponseEntity.notFound().build();
        else
            return ResponseEntity.ok(user);
    }

/**
 * 
 * @param registrationDto : it is an json object. Fetch in this way:
 *                          body: JSON.stringify({ username: username, email: email, password: password })
 * @return                  the response is a json object {message:"xxx"} xxx could be success message or fail message
 */
    @PostMapping("/register")
    public ResponseEntity<?> registerUserAccount(@RequestBody UserRegistrationDto registrationDto) {
        user = registrationDto.toUser();

        if (userService.existsByUsername(registrationDto.getUsername())) {
            return ResponseEntity.ok(Collections.singletonMap("message", "username already in used"));
        }

        String otp = userService.generateOTP();
        userService.sendSimpleMessage(user.getEmail(), "Your OTP", "Your OTP for registration is: " + otp);
        userService.saveOtp(user.getEmail(), otp);

        return ResponseEntity.ok(Collections.singletonMap("message", "Registration initiated. Please check your email for OTP."));
    }

    /**
     * 
     * @param {String} email 
     * @param {String} otp 
     * @return {Json Object} {userId : id}. If the user id is -1, validation fail
     */
    @PostMapping("/validateOtp")
    public ResponseEntity<?> validateOtp(@RequestParam("email") String email, @RequestParam("otp") String otp) {
        if (userService.validateOtp(email, otp)) {
            userService.registUser(user);
            return ResponseEntity.ok(Collections.singletonMap("userId", user.getId()));
            
        } else {
            return ResponseEntity.ok(Collections.singletonMap("userId", -1));
        }
    }

    
    /**
     * 
     * @param {string}username
     * @param {string} password
     * @return {long} user id if logged in successfully
     * @return {long} null
     */
    @PostMapping("/signin")
    public Long signIn(@RequestParam("username") String username, @RequestParam("password") String password) { 
        user = userService.findByUsername(username);

        if (user != null && user.getPassword().equals(password))
        {   
            System.out.println("found user");
            return user.getId(); 
        }
        else
        {
            return null;
        }
    }
}