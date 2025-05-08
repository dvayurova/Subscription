package ru.webrise.subscription.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserRqDto {

    @NotBlank(message = "Поле name обязательно к заполнению")
    private String name;

    @NotBlank(message = "Поле email обязательно к заполнению")
    @Email(message = "Некорректный формат email")
    private String email;
}