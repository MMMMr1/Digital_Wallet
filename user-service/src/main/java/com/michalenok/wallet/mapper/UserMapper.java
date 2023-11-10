package com.michalenok.wallet.mapper;

import com.michalenok.wallet.model.constant.UserRole;
import com.michalenok.wallet.model.constant.UserStatus;
import com.michalenok.wallet.model.dto.request.UserCreateDto;
import com.michalenok.wallet.model.dto.request.UserRegistrationDto;
import com.michalenok.wallet.model.dto.response.UserInfoDto;
import com.michalenok.wallet.model.entity.UserEntity;
import org.mapstruct.*;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    UserInfoDto toUserInfo(UserEntity user);

    @InheritInverseConfiguration
    UserEntity toUserEntity(UserInfoDto user);

    @Mapping(target = "role", expression = "java(rolesToUserRole(userCreateDto.role()))")
    @Mapping(target = "status", expression = "java(statusToUserStatus(userCreateDto.status()))")
    void updateUserEntity(@MappingTarget UserEntity userEntity, UserCreateDto userCreateDto);

    @Mapping(target = "status", expression = "java(UserStatus.WAITING_ACTIVATION.name())")
    @Mapping(target = "role", expression = "java(Set.of(UserRole.USER.name()))")
    UserCreateDto userRegistrationDtoToUserCreateDto(UserRegistrationDto userRegistrationDto);

    @Mapping(target = "uuid", ignore = true)
    @Mapping(target = "role", expression = "java(rolesToUserRole(user.role()))")
    @Mapping(target = "status", expression = "java(statusToUserStatus(user.status()))")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    UserEntity createDtoToUser (UserCreateDto user);

    default UserStatus statusToUserStatus(String status) {
        return UserStatus.valueOf(status);
    }

    default Set<UserRole> rolesToUserRole(Set<String> role) {
        return role.stream()
                .map(UserRole::valueOf)
                .collect(Collectors.toSet());
    }
}
