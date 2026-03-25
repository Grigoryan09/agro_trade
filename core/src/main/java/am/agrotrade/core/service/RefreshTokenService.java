package am.agrotrade.core.service;

import am.agrotrade.core.model.RefreshToken;
import am.agrotrade.core.model.User;

public interface RefreshTokenService {


    RefreshToken createRefreshToken(User user);

    RefreshToken verifyAndRotate(String refreshTokenStr);

    void revokeAllForUser(long userId);

    void cleanupExpired();
}
