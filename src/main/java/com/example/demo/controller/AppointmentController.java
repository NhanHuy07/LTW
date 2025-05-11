package com.example.demo.controller;

import com.example.demo.entity.Appointment;
import com.example.demo.entity.Car;
import com.example.demo.entity.User;
import com.example.demo.service.AppointmentService;
import com.example.demo.service.CarService;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final CarService carService;
    private final UserService userService;

    @GetMapping("/book/{carId}")
    public String showBookingForm(@PathVariable Long carId, Model model) {
        Car car = carService.findById(carId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy xe với ID: " + carId));
        
        Appointment appointment = new Appointment();
        appointment.setCar(car);
        
        try {
            User user = userService.getAuthenticatedUser();
            appointment.setUser(user);
            appointment.setName(user.getName());
            appointment.setEmail(user.getEmail());
            appointment.setPhoneNumber(user.getPhoneNumber());
        } catch (Exception e) {
            // Nếu người dùng chưa đăng nhập, không điền thông tin trước
        }
        
        model.addAttribute("appointment", appointment);
        model.addAttribute("car", car);
        return "appointments/book";
    }

    @PostMapping("/book")
    public String bookAppointment(@ModelAttribute Appointment appointment, RedirectAttributes redirectAttributes) {
        try {
            // Kiểm tra ngày hẹn phải lớn hơn ngày hiện tại
            if (appointment.getAppointmentDate().isBefore(LocalDateTime.now())) {
                redirectAttributes.addFlashAttribute("error", "Ngày hẹn phải lớn hơn ngày hiện tại");
                return "redirect:/appointments/book/" + appointment.getCar().getId();
            }
            
            appointmentService.save(appointment);
            redirectAttributes.addFlashAttribute("success", "Đặt lịch hẹn thành công");
            return "redirect:/appointments/my-appointments";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi khi đặt lịch hẹn: " + e.getMessage());
            return "redirect:/appointments/book/" + appointment.getCar().getId();
        }
    }

    @GetMapping("/my-appointments")
    public String myAppointments(Model model) {
        try {
            User user = userService.getAuthenticatedUser();
            model.addAttribute("appointments", appointmentService.findByUser(user));
            return "appointments/my-appointments";
        } catch (Exception e) {
            // Nếu người dùng chưa đăng nhập, chuyển hướng đến trang đăng nhập
            return "redirect:/login";
        }
    }

    @GetMapping("/cancel/{id}")
    public String cancelAppointment(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            User currentUser = userService.getAuthenticatedUser();
            Appointment appointment = appointmentService.findById(id)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy lịch hẹn với ID: " + id));
            
            // Kiểm tra xem lịch hẹn có thuộc về người dùng hiện tại không
            if (appointment.getUser() != null && appointment.getUser().getId().equals(currentUser.getId())) {
                appointment.setStatus("CANCELED");
                appointmentService.save(appointment);
                redirectAttributes.addFlashAttribute("success", "Hủy lịch hẹn thành công");
            } else {
                redirectAttributes.addFlashAttribute("error", "Bạn không có quyền hủy lịch hẹn này");
            }
            return "redirect:/appointments/my-appointments";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi khi hủy lịch hẹn: " + e.getMessage());
            return "redirect:/appointments/my-appointments";
        }
    }
} 