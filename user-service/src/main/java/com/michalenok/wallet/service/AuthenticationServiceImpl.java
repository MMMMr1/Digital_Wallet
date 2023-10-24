package com.michalenok.wallet.service;

import com.michalenok.wallet.model.constant.UserRole;
import com.michalenok.wallet.model.constant.UserStatus;
import com.michalenok.wallet.model.dto.request.UserCreateDto;
import com.michalenok.wallet.model.dto.request.UserLoginDto;
import com.michalenok.wallet.model.dto.request.UserRegistrationDto;
import com.michalenok.wallet.model.dto.response.UserInfoDto;
import com.michalenok.wallet.model.entity.User;
import com.michalenok.wallet.model.entity.Verification;
import com.michalenok.wallet.repository.api.AuthenticationRepository;
import com.michalenok.wallet.service.api.AuthenticationService;
import com.michalenok.wallet.service.api.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final AuthenticationRepository authenticationRepository;
    private final UserService userService;
//    private final BCryptPasswordEncoder encoder;

    //этот метод используется при самостоятельной регистрации клиента в приложении
    @Override
    @Transactional
    public void register(UserRegistrationDto user) {
        UserInfoDto userInfoDto = userService.create(
                UserCreateDto.builder()
                .mail(user.mail())
                .mobilePhone(user.mobilePhone())
                .password(user.password())
                .status(UserStatus.WAITING_ACTIVATION)
                .role(Set.of(UserRole.USER))
                .build());
        authenticationRepository.save(
                Verification.builder()
                .mail(userInfoDto.mail())
                .code(UUID.randomUUID())
                .build());
//        sendMessage(user.getMail(),code);
        log.info("Send verification code to user: " + user);
    }

    @Override
    @Transactional
    public void verify(String code, String mail) {
        Verification verificationCode = authenticationRepository.findById(mail)
                .orElseThrow(() ->
                        new RuntimeException("there is no code for mail " + mail));
        if(verificationCode.getCode().equals(code)) {
            User user = getUser(mail);
            userService.changeStatus(user.getUuid(), user.getDtUpdate(), UserStatus.ACTIVATED);
            authenticationRepository.delete(verificationCode);
            log.error("Successful verification: " + mail + " " + code);
        }else {
            log.error("Unsuccessful verification: " + mail + " " + code);
            throw new RuntimeException("Incorrect mail and code");
        }
    }

    @Override
    public void login(UserLoginDto userLoginDto) {
        User user = getUser(userLoginDto.mail());
//        if(!encoder.matches(userLoginDto.getPassword(),user.getPassword())){
        if(!userLoginDto.password().equals(user.getPassword())){
            log.error("Unsuccessful login with "+ user.getMail());
            throw new RuntimeException("Incorrect mail and password");
        }
        if (user.getStatus() != UserStatus.ACTIVATED) {
            log.error("Unsuccessful login with "+ user.getMail()+ " user is not activated");
            throw new RuntimeException("Incorrect mail and password");
        }
    }
    @Override
    public User getUser(String mail) {
        return userService.findByMail(mail);
    }
}
