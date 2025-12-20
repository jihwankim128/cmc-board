package com.cmc.user.domain.vo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.cmc.global.common.exception.client.BadRequestException;
import com.cmc.user.domain.constants.UserExceptionStatus;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class NicknameTest {

    private static final int MIN_NICKNAME_LENGTH = 2;
    private static final int MAX_NICKNAME_LENGTH = 16;

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "\t", "\n"})
    void 사용자_닉네임은_비어_있을_수_없다(String emptyValue) {
        // when & then
        assertThatThrownBy(() -> new Nickname(emptyValue))
                .isInstanceOf(BadRequestException.class)
                .hasFieldOrPropertyWithValue("status", UserExceptionStatus.USER_NICKNAME_EMPTY);
    }

    @ParameterizedTest
    @ValueSource(ints = {MIN_NICKNAME_LENGTH - 1, MAX_NICKNAME_LENGTH + 1})
    void 사용자_닉네임의_길이_범위를_벗어나면_안된다(int invalidRange) {
        // given
        String outRangeNickname = "a".repeat(invalidRange);
        // when & then
        assertThatThrownBy(() -> new Nickname(outRangeNickname))
                .isInstanceOf(BadRequestException.class)
                .hasFieldOrPropertyWithValue("status", UserExceptionStatus.USER_NICKNAME_OUT_OF_RANGE);
    }

    @ParameterizedTest
    @ValueSource(ints = {MIN_NICKNAME_LENGTH, MAX_NICKNAME_LENGTH})
    void 유효한_사용자_닉네임은_생성이_된다(int validRange) {
        // given
        String value = "a".repeat(validRange);

        // when
        Nickname nickname = new Nickname(value);

        // then
        assertThat(nickname.value()).isEqualTo(value);
    }
}