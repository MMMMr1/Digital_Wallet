package com.michalenok.wallet.service.api;

import com.michalenok.wallet.model.constant.UserStatus;
import com.michalenok.wallet.model.dto.request.UserCreateDto;
import com.michalenok.wallet.model.dto.response.UserInfoDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.UUID;

public interface UserService {
    UserInfoDto create(UserCreateDto user);
    UserInfoDto update(UUID uuid, UserCreateDto user);
    Page<UserInfoDto> getPage(Pageable paging);
    UserInfoDto findById(UUID uuid);
    UserInfoDto findByMail(String mail);
    UserInfoDto changeStatus(UUID uuid, UserStatus status);
}
