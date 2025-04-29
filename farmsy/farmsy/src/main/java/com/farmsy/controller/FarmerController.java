package com.farmsy.controller;

import com.farmsy.dto.FarmerRegistrationDto;
import com.farmsy.dto.FarmerResponseDto;
import com.farmsy.model.Farmer;
import com.farmsy.service.FarmerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Tag(name = "Farmer Management", description = "Operations related to farmers")
@RestController
@RequestMapping("/api/farmers")
public class FarmerController {

    private final FarmerService farmerService;

    @Autowired
    public FarmerController(FarmerService farmerService) {
        this.farmerService = farmerService;
    }

    @Operation(summary = "Register a new farmer")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Farmer created successfully",
                    content = @Content(schema = @Schema(implementation = Farmer.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "409", description = "Email already exists")
    })
    @PostMapping
    public ResponseEntity<FarmerResponseDto> registerFarmer(
            @Valid @RequestBody FarmerRegistrationDto farmerDto) {

        // Convert DTO to Entity
        Farmer farmer = new Farmer();
        farmer.setName(farmerDto.getName());
        farmer.setEmail(farmerDto.getEmail());
        farmer.setPassword(farmerDto.getPassword());
        farmer.setPhone(farmerDto.getPhone());
        farmer.setAddress(farmerDto.getAddress());
        farmer.setRegion(farmerDto.getRegion());

        Farmer savedFarmer = farmerService.registerFarmer(farmer);

        // Convert Entity back to Response DTO
        FarmerResponseDto responseDto = new FarmerResponseDto();
        responseDto.setId(savedFarmer.getId());
        responseDto.setName(savedFarmer.getName());
        responseDto.setEmail(savedFarmer.getEmail());
        responseDto.setPhone(savedFarmer.getPhone());
        responseDto.setAddress(savedFarmer.getAddress());
        responseDto.setRegion(savedFarmer.getRegion());

        return ResponseEntity.created(URI.create("/api/farmers/" + savedFarmer.getId()))
                .body(responseDto);
    }
    @Operation(summary = "Get all farmers")
    @ApiResponse(
            responseCode = "200",
            description = "List of all farmers",
            content = @Content(
                    schema = @Schema(implementation = Farmer[].class)
            )
    )
    @GetMapping
    public ResponseEntity<List<FarmerResponseDto>> getAllFarmers() {
        List<FarmerResponseDto> farmers = farmerService.getAllFarmers();
        return ResponseEntity.ok(farmers);
    }

    @Operation(summary = "Get farmer by ID")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Found the farmer",
                    content = @Content(schema = @Schema(implementation = Farmer.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Farmer not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<Farmer> getFarmerById(
            @Parameter(description = "ID of the farmer to retrieve", example = "1")
            @PathVariable Long id) {
        Optional<Farmer> farmer = farmerService.getFarmerById(id);
        return farmer.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Update farmer details")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Farmer updated successfully",
                    content = @Content(schema = @Schema(implementation = Farmer.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Farmer not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @PutMapping("/{id}")
    public ResponseEntity<Farmer> updateFarmer(
            @Parameter(description = "ID of the farmer to update", example = "1")
            @PathVariable Long id,
            @Valid @RequestBody Farmer farmerDetails) {
        Farmer updatedFarmer = farmerService.updateFarmer(id, farmerDetails);
        return ResponseEntity.ok(updatedFarmer);
    }

    @Operation(summary = "Delete a farmer")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Farmer deleted successfully"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Farmer not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFarmer(
            @Parameter(description = "ID of the farmer to delete", example = "1")
            @PathVariable Long id) {
        farmerService.deleteFarmer(id);
        return ResponseEntity.noContent().build();
    }

    // DTO for request/response documentation
    @Schema(name = "FarmerRequest", description = "Farmer registration payload")
    public static class FarmerRequest {
        @Schema(example = "John Farmer", required = true)
        public String name;

        @Schema(example = "john@example.com", required = true)
        public String email;

        @Schema(example = "Secure@123", minLength = 6, required = true)
        public String password;

        @Schema(example = "9876543210", pattern = "^\\d{10,15}$", required = true)
        public String phone;

        @Schema(example = "123 Farm Road", required = true)
        public String address;

        @Schema(example = "North", required = true)
        public String region;
    }

    // Error response structure
    @Schema(name = "ErrorResponse", description = "Error response format")
    public static class ErrorResponse {
        @Schema(example = "400")
        public int status;

        @Schema(example = "Bad Request")
        public String error;

        @Schema(example = "Validation failed for email")
        public String message;

        public ErrorResponse(int status, String error, String message) {
            this.status = status;
            this.error = error;
            this.message = message;
        }
    }
}