package com.example.demo.dto.request;

import com.example.demo.constants.ErrorMessage;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ChangePasswordRequest {

    @Size(min = 6, max = 16, message = ErrorMessage.PASSWORD_CHARACTER_LENGTH)
    private String password;

    @Size(min = 6, max = 16, message = ErrorMessage.PASSWORD2_CHARACTER_LENGTH)
    private String password2;
}