package com.demo.simple_account.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RefreshTokenRequestDto {

    @NotBlank(message = "Token not available")
    private String refreshToken;
    
}
