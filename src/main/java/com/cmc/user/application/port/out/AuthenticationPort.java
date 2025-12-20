package com.cmc.user.application.port.out;

public interface AuthenticationPort {

    void persistAuthentication(Long userId, String role);
}