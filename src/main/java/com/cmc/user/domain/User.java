package com.cmc.user.domain;

import com.cmc.user.domain.vo.Nickname;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(staticName = "of")
public class User {
    private final Long id;
    private Nickname nickname;

    public static User create(Nickname nickname) {
        return new User(null, nickname);
    }
}
