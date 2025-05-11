package com.example.demo.dto.request;

import com.example.demo.constants.ErrorMessage;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;



@Data
@ToString
public class ProductRequest {
    private Long id;

    @NotBlank(message = ErrorMessage.FILL_IN_THE_INPUT_FIELD)
    @Length(max = 255)
    private String carName;

    @NotBlank(message = ErrorMessage.FILL_IN_THE_INPUT_FIELD)
    @Length(max = 255)
    private String brand;

    @NotBlank(message = ErrorMessage.FILL_IN_THE_INPUT_FIELD)
    @Length(max = 255)
    private String mileage;

    @NotBlank(message = ErrorMessage.FILL_IN_THE_INPUT_FIELD)
    @Length(max = 255)
    private String fuel;

    @NotBlank(message = ErrorMessage.FILL_IN_THE_INPUT_FIELD)
    @Length(max = 255)
    private String type;

    @NotBlank(message = ErrorMessage.FILL_IN_THE_INPUT_FIELD)
    @Length(max = 255)
    private String doors;

    @NotBlank(message = ErrorMessage.FILL_IN_THE_INPUT_FIELD)
    @Length(max = 255)
    private String gearbox;

    @NotBlank(message = ErrorMessage.FILL_IN_THE_INPUT_FIELD)
    @Length(max = 255)
    private String fileName;

    @NotNull(message = ErrorMessage.FILL_IN_THE_INPUT_FIELD)
    @Min(value = 1, message = ErrorMessage.FILL_IN_THE_INPUT_FIELD)
    private Long price;
}
