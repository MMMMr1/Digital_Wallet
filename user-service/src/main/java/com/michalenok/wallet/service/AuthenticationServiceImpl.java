package com.michalenok.wallet.service;

import com.michalenok.wallet.mapper.UserMapper;
import com.michalenok.wallet.model.constant.UserStatus;
import com.michalenok.wallet.model.dto.request.UserLoginDto;
import com.michalenok.wallet.model.dto.request.UserRegistrationDto;
import com.michalenok.wallet.model.entity.UserEntity;
import com.michalenok.wallet.model.entity.VerificationEntity;
import com.michalenok.wallet.model.exception.ValidationUserException;
import com.michalenok.wallet.model.exception.VerificationUserException;
import com.michalenok.wallet.repository.api.VerificationRepository;
import com.michalenok.wallet.service.api.AuthenticationService;
import com.michalenok.wallet.service.api.UserService;
import com.michalenok.wallet.service.util.VerificationUserUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final VerificationRepository verificationRepository;
    private final UserService userService;
    private final UserMapper userMapper;
    private final VerificationUserUtil verificationUserUtil;

    @Override
    @Transactional
    public void register(UserRegistrationDto user) {
        userService.create(userMapper.userRegistrationDtoToUserCreateDto(user));
        verificationRepository.save(generateVerificationEntity(user.mail()));
    }

    @Override
    @Transactional
    public void verifyUser(String code, String mail) {
            isCodeValid(code, mail);
            userService.changeStatus(getUser(mail).getUuid(), UserStatus.ACTIVATED);
            verificationRepository.deleteByMail(mail);
            log.info("Successful verification: {} , {}", mail, code);
    }

    @Override
    public void login(UserLoginDto userLoginDto) {
        UserEntity user = getUser(userLoginDto.mail());
        isStatusActivated(user);
        isPasswordValid(user.getPassword(), userLoginDto.password());
        log.info("Successful authentication user with mail {}", userLoginDto.mail());
    }

    private UserEntity getUser(String mail) {
        log.info("get user with mail {}", mail);
        return  userMapper.toUserEntity(userService.findByMail(mail));
    }

    private void isCodeValid(String code, String mail){
        VerificationEntity verificationCode = verificationRepository.findById(mail)
                .orElseThrow(() ->
                        new VerificationUserException(String.format("There is no code for mail %s", mail)));
        if(!code.equals(verificationCode.getCode())){
            log.error("Unsuccessful verification: {} , {}", mail, code);
            throw new VerificationUserException("Incorrect mail and code");
        }
    }

    private void isStatusActivated(UserEntity user){
        if (user.getStatus() != UserStatus.ACTIVATED) {
            log.error("Unsuccessful login with {}. User is not activated", user.getMail());
            throw new ValidationUserException(String.format("User with mail {%s} is not ACTIVATED", user.getMail()));
        }
    }

    private void isPasswordValid(String password, String passwordDto){
        if (!password.equals(passwordDto)) {
            log.error("Incorrect password");
            throw new ValidationUserException("Incorrect password");
        }
    }

    private VerificationEntity generateVerificationEntity(String mail){
        VerificationEntity verificationEntity = new VerificationEntity();
        verificationEntity.setMail(mail);
        verificationEntity.setCode(verificationUserUtil.generateCode());
        log.info("Create verification code {}  to user: {}", verificationEntity.getCode(), verificationEntity.getMail());
        return verificationEntity;
    }
}
