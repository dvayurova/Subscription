package ru.webrise.subscription.rest.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.webrise.subscription.dto.SubscriptionRqDto;
import ru.webrise.subscription.dto.SubscriptionRsDto;
import ru.webrise.subscription.dto.UserRqDto;
import ru.webrise.subscription.dto.UserRsDto;
import ru.webrise.subscription.service.user.UserService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserControllerImpl implements UserController {

    private final UserService userService;

    @Override
    public ResponseEntity<UserRsDto> createUser(UserRqDto user) {
        return ResponseEntity.ok(userService.createUser(user));
    }

    @Override
    public ResponseEntity<UserRsDto> getUser(Long id) {
        return ResponseEntity.ok(userService.getUser(id));
    }

    @Override
    public ResponseEntity<UserRsDto> updateUser(Long id, UserRqDto user) {
        return ResponseEntity.ok(userService.updateUser(id, user));
    }

    @Override
    public ResponseEntity<Void> delete(Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<SubscriptionRsDto> addSubscription(Long id, SubscriptionRqDto subscription) {
        return ResponseEntity.ok(userService.addSubscription(id, subscription));
    }

    @Override
    public ResponseEntity<List<SubscriptionRsDto>> getSubscriptions(Long id) {
        return ResponseEntity.ok(userService.getUserSubscriptions(id));
    }

    @Override
    public ResponseEntity<Void> deleteSubscription(Long id, Long subId) {
        userService.removeSubscription(id, subId);
        return ResponseEntity.noContent().build();
    }
}
