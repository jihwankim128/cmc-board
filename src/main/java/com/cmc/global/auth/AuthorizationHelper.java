package com.cmc.global.auth;

public interface AuthorizationHelper {

    Long getCurrentUserId();

    boolean isAdmin();
}
