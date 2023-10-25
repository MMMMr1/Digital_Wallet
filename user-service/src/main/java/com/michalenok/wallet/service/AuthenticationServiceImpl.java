package com.michalenok.wallet.service;

import com.michalenok.wallet.model.constant.UserRole;
import com.michalenok.wallet.model.constant.UserStatus;
import com.michalenok.wallet.model.dto.request.UserCreateDto;
import com.michalenok.wallet.model.dto.request.UserLoginDto;
import com.michalenok.wallet.model.dto.request.UserRegistrationDto;
import com.michalenok.wallet.model.dto.response.UserInfoDto;
import com.michalenok.wallet.model.entity.User;
import com.michalenok.wallet.model.entity.Verification;
import com.michalenok.wallet.model.exception.ValidationUserException;
import com.michalenok.wallet.model.exception.VerificationUserException;
import com.michalenok.wallet.repository.api.AuthenticationRepository;
import com.michalenok.wallet.service.api.AuthenticationService;
import com.michalenok.wallet.service.api.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.Set;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthenticationServiceImpl implements AuthenticationService {
    private final AuthenticationRepository authenticationRepository;
    private final UserService userService;
//    private final BCryptPasswordEncoder encoder;

    //этот метод используется при самостоятельной регистрации клиента в приложении
    @Override
    @Transactional
    public void register(@Validated UserRegistrationDto user) {
        UserInfoDto userInfoDto = userService.create(
                UserCreateDto.builder()
                .mail(user.mail())
                .mobilePhone(user.mobilePhone())
                .password(user.password())
                .status(UserStatus.WAITING_ACTIVATION)
                .role(Set.of(UserRole.USER))
                .build());
        String code = UUID.randomUUID().toString();
        Verification verification = new Verification();
        verification.setMail(userInfoDto.mail());
        verification.setCode(code);
        authenticationRepository.save(verification);
//        sendMessage(user.getMail(),code);
        log.info("Send verification code { "+ code + " to user: " + user);
    }

    @Override
    @Transactional
    public void verify(String code, String mail) {
        Verification verificationCode = authenticationRepository.findById(mail)
                .orElseThrow(() ->
                        new VerificationUserException("there is no code for mail " + mail));
        if(code.equals(verificationCode.getCode())) {
            User user = getUser(mail);
            userService.changeStatus(user.getUuid(), user.getDtUpdate(), UserStatus.ACTIVATED);
            authenticationRepository.delete(verificationCode);
            log.error("Successful verification: " + mail + " " + code);
        }else {
            log.error("Unsuccessful verification: " + mail + " " + code);
            throw new VerificationUserException("Incorrect mail and code");
        }
    }

    @Override
    public void login(@Validated UserLoginDto userLoginDto) {
        User user = getUser(userLoginDto.mail());
//        if(!encoder.matches(userLoginDto.getPassword(),user.getPassword())){
        if(!userLoginDto.password().equals(user.getPassword())){
            log.error("Unsuccessful login with "+ user.getMail());
            throw new ValidationUserException("Incorrect mail and password");
        }
        if (user.getStatus() != UserStatus.ACTIVATED) {
            log.error("Unsuccessful login with "+ user.getMail()+ " user is not activated");
            throw new ValidationUserException("User with mail {" + user.getMail() + " } is not ACTIVATED");
        }
    }
    @Override
    public User getUser(String mail) {
        return userService.findByMail(mail);
    }
}
