package com.farmsy.service;

import com.farmsy.dto.FarmerResponseDto;
import com.farmsy.exception.FarmerNotFoundException;
import com.farmsy.model.Farmer;
import com.farmsy.repository.FarmerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FarmerService {

    private final FarmerRepository farmerRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public FarmerService(FarmerRepository farmerRepository,
                         PasswordEncoder passwordEncoder) {
        this.farmerRepository = farmerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Farmer registerFarmer(Farmer farmer) {
        try {
            // Encrypt password before saving
            farmer.setPassword(passwordEncoder.encode(farmer.getPassword()));
            return farmerRepository.save(farmer);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Email already exists");
        }
    }

    public List<FarmerResponseDto> getAllFarmers() {
        return farmerRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public Optional<Farmer> getFarmerById(Long id) {
        return farmerRepository.findById(id);
    }

    public Farmer updateFarmer(Long id, Farmer farmerDetails) {
        return farmerRepository.findById(id)
                .map(existingFarmer -> {
                    existingFarmer.setName(farmerDetails.getName());
                    existingFarmer.setPhone(farmerDetails.getPhone());
                    existingFarmer.setAddress(farmerDetails.getAddress());
                    existingFarmer.setRegion(farmerDetails.getRegion());

                    // Only update password if provided
                    if (farmerDetails.getPassword() != null && !farmerDetails.getPassword().isEmpty()) {
                        existingFarmer.setPassword(passwordEncoder.encode(farmerDetails.getPassword()));
                    }

                    return farmerRepository.save(existingFarmer);
                })
                .orElseThrow(() -> new FarmerNotFoundException(id));
    }

    public void deleteFarmer(Long id) {
        if (!farmerRepository.existsById(id)) {
            throw new FarmerNotFoundException(id);
        }
        farmerRepository.deleteById(id);
    }

    public List<FarmerResponseDto> getFarmersByRegion(String region) {
        return farmerRepository.findByRegion(region).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private FarmerResponseDto convertToDto(Farmer farmer) {
        FarmerResponseDto dto = new FarmerResponseDto();
        dto.setId(farmer.getId());
        dto.setName(farmer.getName());
        dto.setEmail(farmer.getEmail());
        dto.setPhone(farmer.getPhone());
        dto.setAddress(farmer.getAddress());
        dto.setRegion(farmer.getRegion());
        return dto;
    }
}