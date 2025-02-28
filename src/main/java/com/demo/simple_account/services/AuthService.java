package com.demo.simple_account.services;

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

        public String verify(User user){
        Authentication auth = authManager
                .authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        if(auth.isAuthenticated()){
            return jwtService.generateToken(user.getUsername());
        }
        return "Failed to verify user";
    }
    
}
