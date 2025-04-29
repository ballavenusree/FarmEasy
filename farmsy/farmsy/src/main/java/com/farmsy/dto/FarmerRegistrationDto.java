package com.farmsy.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "FarmerRegistrationDTO", description = "Farmer registration payload")
public class FarmerRegistrationDto {
    private String name;
    private String email;
    private String password;
    private String phone;
    private String address;
    private String region;

    // Getters and setters
    // Add validation annotations as needed
}