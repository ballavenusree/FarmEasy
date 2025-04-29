package com.farmsy.controller;

import com.farmsy.model.Crop;
import com.farmsy.service.CropService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/crops")
public class CropController {

    @Autowired
    private CropService cropService;

    @PostMapping("/farmer/{farmerId}")
    public Crop addCrop(@PathVariable Long farmerId, @RequestBody Crop crop) {
        return cropService.addCropToFarmer(farmerId, crop);
    }

    @GetMapping("/farmer/{farmerId}")
    public List<Crop> getCropsByFarmerId(@PathVariable Long farmerId) {
        return cropService.getAllCropsForFarmer(farmerId);
    }

    @GetMapping("/type/{type}")
    public List<Crop> getCropsByType(@PathVariable String type) {
        return cropService.getAvailableCropsByType(type);
    }

    @GetMapping("/region/{region}")
    public List<Crop> getCropsByRegion(@PathVariable String region) {
        return cropService.getAvailableCropsByRegion(region);
    }

    @PutMapping("/{cropId}")
    public Crop updateCrop(@PathVariable Long cropId, @RequestBody Crop cropDetails) {
        return cropService.updateCropDetails(cropId, cropDetails);
    }

    @DeleteMapping("/{cropId}")
    public ResponseEntity<?> deleteCrop(@PathVariable Long cropId) {
        cropService.removeCrop(cropId);
        return ResponseEntity.ok().build();
    }
}