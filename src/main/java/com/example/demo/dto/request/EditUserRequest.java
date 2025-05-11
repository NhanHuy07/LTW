package com.example.demo.dto.request;

import com.example.demo.constants.ErrorMessage;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EditUserRequest {

    @NotBlank(message = ErrorMessage.EMPTY_FIRST_NAME)
    private String name;
    private String phoneNumber;
    private String address;
}
