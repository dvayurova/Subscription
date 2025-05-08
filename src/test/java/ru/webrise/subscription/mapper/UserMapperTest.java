package ru.webrise.subscription.mapper;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import ru.webrise.subscription.dto.UserRqDto;
import ru.webrise.subscription.dto.UserRsDto;
import ru.webrise.subscription.model.Subscription;
import ru.webrise.subscription.model.User;

class UserMapperTest {
    private final UserMapper mapper = Mappers.getMapper(UserMapper.class);

    @Test
    void map_userDto_toUser() {
        UserRqDto dto = new UserRqDto();
        dto.setName("Alice");
        dto.setEmail("alice@example.com");

        User result = mapper.map(dto);

        Assertions.assertThat(result)
                .isNotNull()
                .satisfies(user -> {
                    Assertions.assertThat(user.getName()).isEqualTo("Alice");
                    Assertions.assertThat(user.getEmail()).isEqualTo("alice@example.com");
                    Assertions.assertThat(user.getSubscriptions()).isEmpty();
                });
    }

    @Test
    void map_user_toUserDto() {
        User user = new User();
        user.setId(20L);
        user.setName("Bob");
        user.setEmail("bob@test.com");
        user.getSubscriptions().add(new Subscription());

        UserRsDto result = mapper.mapToDto(user);

        Assertions.assertThat(result)
                .isNotNull()
                .satisfies(dto -> {
                    Assertions.assertThat(dto.getId()).isEqualTo(20L);
                    Assertions.assertThat(dto.getName()).isEqualTo("Bob");
                    Assertions.assertThat(dto.getEmail()).isEqualTo("bob@test.com");
                });
    }

}