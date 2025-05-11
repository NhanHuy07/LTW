package com.example.demo.controller;

import com.example.demo.entity.Car;
import com.example.demo.entity.User;
import com.example.demo.service.CarService;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/test")
@RequiredArgsConstructor
public class ApiTestController {

    private final UserService userService;
    private final CarService carService;

    @GetMapping("/data")
    public ResponseEntity<Map<String, Object>> testData() {
        Map<String, Object> data = new HashMap<>();
        
        try {
            List<User> users = userService.findAll();
            data.put("users", users);
            data.put("usersCount", users.size());
        } catch (Exception e) {
            data.put("userError", e.getMessage());
        }
        
        try {
            List<Car> cars = carService.findAll();
            data.put("cars", cars);
            data.put("carsCount", cars.size());
        } catch (Exception e) {
            data.put("carError", e.getMessage());
        }
        
        return ResponseEntity.ok(data);
    }
} 