package com.michalenok.wallet.service;

import com.michalenok.wallet.mapper.UserMapper;
import com.michalenok.wallet.model.constant.UserStatus;
import com.michalenok.wallet.model.dto.request.UserCreateDto;
import com.michalenok.wallet.model.dto.response.UserInfoDto;
import com.michalenok.wallet.model.entity.UserEntity;
import com.michalenok.wallet.model.exception.UserAlreadyExistException;
import com.michalenok.wallet.model.exception.UserNotFoundException;
import com.michalenok.wallet.repository.api.UserRepository;
import com.michalenok.wallet.service.api.UserService;
import com.michalenok.wallet.service.util.TimeGenerationUtil;
import com.michalenok.wallet.service.util.UuidUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Log4j2
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final TimeGenerationUtil timeGenerationUtil;
    private final UuidUtil uuidUtil;

    @Override
    @Transactional
    public UserInfoDto create(UserCreateDto userDto) {
        isUserExists(userDto);
        UserEntity user = userMapper.createDtoToUser(userDto);
        initializeNewUser(user);
        return userMapper.toUserInfo(userRepository.save(user));
    }

    @Override
    public UserInfoDto findById(UUID uuid) {
        return userMapper.toUserInfo(getUserById(uuid));
    }

    @Override
    @Transactional
    public UserInfoDto update(UUID uuid, UserCreateDto userDto) {
        UserEntity user = getUserById(uuid);
        userMapper.updateUserEntity(user, userDto);
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
    public UserInfoDto changeStatus(UUID uuid, UserStatus status) {
        return userRepository.findById(uuid)
                .map(user -> {
                    user.setStatus(status);
                    return user;
                })
                .map(userRepository::save)
                .map(userMapper::toUserInfo)
                .orElseThrow(
                        () -> new UserNotFoundException(String.format("User with uuid {%s} not found", uuid)));
    }

    @Override
    public UserInfoDto findByMail(String mail) {
        return userRepository.findByMail(mail)
                .map(userMapper::toUserInfo)
                .orElseThrow(() ->
                        new UserNotFoundException(String.format("User with mail {%s} not found", mail)));
    }

    private void isUserExists(UserCreateDto userDto) {
        if (userRepository.existsByMail(userDto.mail())) {
            log.info("User with mail {} already exist", userDto.mail());
            throw new UserAlreadyExistException(String.format("User with mail {%s} already exist", userDto.mail()));
        }
        if (userRepository.existsByMobilePhone(userDto.mobilePhone())) {
            log.info("User with mobile phone {} already exist", userDto.mobilePhone());
            throw new UserAlreadyExistException(String.format("User with mobile phone {%s} already exist", userDto.mobilePhone()));
        }
    }

    private UserEntity getUserById(UUID uuid) {
        return userRepository.findById(uuid)
                .orElseThrow(() ->
                        new UserNotFoundException(String.format("User with uuid {%s} not found", uuid)));
    }

    private void initializeNewUser(UserEntity user) {
        Instant instant = timeGenerationUtil.generateCurrentInstant();
        user.setUuid(uuidUtil.generateUuid());
        user.setCreatedAt(instant);
        user.setUpdatedAt(instant);
        log.info("initialize user with mail {}: uuid {}, createdAt {}, updatedAt {}",
                user.getMail(), user.getUuid(), user.getCreatedAt(), user.getUpdatedAt());
    }
}
