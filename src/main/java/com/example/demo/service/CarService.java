package com.example.demo.service;

import com.example.demo.entity.Car;

import java.util.List;

public interface CarService {
    Car getCarById(Long carId);

    List<Car> getPopularCars();
}
