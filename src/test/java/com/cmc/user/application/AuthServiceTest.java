package com.cmc.user.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.cmc.user.application.port.out.PasswordHashPort;
import com.cmc.user.domain.User;
import com.cmc.user.domain.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordHashPort passwordHashPort;
    @InjectMocks
    private AuthService authService;

    @Test
    void 사용자_생성_커맨드가_발생하면_사용자를_생성하고_저장한다() {
        // given
        User mockUser = mock(User.class);
        when(mockUser.getId()).thenReturn(1L);
        when(userRepository.save(any(User.class))).thenReturn(mockUser);
        when(passwordHashPort.encode(anyString())).thenReturn("encodePassword");

        // when
        Long result = authService.signup("김지환", "testuser123@example.com", "password");

        // then
        assertThat(mockUser.getId()).isEqualTo(result);
    }

}