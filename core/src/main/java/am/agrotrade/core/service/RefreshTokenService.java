package am.agrotrade.core.service;

import am.agrotrade.core.model.RefreshToken;
import am.agrotrade.core.model.User;

/**
 * Manages refresh token issuing, rotation, revocation, and cleanup.
 */
public interface RefreshTokenService {

    /**
     * Creates a refresh token for the specified user.
     *
     * @param user token owner
     * @return created refresh token
     */
    RefreshToken createRefreshToken(User user);

    /**
     * Validates a refresh token and rotates it.
     *
     * @param refreshTokenStr refresh token value
     * @return new active refresh token
     */
    RefreshToken verifyAndRotate(String refreshTokenStr);

    /**
     * Revokes all refresh tokens for the specified user.
     *
     * @param userId user identifier
     */
    void revokeAllForUser(long userId);

    /**
     * Removes expired refresh tokens.
     */
    void cleanupExpired();
}
