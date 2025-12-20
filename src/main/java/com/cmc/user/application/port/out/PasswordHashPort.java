package com.cmc.user.application.port.out;

public interface PasswordHashPort {

    String encode(String password);

    boolean match(String password, String hash);
}
