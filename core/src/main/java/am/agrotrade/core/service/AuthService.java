package am.agrotrade.core.service;

import am.agrotrade.common.dto.user.BaseUserInfoDto;
import am.agrotrade.common.dto.user.request.LoginRequest;
import am.agrotrade.common.dto.user.request.RegisterRequest;
import am.agrotrade.common.dto.user.request.ResendCodeResponse;
import am.agrotrade.common.dto.user.response.LoginResponse;
import am.agrotrade.common.dto.user.response.RefreshTokenResponse;
import am.agrotrade.common.dto.user.response.RegisterResponse;
import am.agrotrade.common.dto.user.response.VerifyResponse;

import java.awt.print.Pageable;
import java.util.List;

public interface AuthService {

    RegisterResponse register(RegisterRequest request);

    VerifyResponse verify(String email, String code);

    ResendCodeResponse resendVerificationCode(String email);

    LoginResponse login(LoginRequest request);

    RefreshTokenResponse refresh(String refreshTokenStr);

    void logout(long userId);

    void delete(long userId);

    List<BaseUserInfoDto> findAll(Pageable pageable);

    long findUserIdByRole(String role);




}
