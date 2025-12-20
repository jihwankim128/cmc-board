package com.cmc.user.application;

import com.cmc.user.application.port.in.CreateUserUseCase;
import com.cmc.user.domain.User;
import com.cmc.user.domain.UserRepository;
import com.cmc.user.domain.vo.Nickname;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService implements CreateUserUseCase {

    private final UserRepository userRepository;

    @Override
    public Long create(String nickname) {
        User user = User.create(new Nickname(nickname));
        User savedUser = userRepository.save(user);
        return savedUser.getId();
    }
}
