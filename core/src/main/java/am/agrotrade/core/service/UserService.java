package am.agrotrade.core.service;

import am.agrotrade.common.dto.user.BaseUserInfoDto;
import am.agrotrade.common.dto.user.request.ChangePasswordRequest;
import am.agrotrade.common.dto.user.request.UpdateUserRequest;

public interface UserService {

    BaseUserInfoDto get(String username);

    BaseUserInfoDto update(String username, UpdateUserRequest request);

    BaseUserInfoDto changePassword(String username, ChangePasswordRequest request);
}
