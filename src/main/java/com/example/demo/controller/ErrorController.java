package com.example.demo.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

@Controller
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {

    private final ErrorAttributes errorAttributes;

    public ErrorController(ErrorAttributes errorAttributes) {
        this.errorAttributes = errorAttributes;
    }

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, WebRequest webRequest, Model model) {
        // Lấy thông tin lỗi
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        Object message = request.getAttribute(RequestDispatcher.ERROR_MESSAGE);
        Object exception = request.getAttribute(RequestDispatcher.ERROR_EXCEPTION);

        // Thêm thông tin vào model
        if (status != null) {
            model.addAttribute("status", status);
        }
        
        if (message != null) {
            model.addAttribute("message", message);
        } else {
            model.addAttribute("message", "Đã xảy ra lỗi không xác định");
        }

        if (exception != null) {
            model.addAttribute("error", exception.toString());
            if (exception instanceof Exception) {
                Exception ex = (Exception) exception;
                StringBuilder stackTrace = new StringBuilder();
                stackTrace.append(ex.getMessage()).append("\n\n");
                for (StackTraceElement element : ex.getStackTrace()) {
                    stackTrace.append(element.toString()).append("\n");
                }
                model.addAttribute("trace", stackTrace.toString());
            }
        }

        // Phân loại trang lỗi theo mã trạng thái
        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());
            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                return "error";
            } else if (statusCode == HttpStatus.FORBIDDEN.value()) {
                return "error";
            }
        }
        
        return "error";
    }
} 