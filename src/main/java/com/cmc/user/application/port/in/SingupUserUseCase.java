package com.cmc.user.application.port.in;

public interface SingupUserUseCase {
    Long signup(String nickname, String email, String password);
}
