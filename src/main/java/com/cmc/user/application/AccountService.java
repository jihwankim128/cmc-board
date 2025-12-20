package com.cmc.user.application;

import com.cmc.user.application.port.in.LoginUseCase;
import com.cmc.user.application.port.in.SingUpUseCase;
import com.cmc.user.application.port.out.AuthenticationPort;
import com.cmc.user.application.port.out.PasswordHashPort;
import com.cmc.user.domain.User;
import com.cmc.user.domain.UserRepository;
import com.cmc.user.domain.exception.DuplicatedUserAccount;
import com.cmc.user.domain.exception.InvalidUserAccount;
import com.cmc.user.domain.vo.Email;
import com.cmc.user.domain.vo.Nickname;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AccountService implements SingUpUseCase, LoginUseCase {

    private final UserRepository userRepository;
    private final PasswordHashPort passwordHashPort;
    private final AuthenticationPort authenticationPort;

    @Override
    public Long signup(String nickname, String email, String password) {
        if (userRepository.existsByEmail(email)) {
            throw new DuplicatedUserAccount();
        }
        String encodedPassword = passwordHashPort.encode(password);
        User user = User.create(new Nickname(nickname), new Email(email), encodedPassword);
        User savedUser = userRepository.save(user);
        return savedUser.getId();
    }

    @Override
    public void login(String email, String password) {
        User user = userRepository.findByEmail(email).orElseThrow(InvalidUserAccount::new);
        if (!passwordHashPort.match(password, user.getPasswordHash())) {
            throw new InvalidUserAccount();
        }

        authenticationPort.persistAuthentication(user.getId(), user.getRole().name());
    }
}
