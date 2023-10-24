package com.michalenok.wallet.mapper;

import com.michalenok.wallet.model.dto.request.UserCreateDto;
import com.michalenok.wallet.model.dto.response.UserInfoDto;
import com.michalenok.wallet.model.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    UserInfoDto toUserInfo(User user);
    @Mapping(target = "uuid", expression = "java(java.util.UUID.randomUUID())")
    @Mapping(target = "dtCreate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "dtUpdate", expression = "java(java.time.Instant.now())")
    User createDtoToUser (UserCreateDto user);
}
