package com.example.demo.controller;

import com.example.demo.entity.Accessory;
import com.example.demo.entity.User;
import com.example.demo.service.AccessoryService;
import com.example.demo.service.CartService;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/accessories")
@RequiredArgsConstructor
public class AccessoryController {

    private final AccessoryService accessoryService;
    private final CartService cartService;
    private final UserService userService;

    @GetMapping
    public String listAccessories(Model model) {
        List<Accessory> accessories = accessoryService.findAll();
        model.addAttribute("accessories", accessories);
        return "accessories/list";
    }

    @GetMapping("/{id}")
    public String accessoryDetails(@PathVariable Long id, Model model) {
        Accessory accessory = accessoryService.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy phụ tùng với ID: " + id));
        model.addAttribute("accessory", accessory);
        model.addAttribute("quantity", 1);
        return "accessories/details";
    }

    @GetMapping("/category/{category}")
    public String listAccessoriesByCategory(@PathVariable String category, Model model) {
        List<Accessory> accessories = accessoryService.findByCategory(category);
        model.addAttribute("accessories", accessories);
        model.addAttribute("category", category);
        return "accessories/list";
    }

    @PostMapping("/{id}/add-to-cart")
    public String addToCart(@PathVariable Long id, @RequestParam("quantity") int quantity, 
                           RedirectAttributes redirectAttributes) {
        try {
            User user = userService.getAuthenticatedUser();
            Accessory accessory = accessoryService.findById(id)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy phụ tùng với ID: " + id));
            
            cartService.addToCart(user, accessory, quantity);
            redirectAttributes.addFlashAttribute("success", "Đã thêm " + accessory.getName() + " vào giỏ hàng");
            return "redirect:/cart";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi khi thêm vào giỏ hàng: " + e.getMessage());
            return "redirect:/accessories/" + id;
        }
    }
} 