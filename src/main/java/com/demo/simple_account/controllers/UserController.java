package com.demo.simple_account.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import com.demo.simple_account.dtos.requests.RegisterRequestDto;
import com.demo.simple_account.entities.User;
import com.demo.simple_account.services.AuthService;
import com.demo.simple_account.services.UserService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/api")
@Validated
public class UserController {
    
    @Autowired
    private UserService userService;

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<Object> register(@Valid @RequestBody RegisterRequestDto request) {
        Map<String, Object> response = new HashMap<>();
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setEmail(request.getEmail());
        userService.register(user);
        response.put("message", "User : " + user.getUsername() + ", Successfully registered");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@Valid @RequestBody User user, HttpServletResponse servlet) {
        Map<String, Object> response = new HashMap<>();
        String token = authService.verify(user);

        if(token != null){
            Cookie cookie = new Cookie("auth", token);
            cookie.setHttpOnly(true);
            cookie.setSecure(false);    // ensure cookie is only sent over HTTPS
            cookie.setPath("/");        // specify the endpoint for cookie to sent
            cookie.setMaxAge(60 * 3600);
            servlet.addCookie(cookie);

            response.put("message", "Login Successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);

        }else{
            response.put("message", "Invalid Credentials");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
    }
}
