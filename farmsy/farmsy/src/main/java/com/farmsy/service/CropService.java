package com.farmsy.service;

import com.farmsy.model.Crop;
import com.farmsy.model.Farmer;
import com.farmsy.repository.CropRepository;
import com.farmsy.repository.FarmerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CropService {

    @Autowired
    private CropRepository cropRepository;

    @Autowired
    private FarmerRepository farmerRepository;

    public Crop addCropToFarmer(Long farmerId, Crop crop) {
        Farmer farmer = farmerRepository.findById(farmerId)
                .orElseThrow(() -> new RuntimeException("Farmer not found with id: " + farmerId));
        crop.setFarmer(farmer);
        crop.setAvailable(true);
        return cropRepository.save(crop);
    }

    public List<Crop> getAllCropsForFarmer(Long farmerId) {
        return cropRepository.findByFarmerId(farmerId);
    }

    public Crop updateCropDetails(Long cropId, Crop cropDetails) {
        Crop crop = cropRepository.findById(cropId)
                .orElseThrow(() -> new RuntimeException("Crop not found with id: " + cropId));

        crop.setName(cropDetails.getName());
        crop.setType(cropDetails.getType());
        crop.setQuantity(cropDetails.getQuantity());
        crop.setPricePerUnit(cropDetails.getPricePerUnit());
        crop.setHarvestDate(cropDetails.getHarvestDate());
        crop.setAvailable(cropDetails.getAvailable());

        return cropRepository.save(crop);
    }

    public void removeCrop(Long cropId) {
        Crop crop = cropRepository.findById(cropId)
                .orElseThrow(() -> new RuntimeException("Crop not found with id: " + cropId));
        cropRepository.delete(crop);
    }

    public List<Crop> getAvailableCropsByType(String type) {
        return cropRepository.findByTypeAndAvailable(type, true);
    }

    public List<Crop> getAvailableCropsByRegion(String region) {
        return cropRepository.findByFarmerRegionAndAvailable(region, true);
    }
}