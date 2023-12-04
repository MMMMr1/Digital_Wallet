package com.michalenok.wallet.keycloak;

import com.michalenok.wallet.model.dto.request.UserCreateDto;
import org.keycloak.representations.idm.UserRepresentation;
import java.util.List;

public interface KeycloakService {
    void addUser(UserCreateDto userDTO);

    List<UserRepresentation> getUser(String userName);

    void updateUser(String userId, UserCreateDto userDTO);

    void deleteUser(String userId);

    void sendVerificationLink(String userId);

    void sendResetPassword(String userId);
}
