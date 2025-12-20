package com.cmc.user.application.port.in;

public interface SingupUseCase {
    Long signup(String nickname, String email, String password);
}
