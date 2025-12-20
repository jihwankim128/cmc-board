package com.cmc.user.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.cmc.user.domain.vo.Email;
import com.cmc.user.domain.vo.Nickname;
import org.junit.jupiter.api.Test;

class UserTest {

    @Test
    void 사용자_닉네임으로_사용자_정보를_생성할_수_있다() {
        // given
        Nickname nickname = new Nickname("김지환");
        Email email = new Email("testEmail@naver.com");
        String password = "encrypted password";

        // when
        User user = User.create(nickname, email, password);

        // then
        assertThat(user.getNickname()).isEqualTo(nickname);
        assertThat(user.getEmail()).isEqualTo(email);
        assertThat(user.getPasswordHash()).isEqualTo(password);
    }
}
