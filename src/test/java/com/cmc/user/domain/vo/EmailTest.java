package com.cmc.user.domain.vo;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.cmc.global.common.exception.client.BadRequestException;
import com.cmc.user.domain.constants.UserExceptionStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class EmailTest {

    @Test
    void 유효한_이메일_형식으로_객체를_성공적으로_생성한다() {
        String validEmail = "testuser123@example.com";

        assertThatCode(() -> new Email(validEmail))
                .doesNotThrowAnyException();
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "\t", "\n"})
    void 이메일_값이_비어있거나_공백이면_예외가_발생한다(String invalidEmail) {
        assertThatThrownBy(() -> new Email(invalidEmail))
                .isInstanceOf(BadRequestException.class)
                .hasFieldOrPropertyWithValue("status", UserExceptionStatus.USER_EMAIL_INVALID);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "invalid-email",       // @ 기호 없음
            "user@domain",         // . 확장자 없음
            "user@.com",           // @ 뒤 도메인 이름 없음
            "user@domain.c",       // 도메인 확장자가 2자 미만
            "user@domain com",     // 공백 포함
            "@domain.com",         // 로컬 파트 없음
            "user@",               // 도메인 파트 없음
            "user@domain.123"      // 도메인 확장자가 숫자로 끝남
    })
    void 유효하지_않은_이메일_정규식_형식이면_예외가_발생한다(String invalidEmail) {
        assertThatThrownBy(() -> new Email(invalidEmail))
                .isInstanceOf(BadRequestException.class)
                .hasFieldOrPropertyWithValue("status", UserExceptionStatus.USER_EMAIL_INVALID);
    }
}