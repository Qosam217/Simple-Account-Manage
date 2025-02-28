package com.demo.simple_account.dtos;

import com.demo.simple_account.validation.annotations.UniqueUsername;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequestDto {
    @Size(min=2, max=100, message="Username must be between 2 - 100 character")
    @NotBlank(message="Username must be filled")
    @UniqueUsername(message = "Username already exists")
    private String username;

    @Column(name = "password", length=100)
    @NotBlank(message="Password must be filled")
    @Size(min=8, max=100, message="Password must be between 8 - 100 character")
    @Pattern(
        regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[~!@#$%^&*;:'<>,./?]).*$",
        message="Password must contain at least one digit, one lowercase character, one uppercase character, and one special character"
    )
    private String password;

    @NotBlank(message="Email must be filled")
    @Email(message="Email should be valid")
    private String email;
    
}
