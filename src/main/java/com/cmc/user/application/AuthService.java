package com.cmc.user.application;

import com.cmc.user.application.port.in.LoginUseCase;
import com.cmc.user.application.port.in.SingupUseCase;
import com.cmc.user.application.port.out.PasswordHashPort;
import com.cmc.user.domain.User;
import com.cmc.user.domain.UserRepository;
import com.cmc.user.domain.exception.InvalidUserAccount;
import com.cmc.user.domain.vo.Email;
import com.cmc.user.domain.vo.Nickname;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService implements SingupUseCase, LoginUseCase {

    private final UserRepository userRepository;
    private final PasswordHashPort passwordHashPort;

    @Override
    public Long signup(String nickname, String email, String password) {
        String encodedPassword = passwordHashPort.encode(password);
        User user = User.create(new Nickname(nickname), new Email(email), encodedPassword);
        User savedUser = userRepository.save(user);
        return savedUser.getId();
    }

    @Override
    public Long login(String email, String password) {
        User user = userRepository.findByEmail(email).orElseThrow(InvalidUserAccount::new);
        if (!passwordHashPort.match(password, user.getPasswordHash())) {
            throw new InvalidUserAccount();
        }
        return user.getId();
    }
}
