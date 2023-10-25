package com.michalenok.wallet.service;

import com.michalenok.wallet.mapper.UserMapper;
import com.michalenok.wallet.model.constant.UserStatus;
import com.michalenok.wallet.model.dto.request.UserCreateDto;
import com.michalenok.wallet.model.dto.response.UserInfoDto;
import com.michalenok.wallet.model.entity.User;
import com.michalenok.wallet.model.exception.InvalidVersionException;
import com.michalenok.wallet.model.exception.UserAlreadyExistException;
import com.michalenok.wallet.model.exception.UserNotFoundException;
import com.michalenok.wallet.repository.api.UserRepository;
import com.michalenok.wallet.service.api.UserService;
import jakarta.persistence.OptimisticLockException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.time.Instant;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    @Transactional
    public UserInfoDto create(@Validated UserCreateDto userDto) {
        if (userRepository.existsByMail(userDto.mail()) || userRepository.existsByMobilePhone(userDto.mobilePhone())){
            throw new UserAlreadyExistException("User with mail {"+ userDto.mail()+" } or { " + userDto.mobilePhone() + " } already exist");
        }
        User user = userMapper.createDtoToUser(userDto);
        userRepository.save(user);
        return userMapper.toUserInfo(user);
        // если юзер, то при регистрации автоматически создаем ему кошелек - счет. Как один из возможных вариантов
    }

    @Override
    public UserInfoDto findById(UUID uuid) {
        User user = userRepository.findById(uuid).orElseThrow(
                () -> new UserNotFoundException("User with uuid " + uuid + " not found"));
        return userMapper.toUserInfo(user);
    }

    @Override
    @Transactional
    public UserInfoDto update(UUID uuid, Instant version, @Validated UserCreateDto userDto) {
        User user = userRepository.findById(uuid).orElseThrow(
                () -> new UserNotFoundException("User with uuid " + uuid + " not found"));
        if (user.getDtUpdate().toEpochMilli() != version.toEpochMilli()) {
            log.error("Can not update user " + uuid + "invalid version " + version);
            throw new InvalidVersionException("Invalid version");
        }
        user.setMail(userDto.mail());
        user.setMobilePhone(userDto.mobilePhone());
        user.setPassword(userDto.password());
        user.setRole(userDto.role());
        userRepository.save(user);
        return userMapper.toUserInfo(user);
    }

    @Override
    public Page<UserInfoDto> getPage(Pageable paging) {
        return userRepository.findAll(paging)
                .map(userMapper::toUserInfo);
    }

    @Override
    @Transactional
    public UserInfoDto changeStatus(UUID uuid, Instant version, @Validated UserStatus status) {
        User user = userRepository.findById(uuid).orElseThrow(
                () -> new UserNotFoundException("User with uuid " + uuid + " not found"));
        if (user.getDtUpdate().toEpochMilli() != version.toEpochMilli()) {
            log.error("Can not update user " + uuid + "invalid version " + version);
            throw new InvalidVersionException("Invalid version");
        }
        user.setStatus(status);
        return userMapper.toUserInfo(userRepository.save(user));
    }

    @Override
    public User findByMail(String mail) {
        return userRepository.findByMail(mail).orElseThrow(
                () -> new UserNotFoundException("User with mail " + mail + " not found"));
    }
}
