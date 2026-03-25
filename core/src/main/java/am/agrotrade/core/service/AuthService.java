package am.agrotrade.core.service;

import am.agrotrade.common.dto.user.BaseUserInfoDto;
import am.agrotrade.common.dto.user.request.LoginRequest;
import am.agrotrade.common.dto.user.request.RegisterRequest;
import am.agrotrade.common.dto.user.response.AuthResponse;

import java.awt.print.Pageable;
import java.util.List;

public interface AuthService {

    AuthResponse register(RegisterRequest request);

    AuthResponse login(LoginRequest request);

    AuthResponse refresh(String refreshTokenStr);

    void logout(long userId);

    void delete(long userId);

    List<BaseUserInfoDto> findAll(Pageable pageable);

    long findUserIdByRole(String role);




}
