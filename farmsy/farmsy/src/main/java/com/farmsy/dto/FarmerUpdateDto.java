package com.farmsy.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class FarmerUpdateDto {
    @Schema(description = "Full name of the farmer", example = "John Farmer Updated")
    @NotBlank
    @Size(min = 2, max = 100)
    private String name;

    @Schema(description = "Phone number (10-15 digits)", example = "9876543210")
    @NotBlank
    @Pattern(regexp = "^\\d{10,15}$")
    private String phone;

    @Schema(description = "Physical address", example = "123 Updated Farm Road")
    @NotBlank
    private String address;

    @Schema(description = "Geographic region", example = "North-West")
    @NotBlank
    private String region;

    @Schema(description = "New password (optional, min 6 chars)",
            example = "NewSecure123",
            minLength = 6)
    @Size(min = 6)
    private String password;
}