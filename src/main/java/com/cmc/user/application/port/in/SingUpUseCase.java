package com.cmc.user.application.port.in;

public interface SingUpUseCase {
    Long signup(String nickname, String email, String password);
}
