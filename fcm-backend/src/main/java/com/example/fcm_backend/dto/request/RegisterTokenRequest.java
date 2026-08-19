package com.example.fcm_backend.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegisterTokenRequest {

    @NotBlank(message = "Token is required")
    private String token;
}
