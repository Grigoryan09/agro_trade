package am.agrotrade.core.service;

import am.agrotrade.common.dto.user.BaseUserInfoDto;
import am.agrotrade.common.dto.user.LoginDto;
import am.agrotrade.common.dto.user.RefreshTokenDto;
import am.agrotrade.common.dto.user.RegisterDto;
import am.agrotrade.common.dto.user.ResendCodeDto;
import am.agrotrade.common.dto.user.VerifyDto;
import am.agrotrade.common.dto.user.request.LoginRequest;
import am.agrotrade.common.dto.user.request.RegisterRequest;

import java.awt.print.Pageable;
import java.util.List;

public interface AuthService {

    RegisterDto register(RegisterRequest request);

    VerifyDto verify(String email, String code);

    ResendCodeDto resendVerificationCode(String email);

    LoginDto login(LoginRequest request);

    RefreshTokenDto refresh(String refreshTokenStr);

    void logout(long userId);

    void delete(long userId);

    List<BaseUserInfoDto> findAll(Pageable pageable);

    long findUserIdByRole(String role);


}
