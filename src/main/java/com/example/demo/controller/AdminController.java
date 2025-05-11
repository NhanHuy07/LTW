package com.example.demo.controller;

import com.example.demo.constants.Pages;
import com.example.demo.dto.request.AccessoryRequest;
import com.example.demo.dto.request.ProductRequest;
import com.example.demo.entity.Car;
import com.example.demo.entity.Accessory;
import com.example.demo.entity.User;
import com.example.demo.entity.Appointment;
import com.example.demo.entity.OrderItem;
import com.example.demo.service.*;
import com.example.demo.utils.ControllerUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
// @PreAuthorize("hasAuthority('ADMIN')")
public class AdminController {

    private final ControllerUtils controllerUtils;
    private final AdminService adminService;
    private final UserService userService;
    private final CarService carService;
    private final AccessoryService accessoryService;
    private final AppointmentService appointmentService;
    private final OrderItemService orderItemService;
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        try {
            // Lấy số lượng người dùng
            List<User> users;
            try {
                users = userService.findAll();
            } catch (Exception e) {
                users = Collections.emptyList();
                model.addAttribute("userError", "Lỗi khi lấy danh sách người dùng: " + e.getMessage());
            }
            model.addAttribute("totalUsers", users.size());
            model.addAttribute("users", users);

            // Lấy số lượng xe
            List<Car> cars;
            try {
                cars = carService.findAll();
            } catch (Exception e) {
                cars = Collections.emptyList();
                model.addAttribute("carError", "Lỗi khi lấy danh sách xe: " + e.getMessage());
            }
            model.addAttribute("totalCars", cars.size());
            model.addAttribute("cars", cars);

            // Thêm các thuộc tính cho layout
            model.addAttribute("pageTitle", "Dashboard");
            model.addAttribute("active", "dashboard");

            return "admin/dashboard";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Lỗi máy chủ: " + e.getMessage());
            model.addAttribute("trace", e.getStackTrace());
            return "error";
        }
    }

    @GetMapping({"/users", "/users.html"})
    public String listUsers(Model model) {
        try {
            List<User> users = userService.findAll();
            model.addAttribute("users", users);

            // Thêm các thuộc tính cho layout
            model.addAttribute("pageTitle", "Quản lý người dùng");
            model.addAttribute("active", "users");

            return "admin/users";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Lỗi máy chủ: " + e.getMessage());
            model.addAttribute("trace", e.getStackTrace());
            return "error";
        }
    }

    @GetMapping({"/cars", "/cars.html"})
    public String listCars(Model model) {
        try {
            // Lấy danh sách xe từ service
            List<Car> cars = carService.findAll();
            // Nếu danh sách null thì gán thành danh sách rỗng để tránh lỗi NullPointerException
            if (cars == null) {
                cars = Collections.emptyList();
            }

            // Thêm danh sách xe vào model
            model.addAttribute("cars", cars);
            // Thêm các thuộc tính cho layout
            model.addAttribute("pageTitle", "Quản lý xe");
            model.addAttribute("active", "cars");

            return "admin/cars";
        } catch (Exception e) {
            // Ghi log lỗi
            e.printStackTrace();

            // Thêm thông tin lỗi vào model
            model.addAttribute("error", "Lỗi khi tải danh sách xe: " + e.getMessage());
            model.addAttribute("trace", e.getStackTrace());

            // Thêm các thuộc tính cần thiết cho layout error
            model.addAttribute("pageTitle", "Lỗi - Quản lý xe");

            // Trả về trang lỗi
            return "error";
        }
    }

    @GetMapping({"/parts", "/parts.html"})
    public String listAccessories(Model model) {
        try {
            // Lấy danh sách phụ tùng từ service
            List<Accessory> accessories = accessoryService.findAll();
            // Nếu danh sách null thì gán thành danh sách rỗng để tránh lỗi NullPointerException
            if (accessories == null) {
                accessories = Collections.emptyList();
            }

            // Thêm danh sách phụ tùng vào model
            model.addAttribute("accessories", accessories);
            // Thêm các thuộc tính cho layout
            model.addAttribute("pageTitle", "Quản lý phụ tùng");
            model.addAttribute("active", "parts");

            return "admin/parts";
        } catch (Exception e) {
            // Ghi log lỗi
            e.printStackTrace();

            // Thêm thông tin lỗi vào model
            model.addAttribute("error", "Lỗi khi tải danh sách phụ tùng: " + e.getMessage());
            model.addAttribute("trace", e.getStackTrace());

            // Thêm các thuộc tính cần thiết cho layout error
            model.addAttribute("pageTitle", "Lỗi - Quản lý phụ tùng");

            // Trả về trang lỗi
            return "error";
        }
    }
    @GetMapping({"/appointments", "/appointments.html"})
    public String listAppointments(Model model) {
        try {
            // Lấy danh sách cuộc hẹn từ service
            List<Appointment> appointments = appointmentService.findAll();
            // Nếu danh sách null thì gán thành danh sách rỗng để tránh lỗi NullPointerException
            if (appointments == null) {
                appointments = Collections.emptyList();
            }

            // Thêm danh sách cuộc hẹn vào model
            model.addAttribute("appointments", appointments);
            // Thêm các thuộc tính cho layout
            model.addAttribute("pageTitle", "Quản lý cuộc hẹn");
            model.addAttribute("active", "appointments");

            return "admin/appointments";
        } catch (Exception e) {
            // Ghi log lỗi
            e.printStackTrace();

            // Thêm thông tin lỗi vào model
            model.addAttribute("error", "Lỗi khi tải danh sách cuộc hẹn: " + e.getMessage());
            model.addAttribute("trace", e.getStackTrace());

            // Thêm các thuộc tính cần thiết cho layout error
            model.addAttribute("pageTitle", "Lỗi - Quản lý cuộc hẹn");

            // Trả về trang lỗi
            return "error";
        }
    }

    @GetMapping({"/order-items", "/order-items.html"})
    public String listOrderItems(Model model) {
        try {
            // Lấy danh sách đơn hàng từ service
            List<OrderItem> orderItems = orderItemService.findAll();
            // Nếu danh sách null thì gán thành danh sách rỗng để tránh lỗi NullPointerException
            if (orderItems == null) {
                orderItems = Collections.emptyList();
            }

            // Thêm danh sách đơn hàng vào model
            model.addAttribute("orderItems", orderItems);
            // Thêm các thuộc tính cho layout
            model.addAttribute("pageTitle", "Quản lý đơn hàng");
            model.addAttribute("active", "order-items");

            return "admin/order-items";
        } catch (Exception e) {
            // Ghi log lỗi
            e.printStackTrace();

            // Thêm thông tin lỗi vào model
            model.addAttribute("error", "Lỗi khi tải danh sách đơn hàng: " + e.getMessage());
            model.addAttribute("trace", e.getStackTrace());

            // Thêm các thuộc tính cần thiết cho layout error
            model.addAttribute("pageTitle", "Lỗi - Quản lý đơn hàng");

            // Trả về trang lỗi
            return "error";
        }
    }
    @GetMapping("/edit/cars/{carId}")
    public String editCar(@PathVariable Long carId, Model model){
        model.addAttribute("car", adminService.getCarById(carId));
        return "admin-edit-cars";
    }
    @PostMapping("/edit/cars")
    public String editCar(@Valid ProductRequest product, BindingResult bindingResult, Model model,
                              @RequestParam("file") MultipartFile file, RedirectAttributes attributes) {
        if (controllerUtils.validateInputFields(bindingResult, model, "car", product)) {
            return "admin-edit-cars";
        }
        return controllerUtils.setAlertFlashMessage(attributes, "/admin/cars", adminService.editProduct(product, file));
    }

    @GetMapping("/add/cars")
    public String addCar(){
        return "admin-add-cars";
    }
    @PostMapping("/add/cars")
    public String addCar(@Valid ProductRequest product, BindingResult bindingResult, Model model,
                             @RequestParam("file") MultipartFile file, RedirectAttributes attributes) {
//        if (controllerUtils.validateInputFields(bindingResult, model, "car", product)) {
//            return "admin-add-cars";
//        }
        return controllerUtils.setAlertFlashMessage(attributes, "/admin/cars", adminService.addProduct(product, file));
    }

    @GetMapping("/edit/accessory/{accessoryId}")
    public String editAccessory(@PathVariable Long accessoryId, Model model){
        model.addAttribute("accessory", adminService.getAccessoryById(accessoryId));
        return "admin-edit-accessory";
    }
    @PostMapping("/edit/accessory")
    public String editAccessory(@Valid AccessoryRequest accessoryRequest, BindingResult bindingResult, Model model,
                          @RequestParam("file") MultipartFile file, RedirectAttributes attributes) {
//        if (controllerUtils.validateInputFields(bindingResult, model, "accessory", accessoryRequest)) {
//            return "admin-edit-accessory";
//        }
        return controllerUtils.setAlertFlashMessage(attributes, "/admin/parts", adminService.editAccessory(accessoryRequest, file));
    }

    @GetMapping("/add/accessory")
    public String addAccessory(){
        return "admin-add-accessory";
    }
    @PostMapping("/add/accessory")
    public String addAccessory(@Valid AccessoryRequest accessoryRequest, BindingResult bindingResult, Model model,
                               @RequestParam("file") MultipartFile file, RedirectAttributes attributes) {
//        if (controllerUtils.validateInputFields(bindingResult, model, "accessory", accessoryRequest)) {
//            return "admin-edit-accessory";
//        }
        return controllerUtils.setAlertFlashMessage(attributes, "/admin/parts", adminService.addAccessory(accessoryRequest,file));
    }

    @GetMapping("/lock/user/{id}")
    public String lockUser(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        return controllerUtils.setAlertFlashMessage(redirectAttributes, "/admin/users", adminService.lockUser(id));
    }

    @GetMapping("/unlock/user/{id}")
    public String unlockUser(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        return controllerUtils.setAlertFlashMessage(redirectAttributes, "/admin/users", adminService.unlockUser(id));
    }

    @PostMapping("/delete/car/{carId}")
    public String deleteCar(@PathVariable Long carId, RedirectAttributes redirectAttributes){
        return controllerUtils.setAlertFlashMessage(redirectAttributes, "/admin/cars", adminService.deleteCar(carId));
    }
    @PostMapping("/delete/accessory/{accessoryId}")
    public String deleteAccessory(@PathVariable Long accessoryId, RedirectAttributes redirectAttributes){
        return controllerUtils.setAlertFlashMessage(redirectAttributes, "/admin/parts", adminService.deleteAccessory(accessoryId));
    }

    @PostMapping("/delete/appointment/{id}")
    public String deleteAppointment(@PathVariable Long id, RedirectAttributes redirectAttributes){
        return controllerUtils.setAlertFlashMessage(redirectAttributes, "/admin/appointments", adminService.deleteAppointment(id));
    }
} 