package com.example.demo.repository;

import com.example.demo.entity.Order;
import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    
    List<Order> findByUserOrderByCreatedAtDesc(User user);
    
    Optional<Order> findByOrderNumber(String orderNumber);
    
    List<Order> findByStatusOrderByCreatedAtDesc(Order.OrderStatus status);
    
    List<Order> findAllByOrderByCreatedAtDesc();
} 