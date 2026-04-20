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

/**
 * Handles authentication, verification, token refresh, and account lookup operations.
 */
public interface AuthService {

    /**
     * Registers a new user.
     *
     * @param request registration payload
     * @return registered user data
     */
    RegisterDto register(RegisterRequest request);

    /**
     * Verifies a user by email and code.
     *
     * @param email user email
     * @param code verification code
     * @return verification result
     */
    VerifyDto verify(String email, String code);

    /**
     * Resends a verification code.
     *
     * @param email user email
     * @return resend result
     */
    ResendCodeDto resendVerificationCode(String email);

    /**
     * Authenticates a user.
     *
     * @param request login payload
     * @return login result with tokens and user data
     */
    LoginDto login(LoginRequest request);

    /**
     * Refreshes an authentication session using a refresh token.
     *
     * @param refreshTokenStr refresh token value
     * @return refreshed token data
     */
    RefreshTokenDto refresh(String refreshTokenStr);

    /**
     * Logs out the specified user.
     *
     * @param userId user identifier
     */
    void logout(long userId);

    /**
     * Deletes the specified user account.
     *
     * @param userId user identifier
     */
    void delete(long userId);

    /**
     * Returns users using the provided paging settings.
     *
     * @param pageable paging parameters
     * @return user list
     */
    List<BaseUserInfoDto> findAll(Pageable pageable);

    /**
     * Returns a user identifier by role name.
     *
     * @param role role name
     * @return user identifier
     */
    long findUserIdByRole(String role);
}
