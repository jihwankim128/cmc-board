package com.cmc.user.application.port.out;

import com.cmc.user.domain.User;

public interface AuthenticationPort {

    void persistAuthentication(User user);
}