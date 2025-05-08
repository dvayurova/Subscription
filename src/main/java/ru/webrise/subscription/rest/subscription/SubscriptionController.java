package ru.webrise.subscription.rest.subscription;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.webrise.subscription.dto.SubscriptionStatisticDto;

import java.util.List;

@RequestMapping("/subscriptions")
public interface SubscriptionController {

    @GetMapping("/top")
    ResponseEntity<List<SubscriptionStatisticDto>> getTopSubscriptions();
}