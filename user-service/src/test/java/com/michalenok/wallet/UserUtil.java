package com.michalenok.wallet;

import com.michalenok.wallet.model.constant.UserRole;
import com.michalenok.wallet.model.constant.UserStatus;
import com.michalenok.wallet.model.dto.request.UserCreateDto;
import com.michalenok.wallet.model.dto.response.UserInfoDto;
import com.michalenok.wallet.model.entity.UserEntity;
import java.time.Instant;
import java.util.Set;
import java.util.UUID;

public class UserUtil {

    public static UserInfoDto getUserInfoFormEntity(UserEntity userEntity){
        return new UserInfoDto(userEntity.getUuid(),
                userEntity.getMail(),
                userEntity.getMobilePhone(),
                userEntity.getRole(),
                userEntity.getStatus(),
                userEntity.getCreatedAt(),
                userEntity.getUpdatedAt());
    }

    public static UserCreateDto getUserCreateDto(String mail, String mobilePhone) {
        return UserCreateDto.builder()
                .status("ACTIVATED")
                .role(Set.of("USER"))
                .password("12345678")
                .mobilePhone(mobilePhone)
                .mail(mail)
                .build();
    }

    public static UserEntity getUserEntity(){
        return UserEntity.builder()
                .mail("testuser@user.com")
                .uuid(UUID.randomUUID())
                .password("testuser")
                .role(Set.of(UserRole.USER))
                .mobilePhone("12345678")
                .status(UserStatus.ACTIVATED)
                .updatedAt(Instant.now())
                .createdAt(Instant.now())
                .build();
    }

    public static UserInfoDto getUserInfo(UUID uuid, String mail){
        return new UserInfoDto(uuid,
                mail,
                "88888888",
                Set.of(UserRole.USER),
                UserStatus.ACTIVATED,
                Instant.now(),
                Instant.now());
    }
}