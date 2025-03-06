package com.demo.simple_account.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.demo.simple_account.entities.User;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JWTService jwtService;

        public Map<String, String> verify(User user){
        Authentication auth = authManager
                .authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        Map<String, String> token = new HashMap<>();
        if(auth.isAuthenticated()){
            token.put("refresh", jwtService.generateRefreshToken(user.getUsername()));
            token.put("access", jwtService.generateAccessToken(user.getUsername()));
            return token;
        }
        return token;
    }
    
}
