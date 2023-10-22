package com.michalenok.wallet.service.api;

import com.michalenok.wallet.model.dto.request.UserCreateDto;
import com.michalenok.wallet.model.dto.response.UserInfoDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.Instant;
import java.util.UUID;

public interface UserService {
    UUID create(UserCreateDto user);
    UserInfoDto findById(UUID uuid);
    UserInfoDto update(UUID uuid, Instant version, UserCreateDto user);
    Page<UserInfoDto> getAll(Pageable paging);
}
