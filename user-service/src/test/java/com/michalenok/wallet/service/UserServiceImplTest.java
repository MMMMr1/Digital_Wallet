package com.michalenok.wallet.service;

import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import com.michalenok.wallet.feign.AccountServiceFeignClient;
import com.michalenok.wallet.keycloak.KeycloakService;
import com.michalenok.wallet.mapper.UserMapper;
import com.michalenok.wallet.model.constant.UserRole;
import com.michalenok.wallet.model.constant.UserStatus;
import com.michalenok.wallet.model.dto.request.UserCreateDto;
import com.michalenok.wallet.model.dto.response.UserInfoDto;
import com.michalenok.wallet.model.entity.UserEntity;
import com.michalenok.wallet.model.exception.UserAlreadyExistException;
import com.michalenok.wallet.model.exception.UserNotFoundException;
import com.michalenok.wallet.repository.api.UserRepository;
import com.michalenok.wallet.service.util.TimeGenerationUtil;
import com.michalenok.wallet.service.util.UuidUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.michalenok.wallet.service.UserUtil.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private AccountServiceFeignClient accountServiceFeignClient;
    @Mock
    private KeycloakService keycloakService;
    @Mock
    private UserMapper userMapper;
    @Mock
    private UuidUtil uuidUtil;
    @Mock
    private TimeGenerationUtil timeGenerationUtil;

    @Test
    void create_Successful() {
        UserCreateDto userCreateDto = getUserCreateDto("petrov@petrov.com", "11223344");
        UserEntity userEntity = UserEntity.builder()
                .mail(userCreateDto.mail())
                .mobilePhone(userCreateDto.mobilePhone())
                .role(Set.of(UserRole.USER))
                .status(UserStatus.valueOf(userCreateDto.status()))
                .build();

        when(userMapper.createDtoToUser(any(UserCreateDto.class)))
                .thenReturn(userEntity);
        when(userRepository.save(any(UserEntity.class)))
                .thenReturn(userEntity);
        when(userMapper.toUserInfo(any(UserEntity.class)))
                .thenReturn(getUserInfoFormEntity(userEntity));

        Mockito.when(keycloakService.addUser(any(UserCreateDto.class)))
                .thenReturn(UUID.randomUUID().toString());
        UserInfoDto userInfoDto = userService.create(userCreateDto);
        assertThat(userInfoDto)
                .hasFieldOrPropertyWithValue("mail", userInfoDto.mail())
                .hasFieldOrPropertyWithValue("mobilePhone", userInfoDto.mobilePhone())
                .hasFieldOrPropertyWithValue("status", userInfoDto.status())
                .hasFieldOrPropertyWithValue("role", userInfoDto.role())
                .hasFieldOrPropertyWithValue("createdAt", userInfoDto.createdAt())
                .hasFieldOrPropertyWithValue("updatedAt", userInfoDto.updatedAt());
    }

    @Test
    void create_withExistsMail_UserAlreadyExistsException() {
        UserCreateDto userCreateDto = getUserCreateDto("petrov@petrov.com", "11223344");

        when(userRepository.existsByMail(any(String.class)))
                .thenReturn(true);

        assertThrows(UserAlreadyExistException.class, () ->
                userService.create(userCreateDto));
    }

    @Test
    void create_withExistsMobilePhone_UserAlreadyExistsException() {
        UserCreateDto userCreateDto = getUserCreateDto("petrov@petrov.com", "11223344");

        when(userRepository.existsByMail(any(String.class)))
                .thenReturn(false);
        when(userRepository.existsByMobilePhone(any(String.class)))
                .thenReturn(true);

        assertThrows(UserAlreadyExistException.class, () ->
                userService.create(userCreateDto));
    }

    @Test
    void findById_Successful() {
        UUID uuid = UUID.randomUUID();
        String mail = "testuaser1@test.com";
        UserInfoDto userInfo = getUserInfo(uuid, mail);

        when(userRepository.findById(any(UUID.class)))
                .thenReturn(Optional.of(new UserEntity()));
        when(userMapper.toUserInfo(any(UserEntity.class)))
                .thenReturn(userInfo);

        UserInfoDto user = userService.findById(uuid);

        assertThat(user)
                .hasFieldOrPropertyWithValue("mail", user.mail())
                .hasFieldOrPropertyWithValue("mobilePhone", user.mobilePhone())
                .hasFieldOrPropertyWithValue("status", user.status())
                .hasFieldOrPropertyWithValue("role", user.role())
                .hasFieldOrPropertyWithValue("createdAt", user.createdAt())
                .hasFieldOrPropertyWithValue("updatedAt", user.updatedAt());
    }

    @Test
    void findById_ThrowsUserNotFoundException() {
        UUID uuid = UUID.randomUUID();

        when(userRepository.findById(any(UUID.class)))
                .thenThrow(new UserNotFoundException());

        assertThrows(UserNotFoundException.class, () ->
                userService.findById(uuid));
    }

    @Test
    void findByMail_Successful() {
        UUID uuid = UUID.randomUUID();
        String mail = "testuaser1@test.com";
        UserInfoDto userInfo = getUserInfo(uuid, mail);

        when(userRepository.findByMail(any(String.class)))
                .thenReturn(Optional.of(new UserEntity()));
        when(userMapper.toUserInfo(any(UserEntity.class)))
                .thenReturn(userInfo);

        UserInfoDto userByMail = userService.findByMail(mail);

        assertThat(userByMail)
                .hasFieldOrPropertyWithValue("mail", userByMail.mail())
                .hasFieldOrPropertyWithValue("mobilePhone", userByMail.mobilePhone())
                .hasFieldOrPropertyWithValue("status", userByMail.status())
                .hasFieldOrPropertyWithValue("role", userByMail.role())
                .hasFieldOrPropertyWithValue("createdAt", userByMail.createdAt())
                .hasFieldOrPropertyWithValue("updatedAt", userByMail.updatedAt());
    }

    @Test
    void findByMail_ThrowsUserNotFoundException() {
        String mail = "testuaser1@test.com";

        when(userRepository.findByMail(any(String.class)))
                .thenThrow(new UserNotFoundException());

        assertThrows(UserNotFoundException.class, () ->
                userService.findByMail(mail));
    }
}
