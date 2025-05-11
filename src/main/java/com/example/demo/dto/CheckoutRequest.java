package com.example.demo.dto;

import com.example.demo.entity.Order;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CheckoutRequest {
    
    @NotBlank(message = "Họ tên không được để trống")
    private String fullName;
    
    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không hợp lệ")
    private String email;
    
    @NotBlank(message = "Số điện thoại không được để trống")
    @Pattern(regexp = "\\d{10,11}", message = "Số điện thoại phải có 10-11 chữ số")
    private String phone;
    
    @NotBlank(message = "Địa chỉ không được để trống")
    private String address;
    
    @NotBlank(message = "Thành phố không được để trống")
    private String city;
    
    @NotBlank(message = "Quận/Huyện không được để trống")
    private String district;
    
    private String zipCode;
    
    private String note;
    
    @NotBlank(message = "Phương thức thanh toán không được để trống")
    private String paymentMethod;
    
    // Helper method to convert payment method string to enum
    public Order.PaymentMethod getPaymentMethodEnum() {
        try {
            return Order.PaymentMethod.valueOf(paymentMethod);
        } catch (IllegalArgumentException e) {
            return Order.PaymentMethod.COD; // Default to COD if invalid
        }
    }
} 