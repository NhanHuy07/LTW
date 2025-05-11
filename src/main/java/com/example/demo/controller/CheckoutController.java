package com.example.demo.controller;

import com.example.demo.dto.CheckoutRequest;
import com.example.demo.entity.Cart;
import com.example.demo.entity.Order;
import com.example.demo.entity.User;
import com.example.demo.service.CartService;
import com.example.demo.service.OrderService;
import com.example.demo.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.security.Principal;

@Controller
@RequestMapping("/checkout")
public class CheckoutController {

    @Autowired
    private CartService cartService;
    
    @Autowired
    private OrderService orderService;
    
    @Autowired
    private UserService userService;
    
    @GetMapping("")
    public String checkout() {
        return "redirect:/checkout/";
    }
    
    @GetMapping("/")
    public String showCheckoutForm(Principal principal, Model model) {
        if (principal == null) {
            System.out.println("Principal is null, redirecting to login");
            return "redirect:/login";
        }
        
        System.out.println("Principal name: " + principal.getName());
        
        User user = userService.findByEmail(principal.getName());
        if (user == null) {
            System.out.println("User not found for email: " + principal.getName());
            return "redirect:/login";
        }
        
        Cart cart = cartService.getOrCreateCart(user);
        
        if (cart.getItems().isEmpty()) {
            return "redirect:/cart?error=Giỏ hàng của bạn đang trống";
        }
        
        BigDecimal subtotal = cartService.getCartTotal(cart);
        BigDecimal shippingFee = new BigDecimal("30000.00");
        BigDecimal total = subtotal.add(shippingFee);
        
        // Pre-fill checkout info if available
        CheckoutRequest checkoutRequest = new CheckoutRequest();
        checkoutRequest.setFullName(user.getName());
        checkoutRequest.setEmail(user.getEmail());
        checkoutRequest.setPhone(user.getPhoneNumber());
        checkoutRequest.setAddress(user.getAddress());
        checkoutRequest.setPaymentMethod("COD"); // Default payment method
        
        model.addAttribute("checkoutRequest", checkoutRequest);
        model.addAttribute("cart", cart);
        model.addAttribute("subtotal", subtotal);
        model.addAttribute("shippingFee", shippingFee);
        model.addAttribute("total", total);
        
        return "orders/checkout";
    }
    
    @PostMapping("/")
    public String processCheckout(
            Principal principal,
            @Valid @ModelAttribute("checkoutRequest") CheckoutRequest request,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            Model model,
            HttpSession session) {
            
        if (principal == null) {
            return "redirect:/login";
        }
        
        User user = userService.findByEmail(principal.getName());
        if (user == null) {
            return "redirect:/login";
        }
        
        Cart cart = cartService.getOrCreateCart(user);
        
        if (cart.getItems().isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Giỏ hàng của bạn đang trống");
            return "redirect:/cart";
        }
        
        if (bindingResult.hasErrors()) {
            BigDecimal subtotal = cartService.getCartTotal(cart);
            BigDecimal shippingFee = new BigDecimal("30000.00");
            BigDecimal total = subtotal.add(shippingFee);
            
            model.addAttribute("cart", cart);
            model.addAttribute("subtotal", subtotal);
            model.addAttribute("shippingFee", shippingFee);
            model.addAttribute("total", total);
            return "orders/checkout";
        }
        
        try {
            // Create order
            Order order = orderService.createOrderFromCart(
                user,
                cart,
                request.getFullName(),
                request.getEmail(),
                request.getPhone(),
                request.getAddress(),
                request.getCity(),
                request.getDistrict(),
                request.getZipCode(),
                request.getNote(),
                request.getPaymentMethodEnum()
            );
            
            // Clear cart after successful order
            cartService.clearCart(user);
            
            // Store order number in session for confirmation page
            session.setAttribute("lastOrderNumber", order.getOrderNumber());
            
            return "redirect:/checkout/confirmation";
            
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Đã xảy ra lỗi khi tạo đơn hàng: " + e.getMessage());
            return "redirect:/checkout/";
        }
    }
    
    @GetMapping("/confirmation")
    public String showConfirmation(HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        String orderNumber = (String) session.getAttribute("lastOrderNumber");
        
        if (orderNumber == null) {
            redirectAttributes.addFlashAttribute("error", "Không tìm thấy thông tin đơn hàng");
            return "redirect:/cart";
        }
        
        // Remove from session
        session.removeAttribute("lastOrderNumber");
        
        Order order = orderService.findByOrderNumber(orderNumber)
                .orElseThrow(() -> new IllegalStateException("Không tìm thấy đơn hàng với mã: " + orderNumber));
        
        model.addAttribute("order", order);
        
        return "orders/confirmation";
    }
} 