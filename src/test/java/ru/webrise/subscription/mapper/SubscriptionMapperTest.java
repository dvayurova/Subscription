package ru.webrise.subscription.mapper;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import ru.webrise.subscription.dto.SubscriptionRqDto;
import ru.webrise.subscription.dto.SubscriptionRsDto;
import ru.webrise.subscription.model.Subscription;
import ru.webrise.subscription.model.User;

import java.util.List;

class SubscriptionMapperTest {

    private final SubscriptionMapper mapper = Mappers.getMapper(SubscriptionMapper.class);

    @Test
    void map_subscriptionDto_toSubscription() {
        SubscriptionRqDto dto = new SubscriptionRqDto();
        dto.setName("Premium");

        Subscription result = mapper.map(dto);

        Assertions.assertThat(result)
                .isNotNull()
                .satisfies(sub -> {
                    Assertions.assertThat(sub.getName()).isEqualTo("Premium");
                    Assertions.assertThat(sub.getUser()).isNull();
                });
    }

    @Test
    void map_subscription_toSubscriptionDto() {
        Subscription subscription = new Subscription();
        subscription.setId(2L);
        subscription.setName("Basic");
        subscription.setUser(new User());

        SubscriptionRsDto result = mapper.mapToDto(subscription);

        Assertions.assertThat(result)
                .isNotNull()
                .satisfies(dto -> {
                    Assertions.assertThat(dto.getId()).isEqualTo(2L);
                    Assertions.assertThat(dto.getName()).isEqualTo("Basic");
                });
    }

    @Test
    void map_subscriptionList_toDtoList() {
        Subscription sub1 = new Subscription();
        sub1.setId(1L);
        Subscription sub2 = new Subscription();
        sub2.setId(2L);
        List<Subscription> list = List.of(sub1, sub2);

        List<SubscriptionRsDto> result = mapper.mapToDto(list);

        Assertions.assertThat(result)
                .hasSize(2)
                .extracting(SubscriptionRsDto::getId)
                .containsExactly(1L, 2L);
    }
}