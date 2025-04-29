package com.farmsy.service;

import com.farmsy.model.*;
import com.farmsy.repository.CropRepository;
import com.farmsy.repository.DistributorRepository;
import com.farmsy.repository.FarmerRepository;
import com.farmsy.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CropRepository cropRepository;

    @Autowired
    private DistributorRepository distributorRepository;

    @Autowired
    private FarmerRepository farmerRepository;

    public Order createOrder(Long distributorId, Long cropId, Double quantity) {
        Distributor distributor = distributorRepository.findById(distributorId)
                .orElseThrow(() -> new RuntimeException("Distributor not found"));

        Crop crop = cropRepository.findById(cropId)
                .orElseThrow(() -> new RuntimeException("Crop not found"));

        if (!crop.getAvailable()) {
            throw new RuntimeException("Crop is not available");
        }

        if (crop.getQuantity() < quantity) {
            throw new RuntimeException("Requested quantity exceeds available quantity");
        }

        Farmer farmer = crop.getFarmer();

        Order order = new Order();
        order.setQuantity(quantity);
        order.setTotalPrice(quantity * crop.getPricePerUnit());
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(OrderStatus.PENDING);
        order.setCrop(crop);
        order.setDistributor(distributor);
        order.setFarmer(farmer);

        return orderRepository.save(order);
    }

    public List<Order> getOrdersByDistributorId(Long distributorId) {
        return orderRepository.findByDistributorId(distributorId);
    }

    public List<Order> getOrdersByFarmerId(Long farmerId) {
        return orderRepository.findByFarmerId(farmerId);
    }

    public Order updateOrderStatus(Long orderId, OrderStatus status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setStatus(status);

        if (status == OrderStatus.ACCEPTED) {
            Crop crop = order.getCrop();
            crop.setQuantity(crop.getQuantity() - order.getQuantity());
            if (crop.getQuantity() <= 0) {
                crop.setAvailable(false);
            }
            cropRepository.save(crop);
        }

        return orderRepository.save(order);
    }

    public void deleteOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        orderRepository.delete(order);
    }
}