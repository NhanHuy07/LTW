package com.example.demo.dto.request;


import com.example.demo.constants.ErrorMessage;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import java.math.BigDecimal;

@ToString
@Data
public class AccessoryRequest {

    Long id;

    @NotBlank(message = ErrorMessage.FILL_IN_THE_INPUT_FIELD)
    @Length(max = 255)
    private String name;

    private String description;

    @NotNull
    private BigDecimal price;

    @NotNull
    @Min(value = 1)
    private int stock;

    @NotBlank(message = ErrorMessage.FILL_IN_THE_INPUT_FIELD)
    @Length(max = 255)
    private String image;

    @NotBlank(message = ErrorMessage.FILL_IN_THE_INPUT_FIELD)
    @Length(max = 255)
    private String category;
}
