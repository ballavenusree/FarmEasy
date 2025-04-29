package com.farmsy.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FarmerResponseDto {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String address;
    private String region;

    // Getters and setters
}