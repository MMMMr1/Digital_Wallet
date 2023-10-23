package com.michalenok.wallet.service.api;

import com.michalenok.wallet.model.dto.request.UserLoginDto;
import com.michalenok.wallet.model.dto.request.UserRegistrationDto;
import com.michalenok.wallet.model.entity.User;

public interface AuthenticationService {
    void register(UserRegistrationDto user);
    void verify(String code,String mail);
    void login(UserLoginDto user);
    User getUser(String mail);
}
