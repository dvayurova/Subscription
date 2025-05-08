package ru.webrise.subscription.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SubscriptionStatisticDto {
    private String name;
    private int count;
}
