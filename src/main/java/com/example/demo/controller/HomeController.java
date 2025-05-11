package com.example.demo.controller;

import com.example.demo.entity.Car;
import com.example.demo.repository.CarRepository;
import com.example.demo.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final CarService carService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("cars",carService.getPopularCars());
        return "index";
    }

    @GetMapping("/contact")
    public String contact() {
        return "contact";
    }
    
    @GetMapping({"/blog", "/blog.html"})
    public String blog() {
        return "blog";
    }
    
    @GetMapping({"/blog-details", "/blog-details.html"})
    public String blogDetails() {
        return "blog-details";
    }
    
    @GetMapping({"/faq", "/faq.html"})
    public String faq() {
        return "faq";
    }
    
    @GetMapping({"/terms", "/terms.html"})
    public String terms() {
        return "terms";
    }
    
    @GetMapping({"/car-details", "/car-details.html"})
    public String carDetails(){
        return "car-details";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
