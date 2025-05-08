package ru.webrise.subscription.rest.user;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.webrise.subscription.dto.SubscriptionRqDto;
import ru.webrise.subscription.dto.SubscriptionRsDto;
import ru.webrise.subscription.dto.UserRqDto;
import ru.webrise.subscription.dto.UserRsDto;

import java.util.List;

@RequestMapping("/users")
public interface UserController {

    @PostMapping
    ResponseEntity<UserRsDto> createUser(@Valid @RequestBody UserRqDto user);

    @GetMapping("/{id}")
    ResponseEntity<UserRsDto> getUser(@PathVariable Long id);

    @PutMapping("/{id}")
    ResponseEntity<UserRsDto> updateUser(@PathVariable Long id, @Valid @RequestBody UserRqDto user);

    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable Long id);

    @PostMapping("/{id}/subscriptions")
    ResponseEntity<SubscriptionRsDto> addSubscription(@PathVariable Long id, @Valid @RequestBody SubscriptionRqDto subscription);

    @GetMapping("/{id}/subscriptions")
    ResponseEntity<List<SubscriptionRsDto>> getSubscriptions(@PathVariable Long id);

    @DeleteMapping("/{id}/subscriptions/{subId}")
    ResponseEntity<Void> deleteSubscription(@PathVariable Long id, @PathVariable Long subId);
}
