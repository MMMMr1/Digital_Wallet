package com.michalenok.wallet.keycloak;

import com.michalenok.wallet.model.dto.request.UserCreateDto;

public interface KeycloakService {
    String addUser(UserCreateDto userDTO);
}
