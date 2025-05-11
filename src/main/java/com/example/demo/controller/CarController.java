package com.example.demo.controller;

import com.example.demo.entity.Car;
import com.example.demo.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
public class CarController {

    @Autowired
    private CarService carService;

//    @GetMapping("/cars")
//    public String getAllCar(Model model, @RequestParam(name = "pageNumber", defaultValue = "1") Integer pageNumber) {
//        Page<Car> list = carService.getAll(pageNumber);
//        model.addAttribute("totalPage", list.getTotalPages());
//        model.addAttribute("currentPage", pageNumber);
//        model.addAttribute("list",list);
//        return "cars";
//    }

    @GetMapping("car/{carId}")
    public String getCarById(@PathVariable Long carId, Model model){
        model.addAttribute("car", carService.getCarById(carId));
        return "car";
    }

    @GetMapping("/cars")
    public String Search(Model model, @Param("keyword") String keyword, @RequestParam(name = "pageNumber", defaultValue = "1") Integer pageNumber){
        Page<Car> list = carService.getAll(pageNumber);
        if(keyword != null){
            list = carService.searchCar(keyword,pageNumber);
            model.addAttribute("keyword",keyword);
        }
        model.addAttribute("totalPage", list.getTotalPages());
        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("list",list);
        return "cars";
    }

    @GetMapping("/cars/filter")
    public String findByFilters(
            Model model,
            @RequestParam(name = "brand", required = false) String brand,
            @RequestParam(name = "fuel", required = false) String fuel,
            @RequestParam(name = "color", required = false) String color,
            @RequestParam(name = "numberOfSeats", required = false) String numberOfSeats, // Thay Long bằng String
            @RequestParam(name = "pageNumber", defaultValue = "1") Integer pageNumber) {

        // Chuyển chuỗi rỗng thành null
        if (brand != null && brand.isEmpty()) brand = null;
        if (fuel != null && fuel.isEmpty()) fuel = null;
        if (color != null && color.isEmpty()) color = null;
        if (numberOfSeats != null && numberOfSeats.isEmpty()) numberOfSeats = null;

        Page<Car> list = carService.getAll(pageNumber);
        if (brand != null || fuel != null || color != null || numberOfSeats != null) {
            // Chuyển numberOfSeats từ String sang Long nếu cần (phụ thuộc vào carService)
            Long seats = (numberOfSeats != null) ? Long.valueOf(numberOfSeats) : null;
            list = carService.findByFilters(brand, fuel, color, seats, pageNumber);
        }

        model.addAttribute("selectedBrand", brand);
        model.addAttribute("selectedFuel", fuel);
        model.addAttribute("selectedColor", color);
        model.addAttribute("selectedNumberOfSeats", numberOfSeats); // Truyền String để khớp với value
        model.addAttribute("totalPage", list.getTotalPages());
        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("list", list);

        return "cars";
    }
}
