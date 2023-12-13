package com.michalenok.wallet.integration;

import com.michalenok.wallet.keycloak.KeycloakService;
import com.michalenok.wallet.model.dto.request.UserCreateDto;
import com.michalenok.wallet.model.dto.response.UserInfoDto;
import com.michalenok.wallet.model.exception.UserAlreadyExistException;
import com.michalenok.wallet.model.exception.UserNotFoundException;
import com.michalenok.wallet.service.api.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Pageable;

import java.util.Set;
import java.util.UUID;

import static com.michalenok.wallet.model.constant.UserStatus.DEACTIVATED;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

@Profile("test")
class UserServiceIntegrationImplTest extends IntegrationTestBase {
    @Autowired
    private UserService userService;
    @MockBean
    private KeycloakService keycloakService;

    @Test
    void create_Successful() {
        Mockito.when(keycloakService.addUser(any(UserCreateDto.class)))
                .thenReturn(UUID.randomUUID().toString());
        UserInfoDto userInfoDto = userService.create(getUserCreateDto("dmitryd@dmitry.com", "2345678"));
        assertEquals("dmitryd@dmitry.com", userInfoDto.mail());
    }

    @Test
    void createDuplicateMail_ThrowUserAlreadyExistException() {
        assertThrows(UserAlreadyExistException.class, () -> userService.create(getUserCreateDto("testuaser1@test.com", "2234561")));
    }

    @Test
    void createDuplicatePhoneNumber_ThrowUserAlreadyExistException() {
        assertThrows(UserAlreadyExistException.class, () -> userService.create(getUserCreateDto("petrov1@mail.com", "12345678")));
    }

    @Test
    void findById_Successful() {
        UUID uuid = UUID.fromString("ad16c450-386f-427b-a5e5-763577425e5d");
        assertEquals("testuaser1@test.com", userService.findById(uuid).mail());
    }

    @Test
    void findById_ThrowUserNotFoundException() {
        UUID uuid = UUID.fromString("6d8df286-5ad8-40ed-8fc6-b236c1403536");
        assertThrows(UserNotFoundException.class, () -> userService.findById(uuid));
    }

    @Test
    void update_Successful() {
        UUID uuid = UUID.fromString("ad16c450-386f-427b-a5e5-763577425e5d");
        UserInfoDto updatedUser = userService.update(uuid, UserCreateDto.builder()
                .mobilePhone("88888888")
                .mail("testuaser1@test.com")
                .password("12345678")
                .status("ACTIVATED")
                .role(Set.of("USER"))
                .build());
        assertEquals("88888888", updatedUser.mobilePhone());
    }

    @Test
    void update_UserNotFoundException() {
        UUID uuid = UUID.fromString("6d8df286-5ad8-40ed-8fc6-b236c1403536");
        assertThrows(UserNotFoundException.class, () ->
                userService.update(uuid, UserCreateDto.builder()
                        .mobilePhone("88888888")
                        .mail("testuaser1@test.com")
                        .password("12345678")
                        .status("ACTIVATED")
                        .role(Set.of("USER"))
                        .build()));
    }

    @Test
    void getPage_Successful() {
        assertEquals(3, userService.getPage(Pageable.unpaged()).getTotalElements());
    }

    @Test
    void changeStatus_Successful() {
        UUID uuid = UUID.fromString("ad16c450-386f-427b-a5e5-763577425e5d");
        UserInfoDto userInfoDto = userService.changeStatus(uuid, DEACTIVATED);
        assertEquals(DEACTIVATED, userInfoDto.status());
    }

    @Test
    void findByMail_Successful() {
        UserInfoDto user = userService.findByMail("testuaser1@test.com");
        assertEquals(UUID.fromString("ad16c450-386f-427b-a5e5-763577425e5d"), user.uuid());
    }

    private UserCreateDto getUserCreateDto(String mail, String mobilePhone) {
        return UserCreateDto.builder()
                .status("ACTIVATED")
                .role(Set.of("USER"))
                .password("12345678")
                .mobilePhone(mobilePhone)
                .mail(mail)
                .build();
    }
}