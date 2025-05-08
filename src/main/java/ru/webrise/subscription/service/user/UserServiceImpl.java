package ru.webrise.subscription.service.user;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.webrise.subscription.dto.SubscriptionRqDto;
import ru.webrise.subscription.dto.SubscriptionRsDto;
import ru.webrise.subscription.dto.UserRqDto;
import ru.webrise.subscription.dto.UserRsDto;
import ru.webrise.subscription.exceptions.*;
import ru.webrise.subscription.mapper.SubscriptionMapper;
import ru.webrise.subscription.mapper.UserMapper;
import ru.webrise.subscription.repository.SubscriptionRepository;
import ru.webrise.subscription.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;
    private final SubscriptionRepository subscriptionRepository;

    private final UserMapper mapper;
    private final SubscriptionMapper subscriptionMapper;

    @Override
    public UserRsDto createUser(UserRqDto dto) {
        try {
            var user = userRepository.save(mapper.map(dto));
            LOGGER.debug("Успешное сохранение пользователя c id: {}", user.getId());
            return mapper.mapToDto(user);
        } catch (Exception e) {
            throw new UserSavingException("Ошибка при создании пользователя: " + dto.getName(), e);
        }
    }

    @Override
    public UserRsDto getUser(Long id) {
        try {
            var user = userRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Пользователь с id %s не найден" + id));
            LOGGER.debug("Найден пользователь c id: {}", id);
            return mapper.mapToDto(user);
        } catch (Exception e) {
            throw new UserNotFoundException("Ошибка при получении пользователя: " + id, e);
        }
    }

    @Override
    public void deleteUser(Long id) {
        try {
            userRepository.deleteById(id);
            LOGGER.debug("Пользователь c id: {} удален", id);
        } catch (Exception e) {
            throw new UserDeletingException("Ошибка при удалении пользователя: " + id, e);
        }
    }

    @Override
    public UserRsDto updateUser(Long id, UserRqDto updatedUser) {
        try {
            var savedUser = userRepository.findById(id)
                    .map(user -> {
                        user.setName(updatedUser.getName());
                        user.setEmail(updatedUser.getEmail());
                        return userRepository.save(user);
                    })
                    .orElseThrow(() -> new RuntimeException("Пользователь с id %s не найден" + id));
            LOGGER.debug("Успешное обновление пользователя c id: {}", id);
            return mapper.mapToDto(savedUser);
        } catch (Exception e) {
            throw new UserSavingException("Ошибка при создании пользователя c id: " + id, e);
        }
    }

    @Override
    public List<SubscriptionRsDto> getUserSubscriptions(Long userId) {
        try {
            var subscriptions = userRepository.findById(userId)
                    .map(user -> List.copyOf(user.getSubscriptions()))
                    .orElseThrow();
            LOGGER.debug("Успешное получение подписок пользователя c id: {}", userId);
            return subscriptionMapper.mapToDto(subscriptions);
        } catch (Exception e) {
            throw new SubscriptionNotFoundException("Ошибка при получении популярных подписок", e);
        }
    }

    @Override
    public SubscriptionRsDto addSubscription(Long userId, SubscriptionRqDto dto) {
        try {
            var user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("Пользователь с id %s не найден" + userId));
            var subscription = subscriptionMapper.map(dto);
            subscription.setUser(user);
            var savedSubscription = subscriptionRepository.save(subscription);
            user.getSubscriptions().add(savedSubscription);
            userRepository.save(user);
            LOGGER.debug("Добавлена подписка на сервис: {} для пользователя c id: {}", dto.getName(), userId);
            return subscriptionMapper.mapToDto(savedSubscription);
        } catch (Exception e) {
            throw new SubscriptionChangingException(
                    String.format("Ошибка при добавлении подписки на сервис: %s для пользователя c id: %s", dto.getName(), userId),
                    e
            );
        }
    }

    @Override
    public void removeSubscription(Long userId, Long subId) {
        try {
            var user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("Пользователь с id %s не найден" + userId));
            var subscription = subscriptionRepository.findById(subId)
                    .orElseThrow(() -> new RuntimeException("Подписка с id %s не найдена" + subId));
            user.getSubscriptions().remove(subscription);
            userRepository.save(user);
            LOGGER.debug("Удалена подписка на сервис: {} для пользователя c id: {}", subscription.getName(), userId);
        } catch (Exception e) {
            throw new SubscriptionChangingException(
                    String.format("Ошибка при удалении подписки на сервис с id: %s для пользователя c id: %s", subId, userId),
                    e
            );
        }
    }
}
