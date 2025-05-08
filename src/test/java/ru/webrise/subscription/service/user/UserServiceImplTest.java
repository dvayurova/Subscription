package ru.webrise.subscription.service.user;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.webrise.subscription.dto.SubscriptionRqDto;
import ru.webrise.subscription.dto.SubscriptionRsDto;
import ru.webrise.subscription.dto.UserRqDto;
import ru.webrise.subscription.dto.UserRsDto;
import ru.webrise.subscription.exceptions.UserDeletingException;
import ru.webrise.subscription.exceptions.UserNotFoundException;
import ru.webrise.subscription.exceptions.UserSavingException;
import ru.webrise.subscription.mapper.SubscriptionMapper;
import ru.webrise.subscription.mapper.UserMapper;
import ru.webrise.subscription.model.Subscription;
import ru.webrise.subscription.model.User;
import ru.webrise.subscription.repository.SubscriptionRepository;
import ru.webrise.subscription.repository.UserRepository;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private SubscriptionRepository subscriptionRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private SubscriptionMapper subscriptionMapper;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void createUser_shouldSaveAndReturnDto() {
        // Arrange
        UserRqDto inputDto = new UserRqDto();
        User entity = new User();
        User savedEntity = new User();
        UserRsDto expectedDto = new UserRsDto();

        Mockito.when(userMapper.map(inputDto)).thenReturn(entity);
        Mockito.when(userRepository.save(entity)).thenReturn(savedEntity);
        Mockito.when(userMapper.mapToDto(savedEntity)).thenReturn(expectedDto);

        // Act
        UserRsDto result = userService.createUser(inputDto);

        // Assert
        Assertions.assertThat(result).isEqualTo(expectedDto);
        Mockito.verify(userRepository).save(entity);
    }

    @Test
    void createUser_shouldThrowOnSaveError() {
        // Arrange
        UserRqDto inputDto = new UserRqDto();
        Mockito.when(userMapper.map(inputDto)).thenThrow(new RuntimeException("Ошибка маппинга"));

        // Act & Assert
        Assertions.assertThatThrownBy(() -> userService.createUser(inputDto))
                .isInstanceOf(UserSavingException.class)
                .hasMessageContaining("Ошибка при создании пользователя");
    }

    // Тесты для getUser
    @Test
    void getUser_shouldReturnDtoWhenExists() {
        // Arrange
        Long userId = 1L;
        User user = new User();
        UserRsDto expectedDto = new UserRsDto();

        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        Mockito.when(userMapper.mapToDto(user)).thenReturn(expectedDto);

        // Act
        UserRsDto result = userService.getUser(userId);

        // Assert
        Assertions.assertThat(result).isEqualTo(expectedDto);
    }

    @Test
    void getUser_shouldThrowWhenNotFound() {
        // Arrange
        Long userId = 999L;
        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        Assertions.assertThatThrownBy(() -> userService.getUser(userId))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessageContaining(userId.toString());
    }

    // Тесты для deleteUser
    @Test
    void deleteUser_shouldCallRepository() {
        // Arrange
        Long userId = 1L;

        // Act
        userService.deleteUser(userId);

        // Assert
        Mockito.verify(userRepository).deleteById(userId);
    }

    @Test
    void deleteUser_shouldThrowOnError() {
        // Arrange
        Long userId = 1L;
        Mockito.doThrow(new RuntimeException("Ошибка при удалении")).when(userRepository).deleteById(userId);

        // Act & Assert
        Assertions.assertThatThrownBy(() -> userService.deleteUser(userId))
                .isInstanceOf(UserDeletingException.class)
                .hasMessageContaining(userId.toString());
    }

    // Тесты для updateUser
    @Test
    void updateUser_shouldUpdateExistingUser() {
        // Arrange
        Long userId = 1L;
        UserRqDto updateDto = new UserRqDto();
        updateDto.setName("New Name");
        updateDto.setEmail("new@email.com");

        User existingUser = new User();
        User savedUser = new User();
        UserRsDto expectedDto = new UserRsDto();

        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        Mockito.when(userRepository.save(existingUser)).thenReturn(savedUser);
        Mockito.when(userMapper.mapToDto(savedUser)).thenReturn(expectedDto);

        // Act
        UserRsDto result = userService.updateUser(userId, updateDto);

        // Assert
        Assertions.assertThat(result).isEqualTo(expectedDto);
        Assertions.assertThat(existingUser.getName()).isEqualTo("New Name");
        Assertions.assertThat(existingUser.getEmail()).isEqualTo("new@email.com");
    }

    // Тесты для подписок
    @Test
    void addSubscription_shouldAddToUser() {
        // Arrange
        Long userId = 1L;
        SubscriptionRqDto dto = new SubscriptionRqDto();
        SubscriptionRsDto rsDto = new SubscriptionRsDto();
        Subscription subscription = new Subscription();
        User user = new User();

        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        Mockito.when(subscriptionMapper.map(dto)).thenReturn(subscription);
        Mockito.when(subscriptionRepository.save(subscription)).thenReturn(subscription);
        Mockito.when(subscriptionMapper.mapToDto(subscription)).thenReturn(rsDto);

        // Act
        SubscriptionRsDto result = userService.addSubscription(userId, dto);

        // Assert
        Assertions.assertThat(result).isEqualTo(rsDto);
        Assertions.assertThat(user.getSubscriptions()).contains(subscription);
        Mockito.verify(userRepository).save(user);
    }

    @Test
    void removeSubscription_shouldRemoveFromUser() {
        // Arrange
        Long userId = 1L;
        Long subId = 2L;
        User user = new User();
        Subscription subscription = new Subscription();
        user.getSubscriptions().add(subscription);

        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        Mockito.when(subscriptionRepository.findById(subId)).thenReturn(Optional.of(subscription));

        // Act
        userService.removeSubscription(userId, subId);

        // Assert
        Assertions.assertThat(user.getSubscriptions()).doesNotContain(subscription);
        Mockito.verify(userRepository).save(user);
    }
}