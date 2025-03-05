package com.demo.simple_account.validation.validators;

import com.demo.simple_account.dtos.requests.LoginRequestDto;
import com.demo.simple_account.validation.annotations.UsernameOrEmail;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UsernameOrEmailValidator implements ConstraintValidator<UsernameOrEmail, LoginRequestDto>{

    @Override
    public boolean isValid(LoginRequestDto dto, ConstraintValidatorContext context) {
        return (dto.getUsername() != null && !dto.getUsername().isEmpty()) || (dto.getEmail() != null && !dto.getEmail().isEmpty());
    }
    
    
}
