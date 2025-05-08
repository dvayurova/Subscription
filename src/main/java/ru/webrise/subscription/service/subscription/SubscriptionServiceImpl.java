package ru.webrise.subscription.service.subscription;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.webrise.subscription.dto.SubscriptionStatisticDto;
import ru.webrise.subscription.exceptions.SubscriptionNotFoundException;
import ru.webrise.subscription.repository.SubscriptionRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SubscriptionServiceImpl.class);
    private final SubscriptionRepository subscriptionRepository;

    @Override
    public List<SubscriptionStatisticDto> getTopSubscriptions() {
        try {
            var subscriptions = subscriptionRepository.findTop3PopularSubscriptions().stream()
                    .map(p -> new SubscriptionStatisticDto(p.getName(), p.getCount()))
                    .toList();
            LOGGER.debug("Найдены популярные подписки");
            return subscriptions;
        } catch (Exception e) {
            throw new SubscriptionNotFoundException("Ошибка при получении популярных подписок", e);
        }
    }
}

