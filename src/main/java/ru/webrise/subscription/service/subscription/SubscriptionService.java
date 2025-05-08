package ru.webrise.subscription.service.subscription;

import ru.webrise.subscription.dto.SubscriptionStatisticDto;

import java.util.List;

public interface SubscriptionService {

    List<SubscriptionStatisticDto> getTopSubscriptions();
}
