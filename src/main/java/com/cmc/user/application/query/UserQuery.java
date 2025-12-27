package com.cmc.user.application.query;

import java.util.List;
import java.util.Map;

public interface UserQuery {

    UserDto getUser(Long userId);

    Map<Long, UserDto> getUsers(List<Long> authorIds);
}
