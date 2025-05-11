package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin-test")
public class AdminTestController {

    @GetMapping("/simple")
    public String simpleTest(Model model) {
        model.addAttribute("message", "Đây là trang test đơn giản");
        return "admin/simple-test";
    }
    
    @GetMapping("/layout-test")
    public String layoutTest(Model model) {
        model.addAttribute("pageTitle", "Trang Test Layout");
        model.addAttribute("active", "dashboard");
        model.addAttribute("message", "Đây là nội dung test layout");
        model.addAttribute("content", "admin/simple-test :: content");
        return "admin/layout";
    }
} 