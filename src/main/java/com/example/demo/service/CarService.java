package com.example.demo.service;

import com.example.demo.entity.Car;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CarService {
    Car getCarById(Long carId);

    List<Car> getPopularCars();

    List<Car> searchCar(String keyword);

    Page<Car> getAll(Integer pageNumber);

    Page<Car> searchCar(String keyword, Integer pageNumber);
}
