package ru.webrise.subscription.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubscriptionRqDto {
    @NotBlank(message = "Поле name обязательно к заполнению")
    private String name;
}
