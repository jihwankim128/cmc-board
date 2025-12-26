package com.cmc.user.application;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.cmc.global.common.exception.client.BadRequestException;
import com.cmc.user.application.port.out.AuthenticationPort;
import com.cmc.user.application.port.out.PasswordHashPort;
import com.cmc.user.domain.User;
import com.cmc.user.domain.UserRepository;
import com.cmc.user.domain.constants.UserExceptionStatus;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordHashPort passwordHashPort;
    @Mock
    private AuthenticationPort authenticationPort;
    @InjectMocks
    private AccountService accountService;

    @Test
    void 이미_존재하는_계정으로_회원가입을_시도하면_예외가_발생한다() {
        // given
        when(userRepository.existsByEmail(anyString())).thenReturn(true);

        // when & then
        assertThatThrownBy(() -> accountService.signup("김지환", "testuser123@example.com", "password"))
                .isInstanceOf(BadRequestException.class)
                .hasFieldOrPropertyWithValue("status", UserExceptionStatus.USER_ACCOUNT_DUPLICATED);
    }

    @Test
    void 회원가입_커맨드가_발생하면_사용자를_생성하고_저장한다() {
        // given
        User mockUser = mock(User.class);
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(passwordHashPort.encode(anyString())).thenReturn("encodePassword");

        // when
        accountService.signup("김지환", "testuser123@example.com", "password");

        // then
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void 없는_이메일_정보로_로그인_요청을_하면_잘못된_계정이다() {
        // given
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> accountService.login("testuser123@example.com", "password"))
                .isInstanceOf(BadRequestException.class)
                .hasFieldOrPropertyWithValue("status", UserExceptionStatus.USER_ACCOUNT_INVALID);
    }

    @Test
    void 틀린_이메일_비밀번호로_로그인_요청을_하면_잘못된_계정이다() {
        // given
        User mockUser = mock(User.class);
        when(mockUser.getPasswordHash()).thenReturn("encodePassword");
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(mockUser));
        when(passwordHashPort.match(anyString(), anyString())).thenReturn(false);

        // when & then
        assertThatThrownBy(() -> accountService.login("testuser123@example.com", "password"))
                .isInstanceOf(BadRequestException.class)
                .hasFieldOrPropertyWithValue("status", UserExceptionStatus.USER_ACCOUNT_INVALID);
    }

    @Test
    void 사용자_로그인_커맨드가_발생하면_로그인한_사용자_인증_정보를_저장한다() {
        // given
        User mockUser = mock(User.class);
        when(mockUser.getPasswordHash()).thenReturn("encodePassword");
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(mockUser));
        when(passwordHashPort.match(anyString(), anyString())).thenReturn(true);

        // when
        accountService.login("testuser123@example.com", "password");

        // then
        verify(authenticationPort, times(1)).persistAuthentication(any(User.class));
    }
}