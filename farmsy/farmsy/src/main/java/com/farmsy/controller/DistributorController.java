package com.farmsy.controller;

import com.farmsy.model.Distributor;
import com.farmsy.service.DistributorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/distributors")
public class DistributorController {

    @Autowired
    private DistributorService distributorService;

    @PostMapping
    public Distributor registerDistributor(@RequestBody Distributor distributor) {
        return distributorService.registerDistributor(distributor);
    }

    @GetMapping
    public List<Distributor> getAllDistributors() {
        return distributorService.getAllDistributors();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Distributor> getDistributorById(@PathVariable Long id) {
        Optional<Distributor> distributor = distributorService.getDistributorById(id);
        return distributor.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public Distributor updateDistributor(@PathVariable Long id, @RequestBody Distributor distributorDetails) {
        return distributorService.updateDistributor(id, distributorDetails);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDistributor(@PathVariable Long id) {
        distributorService.deleteDistributor(id);
        return ResponseEntity.ok().build();
    }
}