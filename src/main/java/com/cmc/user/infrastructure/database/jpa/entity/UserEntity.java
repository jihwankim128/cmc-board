package com.cmc.user.infrastructure.database.jpa.entity;

import com.cmc.global.database.TimeBaseEntity;
import com.cmc.user.domain.User;
import com.cmc.user.domain.vo.Nickname;
import com.cmc.user.domain.vo.Email;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "users")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class UserEntity extends TimeBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String nickname;

    @Column(nullable = false, unique = true, length = 120)
    private String email;

    @Column(nullable = false, length = 100)
    private String passwordHash;

    public static UserEntity from(User user) {
        return new UserEntity(
                user.getId(),
                user.getNickname().value(),
                user.getEmail().value(),
                user.getPasswordHash()
        );
    }

    public User toDomain() {
        return User.of(id, new Nickname(nickname), new Email(email), passwordHash);
    }
}
