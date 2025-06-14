package com.farmsy.repository;

import com.farmsy.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByDistributorId(Long distributorId);
    List<Order> findByFarmerId(Long farmerId);
    List<Order> findByStatus(String status);
}