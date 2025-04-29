package com.farmsy.controller;

import com.farmsy.model.Order;
import com.farmsy.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.farmsy.model.OrderStatus;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/distributor/{distributorId}/crop/{cropId}")
    public Order createOrder(@PathVariable Long distributorId,
                             @PathVariable Long cropId,
                             @RequestParam Double quantity) {
        return orderService.createOrder(distributorId, cropId, quantity);
    }

    @GetMapping("/distributor/{distributorId}")
    public List<Order> getOrdersByDistributorId(@PathVariable Long distributorId) {
        return orderService.getOrdersByDistributorId(distributorId);
    }

    @GetMapping("/farmer/{farmerId}")
    public List<Order> getOrdersByFarmerId(@PathVariable Long farmerId) {
        return orderService.getOrdersByFarmerId(farmerId);
    }

    @PutMapping("/{orderId}/status")
    public Order updateOrderStatus(@PathVariable Long orderId, @RequestParam OrderStatus status) {
        return orderService.updateOrderStatus(orderId, status);
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<?> deleteOrder(@PathVariable Long orderId) {
        orderService.deleteOrder(orderId);
        return ResponseEntity.ok().build();
    }
}