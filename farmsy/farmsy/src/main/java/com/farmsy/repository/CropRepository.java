package com.farmsy.repository;

import com.farmsy.model.Crop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CropRepository extends JpaRepository<Crop, Long> {
    List<Crop> findByFarmerId(Long farmerId);
    List<Crop> findByTypeAndAvailable(String type, Boolean available);
    List<Crop> findByFarmerRegionAndAvailable(String region, Boolean available);
}
