package com.example.demo.service.impl;

import com.example.demo.entity.OrderItem;
import com.example.demo.repository.OrderItemRepository;
import com.example.demo.service.OrderItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderItemServiceimpl implements OrderItemService {

    private final OrderItemRepository orderItemRepository;

    @Override
    public List<OrderItem> findAll() {
        return orderItemRepository.findAll();
    }

    @Override
    public Optional<OrderItem> findById(Long id) {
        return orderItemRepository.findById(id);
    }

    @Override
    public List<OrderItem> findByOrderId(Long orderId) {
        return orderItemRepository.findByOrderId(orderId);
    }

    @Override
    public OrderItem save(OrderItem orderItem) {
        return orderItemRepository.save(orderItem);
    }

    @Override
    public void deleteById(Long id) {
        orderItemRepository.deleteById(id);
    }
}
