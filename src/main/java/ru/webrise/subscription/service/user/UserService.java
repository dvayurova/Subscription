package ru.webrise.subscription.service.user;

import ru.webrise.subscription.dto.SubscriptionRqDto;
import ru.webrise.subscription.dto.SubscriptionRsDto;
import ru.webrise.subscription.dto.UserRqDto;
import ru.webrise.subscription.dto.UserRsDto;

import java.util.List;

public interface UserService {

    UserRsDto createUser(UserRqDto dto);

    UserRsDto getUser(Long id);

    void deleteUser(Long id);

    UserRsDto updateUser(Long id, UserRqDto updatedUser);

    List<SubscriptionRsDto> getUserSubscriptions(Long userId);

    SubscriptionRsDto addSubscription(Long userId, SubscriptionRqDto subscription);

    void removeSubscription(Long userId, Long subId);
}
