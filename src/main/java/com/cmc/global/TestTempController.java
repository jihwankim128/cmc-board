package com.cmc.global;

import static com.cmc.user.presentation.api.status.UserSuccessStatus.USER_LOGIN_SUCCESS;

import com.cmc.global.auth.AdminPrincipal;
import com.cmc.global.auth.UserPrincipal;
import com.cmc.global.common.dto.CommonResponse;
import com.cmc.global.common.interfaces.StatusCode;
import com.cmc.user.application.port.in.LoginUseCase;
import com.cmc.user.application.port.out.PasswordHashPort;
import com.cmc.user.domain.User;
import com.cmc.user.domain.UserRepository;
import com.cmc.user.domain.vo.Email;
import com.cmc.user.domain.vo.Nickname;
import com.cmc.user.domain.vo.UserRole;
import com.cmc.user.infrastructure.database.jpa.UserJpaRepository;
import com.cmc.user.infrastructure.database.jpa.entity.UserEntity;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// Test용 임시 컨트롤러 - 제거 예정
@RestController
@RequiredArgsConstructor
@RequestMapping("/tool")
public class TestTempController {

    private final PasswordHashPort passwordHashPort;
    private final UserRepository userRepository;
    private final LoginUseCase loginUseCase;
    private final UserJpaRepository userJpaRepository;

    @PostConstruct
    @Transactional
    public void init() {
        String encodedPassword = passwordHashPort.encode("testPassword");
        User user = User.of(null, new Nickname("admin"), new Email("test@mail.com"), encodedPassword, UserRole.ADMIN);
        userRepository.save(user);
    }

    @GetMapping("/auth/admin")
    public CommonResponse<Void> login() {
        loginUseCase.login("test@mail.com", "testPassword");
        return CommonResponse.noContent(USER_LOGIN_SUCCESS, "성공");
    }

    @GetMapping("/users/me")
    public CommonResponse<UserDto> getMe(@UserPrincipal Long userId) {
        UserEntity userEntity = userJpaRepository.findById(userId).get();
        UserDto dto = new UserDto(userEntity.getId(), userEntity.getNickname(), userEntity.getEmail(),
                userEntity.getPasswordHash(), userEntity.getRole().name());
        return CommonResponse.ok(dto, UserCode.USER_GET_ME_SUCCESS, "조회 성공");
    }

    @AdminPrincipal
    @GetMapping("/users/me/admin")
    public CommonResponse<UserDto> getMeAdmin() {
        UserEntity userEntity = userJpaRepository.findById(1L).get();
        UserDto dto = new UserDto(userEntity.getId(), userEntity.getNickname(), userEntity.getEmail(),
                userEntity.getPasswordHash(), userEntity.getRole().name());
        return CommonResponse.ok(dto, UserCode.USER_GET_ME_SUCCESS, "조회 성공");
    }

    public enum UserCode implements StatusCode {
        USER_GET_ME_SUCCESS;
    }

    public record UserDto(Long userId, String nickname, String email, String password, String role) {
    }
}
