package com.demo.simple_account.controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.simple_account.entities.User;
import com.demo.simple_account.services.AuthService;
import com.demo.simple_account.services.JWTService;
import com.demo.simple_account.services.UserService;
import com.demo.simple_account.utils.CookieUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
@Validated
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<Object> login(@Valid @RequestBody User user, HttpServletResponse servlet) {
        Map<String, Object> response = new HashMap<>();
        Map<String, String> token = authService.verify(user);

        if(!token.isEmpty()){
            Cookie refreshCookie = CookieUtil.creatCookie("refresh", token.get("refresh"), 60*3600, "/");
            servlet.addCookie(refreshCookie);

            Cookie accessCookie = CookieUtil.creatCookie("access", token.get("access"), 3600, "/"); 
            new Cookie("access", token.get("access"));
            servlet.addCookie(accessCookie);

            response.put("message", "Login Successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);

        }else{
            response.put("message", "Invalid Credentials");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
    }


    @PostMapping("/refresh")
    public ResponseEntity<Object> refreshingToken(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        Map<String, Object> data = new HashMap<>();
        String refreshHeader = request.getHeader("Authorization");
        String refreshToken = null;
        String username = null;

        if(refreshHeader != null && refreshHeader.startsWith("Bearer")){
            refreshToken = refreshHeader.substring(7);
            username = jwtService.extractUsername(refreshToken);
        }

        UserDetails userDetails = userService.loadUserByUsername(username);
        if (jwtService.validateToken(refreshToken, userDetails)) {
            Cookie accessCookie = CookieUtil.creatCookie("access", jwtService.generateAccessToken(username), 3600, "/"); 
            response.addCookie(accessCookie);

            data.put("message", "Refresh Success");
            return new ResponseEntity<>(data, HttpStatus.OK);
        }else{
            data.put("message", "Refresh Token Not Valid");
            return new ResponseEntity<>(data, HttpStatus.OK);
        }
    }
}
