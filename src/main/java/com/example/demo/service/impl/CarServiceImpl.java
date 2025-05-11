package com.example.demo.service.impl;

import com.example.demo.entity.Car;
import com.example.demo.repository.CarRepository;
import com.example.demo.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {

    @Autowired
    private CarRepository carRepository;

    @Override
    public Car getCarById(Long carId) {
        return carRepository.findById(carId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Khong tim thay san pham"));
    }

    @Override
    public java.util.Optional<Car> findById(Long id) {
        return carRepository.findById(id);
    }

    @Override
    public List<Car> getPopularCars() {
        List<Long> carIds = Arrays.asList(1L, 2L, 3L);
        return carRepository.findByIdIn(carIds);
    }

    @Override
    public List<Car> searchCar(String keyword) {
        return carRepository.findByCarNameContaining(keyword);
    }

    @Override
    public Page<Car> getAll(Integer pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber - 1, 6);
        return carRepository.findAll(pageable);
    }

    @Override
    public Page<Car> searchCar(String keyword, Integer pageNumber) {
        List<Car> list = carRepository.findByCarNameContaining(keyword);
        Integer searchSize = list.size();
        Pageable pageable = PageRequest.of(pageNumber - 1, 6);
        Integer start = (int) pageable.getOffset();
        Integer end = (int) ((pageable.getOffset() + pageable.getPageSize()) > list.size() ? list.size() : pageable.getOffset() + pageable.getPageSize());
        list = list.subList(start,end);
        return new PageImpl<Car>(list,pageable,searchSize);
    }

    @Override
    public List<Car> findAll() {
        return carRepository.findAll();
    }

    @Override
    public Page<Car> findByBrand(String brand, Integer pageNumber) {
        List<Car> list = carRepository.findByBrandIgnoreCase(brand);
        Integer searchSize = list.size();
        Pageable pageable = PageRequest.of(pageNumber - 1, 6);
        Integer start = (int) pageable.getOffset();
        Integer end = (int) ((pageable.getOffset() + pageable.getPageSize()) > list.size() ? list.size() : pageable.getOffset() + pageable.getPageSize());
        list = list.subList(start,end);
        return new PageImpl<Car>(list,pageable,searchSize);
    }

    @Override
    public List<String> getAllBrands() {
        return carRepository.findAllBrands();
    }

    public Page<Car> findByFilters(String brand, String fuel, String color, Long numberOfSeats, Integer pageNumber) {
        List<Car> list = carRepository.findByFilters(brand,fuel,color,numberOfSeats);
        Integer searchSize = list.size();
        Pageable pageable = PageRequest.of(pageNumber - 1, 6);
        Integer start = (int) pageable.getOffset();
        Integer end = (int) ((pageable.getOffset() + pageable.getPageSize()) > list.size() ? list.size() : pageable.getOffset() + pageable.getPageSize());
        list = list.subList(start,end);
        return new PageImpl<Car>(list,pageable,searchSize);
    }
}
