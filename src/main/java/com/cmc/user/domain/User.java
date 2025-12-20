package com.cmc.user.domain;

import com.cmc.user.domain.vo.Email;
import com.cmc.user.domain.vo.Nickname;
import com.cmc.user.domain.vo.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(staticName = "of")
public class User {
    private final Long id;
    private Nickname nickname;
    private Email email;
    private String passwordHash;
    private UserRole role;

    public static User create(Nickname nickname, Email email, String passwordHash) {
        return new User(null, nickname, email, passwordHash, UserRole.USER);
    }
}
