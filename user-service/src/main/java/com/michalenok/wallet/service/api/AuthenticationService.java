package com.michalenok.wallet.service.api;

import com.michalenok.wallet.model.dto.request.UserRegistrationDto;

public interface AuthenticationService {
    void register(UserRegistrationDto user);
    void verifyUser(String code, String mail);
}
