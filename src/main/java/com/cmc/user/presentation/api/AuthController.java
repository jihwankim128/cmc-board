package com.cmc.user.presentation.api;

import static com.cmc.user.presentation.api.status.UserSuccessStatus.USER_SIGNUP_SUCCESS;

import com.cmc.global.common.dto.CommonResponse;
import com.cmc.global.web.message.MessageSourceHelper;
import com.cmc.user.application.port.in.SingupUserUseCase;
import com.cmc.user.presentation.api.dto.SignupDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final SingupUserUseCase signupUseCase;
    private final MessageSourceHelper messageSourceHelper;

    @PostMapping("/signup")
    public CommonResponse<Long> signup(@RequestBody @Valid SignupDto dto) {
        Long userId = signupUseCase.signup(dto.nickname(), dto.email(), dto.password());
        String message = messageSourceHelper.extractMessage(USER_SIGNUP_SUCCESS);
        return CommonResponse.ok(userId, USER_SIGNUP_SUCCESS, message);
    }
}
