package com.demo.simple_account.validation.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.demo.simple_account.validation.validators.UsernameOrEmailValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Constraint(validatedBy = UsernameOrEmailValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface UsernameOrEmail {
    String message() default "Either Username or Email must be provided";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default{};
    
}
