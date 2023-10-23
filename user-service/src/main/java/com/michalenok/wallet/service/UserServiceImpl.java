package com.michalenok.wallet.service;

import com.michalenok.wallet.mapper.UserMapper;
import com.michalenok.wallet.model.constant.UserRole;
import com.michalenok.wallet.model.constant.UserStatus;
import com.michalenok.wallet.model.dto.request.UserCreateDto;
import com.michalenok.wallet.model.dto.response.UserInfoDto;
import com.michalenok.wallet.model.entity.User;
import com.michalenok.wallet.repository.api.UserRepository;
import com.michalenok.wallet.service.api.UserService;
import jakarta.persistence.OptimisticLockException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    @Override
    public UserInfoDto create(UserCreateDto userDto) {
        User user = userRepository.save(User.builder()
                .uuid(UUID.randomUUID())
                .mail(userDto.getMail())
                .mobilePhone(userDto.getMobilePhone())
                .password(userDto.getPassword())
                .role(userDto.getRole() == null ? UserRole.USER : userDto.getRole())
                .status(userDto.getStatus() == null ? UserStatus.WAITING_ACTIVATION : userDto.getStatus())
                .dtCreate(Instant.now())
                .dtUpdate(Instant.now())
                .build());
        return userMapper.map(user);
        // если юзер, то при регистрации автоматически создаем ему кошелек - счет. Как один из возможных вариантов
    }
    @Override
    public UserInfoDto findById(UUID uuid) {
        User user= userRepository.findById(uuid).orElseThrow(
                () -> new RuntimeException("User with uuid " + uuid + " not found"));
        return userMapper.map(user);
    }
    @Override
    public UserInfoDto update(UUID uuid, Instant version, UserCreateDto userDto) {
        User user = userRepository.findById(uuid).orElseThrow(
                () -> new RuntimeException("User with uuid " + uuid + " not found"));
        if(user.getDtUpdate().toEpochMilli() != version.toEpochMilli()){
            log.error("Can not update user " + uuid + "invalid version " + version);
            throw new OptimisticLockException("Invalid version");
        }
        user.setMail(userDto.getMail());
        user.setMobilePhone(userDto.getMobilePhone());
        user.setPassword(userDto.getPassword());
        user.setRole(userDto.getRole());
        userRepository.save(user);
        return userMapper.map(user);
    }
    @Override
    public Page<UserInfoDto> getPage(Pageable paging) {
        return userRepository.findAll(paging)
                .map(userMapper::map);
    }
    @Override
    public UserInfoDto changeStatus(UUID uuid, Instant version, UserStatus status) {
        User user = userRepository.findById(uuid).orElseThrow(
                () -> new RuntimeException("User with uuid " + uuid + " not found"));
        if(user.getDtUpdate().toEpochMilli() != version.toEpochMilli()){
            log.error("Can not update user " + uuid + "invalid version " + version);
            throw new OptimisticLockException("Invalid version");
        }
        user.setStatus(status);
        return userMapper.map(userRepository.save(user));
    }
    @Override
    public User findByMail(String mail) {
        return userRepository.findByMail(mail).orElseThrow(
                () -> new RuntimeException("User with mail " + mail + " not found"));
    }
}
