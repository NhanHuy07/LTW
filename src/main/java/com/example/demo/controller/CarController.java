package com.example.demo.controller;

import com.example.demo.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CarController {

    @Autowired
    private CarService carService;

    @GetMapping("car/{carId}")
    public String getCarById(@PathVariable Long carId, Model model){
        model.addAttribute("car", carService.getCarById(carId));
        return "temp";
    }
}
