package com.demo.simple_account.validation.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.demo.simple_account.repositories.UserRepository;
import com.demo.simple_account.validation.annotations.UniqueUsername;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

@Component
public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String>{

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {
        return username != null && !userRepository.existsByUsername(username);
    }
    
}
