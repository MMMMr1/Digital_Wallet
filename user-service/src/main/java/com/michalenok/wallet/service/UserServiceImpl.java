package com.michalenok.wallet.service;

import com.michalenok.wallet.mapper.UserMapper;
import com.michalenok.wallet.model.dto.request.UserCreateDto;
import com.michalenok.wallet.model.dto.response.UserInfoDto;
import com.michalenok.wallet.model.entity.User;
import com.michalenok.wallet.repository.api.UserRepository;
import com.michalenok.wallet.service.api.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    @Override
    public UUID create(UserCreateDto userDto) {
        return userRepository.save(User.builder()
                .uuid(UUID.randomUUID())
                .mail(userDto.getMail())
                .mobilePhone(userDto.getMobilePhone())
                .password(userDto.getPassword())
                .role(userDto.getRole())
                .status(userDto.getStatus())
                .dtCreate(Instant.now())
                .dtUpdate(Instant.now())
                .build()).getUuid();
    }

    @Override
    public UserInfoDto findById(UUID uuid) {
        return userRepository.findById(uuid).stream()
                .map(userMapper::map)
                .findFirst().get();
    }

    @Override
    public UserInfoDto update(UUID id, Instant version, UserCreateDto user) {
        return null;
    }

    @Override
    public Page<UserInfoDto> getAll(Pageable paging) {
        return userRepository.findAll(paging)
                .map(userMapper::map);
    }
}
