package ru.webrise.subscription.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.webrise.subscription.dto.UserRqDto;
import ru.webrise.subscription.dto.UserRsDto;
import ru.webrise.subscription.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "subscriptions", ignore = true)
    User map(UserRqDto dto);

    UserRsDto mapToDto(User user);
}
