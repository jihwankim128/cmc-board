package com.cmc.user.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.cmc.user.domain.User;
import com.cmc.user.domain.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserService userService;

    @Test
    void 사용자_생성_커맨드가_발생하면_사용자를_생성하고_저장한다() {
        // given
        User mockUser = mock(User.class);
        when(mockUser.getId()).thenReturn(1L);
        when(userRepository.save(any(User.class))).thenReturn(mockUser);

        // when
        Long result = userService.create("김지환");

        // then
        assertThat(mockUser.getId()).isEqualTo(result);
    }

}