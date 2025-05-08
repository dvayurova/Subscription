package ru.webrise.subscription.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.webrise.subscription.dto.SubscriptionRqDto;
import ru.webrise.subscription.dto.SubscriptionRsDto;
import ru.webrise.subscription.model.Subscription;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SubscriptionMapper {

    @Mapping(target = "user", ignore = true)
    Subscription map(SubscriptionRqDto dto);
    SubscriptionRsDto mapToDto(Subscription subscription);
    List<SubscriptionRsDto> mapToDto(List<Subscription> subscriptions);
}
