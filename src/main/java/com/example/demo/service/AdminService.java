package com.example.demo.service;

import com.example.demo.dto.request.AccessoryRequest;
import com.example.demo.dto.request.ProductRequest;
import com.example.demo.dto.response.MessageResponse;
import com.example.demo.entity.Accessory;
import com.example.demo.entity.Car;
import org.springframework.web.multipart.MultipartFile;

public interface AdminService {

    Car getCarById(Long carId);

    Accessory getAccessoryById(Long accessoryId);

    MessageResponse editProduct(ProductRequest productRequest, MultipartFile file);

    MessageResponse addProduct(ProductRequest productRequest, MultipartFile file);

    MessageResponse editAccessory(AccessoryRequest accessoryRequest, MultipartFile file);

    MessageResponse addAccessory(AccessoryRequest accessoryRequest, MultipartFile file);

    MessageResponse lockUser(Long id);

    MessageResponse unlockUser(Long id);

    MessageResponse deleteCar(Long id);

    MessageResponse deleteAccessory(Long id);

    MessageResponse deleteAppointment(Long id);
}
