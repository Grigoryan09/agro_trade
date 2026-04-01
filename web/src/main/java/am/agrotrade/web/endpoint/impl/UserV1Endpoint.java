package am.agrotrade.web.endpoint.impl;

import am.agrotrade.common.dto.user.request.ChangePasswordRequest;
import am.agrotrade.common.dto.user.request.UpdateUserRequest;
import am.agrotrade.common.dto.user.response.BaseUserInfoResponse;
import am.agrotrade.core.security.UserPrincipal;
import am.agrotrade.core.service.UserService;
import am.agrotrade.web.endpoint.UserV1API;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UserV1Endpoint implements UserV1API {

    private final UserService userService;

    @Override
    public BaseUserInfoResponse get(UserPrincipal user) {
        return new BaseUserInfoResponse(userService.get(user.getUsername()));
    }

    @Override
    public BaseUserInfoResponse update(UserPrincipal user, UpdateUserRequest request) {
        return new BaseUserInfoResponse(userService.update(user.getUsername(), request));
    }

    @Override
    public BaseUserInfoResponse changePassword(UserPrincipal user, ChangePasswordRequest request) {
        return new BaseUserInfoResponse(userService.changePassword(user.getUsername(), request));
    }

}
