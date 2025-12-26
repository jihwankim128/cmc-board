package com.cmc.user.presentation.api;

import static com.cmc.user.presentation.api.status.UserSuccessStatus.USER_LOGIN_SUCCESS;
import static com.cmc.user.presentation.api.status.UserSuccessStatus.USER_SIGNUP_SUCCESS;

import com.cmc.global.common.dto.CommonResponse;
import com.cmc.global.web.message.MessageSourceHelper;
import com.cmc.user.application.port.in.LoginUseCase;
import com.cmc.user.application.port.in.SingUpUseCase;
import com.cmc.user.presentation.api.docs.AccountApiControllerDocs;
import com.cmc.user.presentation.api.dto.LoginDto;
import com.cmc.user.presentation.api.dto.SignupDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/account")
public class AccountController implements AccountApiControllerDocs {

    private final SingUpUseCase signupUseCase;
    private final LoginUseCase loginUseCase;
    private final MessageSourceHelper messageSourceHelper;

    // TODO: Password 비즈니스 좀 더 명확히
    @PostMapping("/signup")
    public CommonResponse<Void> signup(@RequestBody @Valid SignupDto dto) {
        signupUseCase.signup(dto.nickname(), dto.email(), dto.password());
        String message = messageSourceHelper.extractMessage(USER_SIGNUP_SUCCESS);
        return CommonResponse.noContent(USER_SIGNUP_SUCCESS, message);
    }

    @PostMapping("/login")
    public CommonResponse<Void> login(@RequestBody @Valid LoginDto dto) {
        loginUseCase.login(dto.email(), dto.password());
        String message = messageSourceHelper.extractMessage(USER_LOGIN_SUCCESS);
        return CommonResponse.noContent(USER_LOGIN_SUCCESS, message);
    }
}
