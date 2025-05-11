package com.example.demo.service;

import com.example.demo.entity.OrderItem;

import java.util.List;
import java.util.Optional;

public interface OrderItemService {
    List<OrderItem> findAll();
    Optional<OrderItem> findById(Long id);
    List<OrderItem> findByOrderId(Long orderId);
    OrderItem save(OrderItem orderItem);
    void deleteById(Long id);
}
