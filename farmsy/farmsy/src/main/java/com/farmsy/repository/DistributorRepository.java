package com.farmsy.repository;

import com.farmsy.model.Distributor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DistributorRepository extends JpaRepository<Distributor, Long> {
    Optional<Distributor> findByEmail(String email);
    boolean existsByEmail(String email);
}