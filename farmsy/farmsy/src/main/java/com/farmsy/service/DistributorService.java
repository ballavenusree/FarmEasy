package com.farmsy.service;

import com.farmsy.model.Distributor;
import com.farmsy.repository.DistributorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DistributorService {

    @Autowired
    private DistributorRepository distributorRepository;

    public Distributor registerDistributor(Distributor distributor) {
        if (distributorRepository.existsByEmail(distributor.getEmail())) {
            throw new RuntimeException("Email already in use");
        }
        return distributorRepository.save(distributor);
    }

    public List<Distributor> getAllDistributors() {
        return distributorRepository.findAll();
    }

    public Optional<Distributor> getDistributorById(Long id) {
        return distributorRepository.findById(id);
    }

    public Distributor updateDistributor(Long id, Distributor distributorDetails) {
        Distributor distributor = distributorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Distributor not found"));

        distributor.setName(distributorDetails.getName());
        distributor.setPhone(distributorDetails.getPhone());
        distributor.setAddress(distributorDetails.getAddress());
        distributor.setBusinessLicense(distributorDetails.getBusinessLicense());

        return distributorRepository.save(distributor);
    }

    public void deleteDistributor(Long id) {
        Distributor distributor = distributorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Distributor not found"));
        distributorRepository.delete(distributor);
    }
}