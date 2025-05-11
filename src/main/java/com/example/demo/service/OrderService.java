package com.example.demo.service;

import com.example.demo.entity.*;
import com.example.demo.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class OrderService {
    
    @Autowired
    private OrderRepository orderRepository;
    
    public List<Order> findOrdersByUser(User user) {
        return orderRepository.findByUserOrderByCreatedAtDesc(user);
    }
    
    public Optional<Order> findById(Long id) {
        return orderRepository.findById(id);
    }
    
    public Optional<Order> findByOrderNumber(String orderNumber) {
        return orderRepository.findByOrderNumber(orderNumber);
    }
    
    public List<Order> findAllOrders() {
        return orderRepository.findAllByOrderByCreatedAtDesc();
    }
    
    public List<Order> findOrdersByStatus(Order.OrderStatus status) {
        return orderRepository.findByStatusOrderByCreatedAtDesc(status);
    }
    
    @Transactional
    public Order createOrderFromCart(User user, Cart cart, String fullName, String email, 
                              String phone, String address, String city, String district, 
                              String zipCode, String note, Order.PaymentMethod paymentMethod) {
        if (cart.getItems().isEmpty()) {
            throw new IllegalStateException("Giỏ hàng trống");
        }
        
        Order order = new Order();
        order.setOrderNumber(generateOrderNumber());
        order.setUser(user);
        order.setFullName(fullName);
        order.setEmail(email);
        order.setPhone(phone);
        order.setAddress(address);
        order.setCity(city);
        order.setDistrict(district);
        order.setZipCode(zipCode);
        order.setNote(note);
        order.setPaymentMethod(paymentMethod);
        
        // Tính tổng tiền
        BigDecimal subtotal = cart.getItems().stream()
                .map(item -> item.getPrice().multiply(new BigDecimal(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
                
        order.setSubtotal(subtotal);
        order.setTotalAmount(subtotal.add(order.getShippingFee()));
        
        // Tạo các order item từ cart item
        for (CartItem cartItem : cart.getItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setAccessory(cartItem.getAccessory());
            orderItem.setAccessoryName(cartItem.getAccessory().getName());
            orderItem.setAccessoryCategory(cartItem.getAccessory().getCategory());
            orderItem.setAccessoryImage(cartItem.getAccessory().getImage());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(cartItem.getAccessory().getPrice());
            orderItem.calculateSubtotal();
            
            order.addOrderItem(orderItem);
        }
        
        // Tạo payment
        Payment payment = new Payment();
        payment.setPaymentMethod(paymentMethod);
        payment.setAmount(order.getTotalAmount());
        payment.setStatus(Payment.PaymentStatus.PENDING);
        
        order.setPaymentInfo(payment);
        
        // Lưu order
        return orderRepository.save(order);
    }
    
    @Transactional
    public void updateOrderStatus(Order order, Order.OrderStatus status) {
        if (order.getStatus() == status) {
            return;
        }
        
        order.setStatus(status);
        
        switch (status) {
            case CONFIRMED:
                order.setConfirmedAt(LocalDateTime.now());
                break;
            case SHIPPED:
                order.setShippedAt(LocalDateTime.now());
                break;
            case DELIVERED:
                order.setDeliveredAt(LocalDateTime.now());
                if (order.getPaymentMethod() == Order.PaymentMethod.COD) {
                    order.setPaymentStatus(Order.PaymentStatus.PAID);
                    order.getPayment().setStatus(Payment.PaymentStatus.COMPLETED);
                    order.getPayment().setPaymentDate(LocalDateTime.now());
                }
                break;
            case CANCELED:
                order.setCanceledAt(LocalDateTime.now());
                break;
            default:
                break;
        }
        
        orderRepository.save(order);
    }
    
    @Transactional
    public void updatePaymentStatus(Order order, String transactionId, Payment.PaymentStatus status) {
        Payment payment = order.getPayment();
        
        payment.setTransactionId(transactionId);
        payment.setStatus(status);
        
        if (status == Payment.PaymentStatus.COMPLETED) {
            payment.setPaymentDate(LocalDateTime.now());
            order.setPaymentStatus(Order.PaymentStatus.PAID);
        } else {
            order.setPaymentStatus(Order.PaymentStatus.UNPAID);
        }
        
        orderRepository.save(order);
    }
    
    private String generateOrderNumber() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String datePrefix = LocalDateTime.now().format(formatter);
        
        // Tạo 6 số ngẫu nhiên
        Random random = new Random();
        String randomDigits = String.format("%06d", random.nextInt(1000000));
        
        return "ORD" + datePrefix + randomDigits;
    }
} 