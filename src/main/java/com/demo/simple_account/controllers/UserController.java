package com.demo.simple_account.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import com.demo.simple_account.dtos.RegisterRequestDto;
import com.demo.simple_account.entities.User;
import com.demo.simple_account.services.AuthService;
import com.demo.simple_account.services.UserService;

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
        userService.register(user);
        response.put("message", "User : " + user.getUsername() + " Successfully registered");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody User user) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", authService.verify(user));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    
}
