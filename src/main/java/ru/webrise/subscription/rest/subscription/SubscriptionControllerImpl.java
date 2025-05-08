package ru.webrise.subscription.rest.subscription;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.webrise.subscription.dto.SubscriptionStatisticDto;
import ru.webrise.subscription.service.subscription.SubscriptionService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SubscriptionControllerImpl implements SubscriptionController {

    private final SubscriptionService subscriptionService;

    @Override
    public ResponseEntity<List<SubscriptionStatisticDto>> getTopSubscriptions() {
        return ResponseEntity.ok(subscriptionService.getTopSubscriptions());
    }
}
