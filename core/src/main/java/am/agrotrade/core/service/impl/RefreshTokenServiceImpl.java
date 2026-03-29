package am.agrotrade.core.service.impl;

import am.agrotrade.core.exception.InvalidRefreshTokenException;
import am.agrotrade.core.model.RefreshToken;
import am.agrotrade.core.model.User;
import am.agrotrade.core.repository.RefreshTokenRepository;
import am.agrotrade.core.service.RefreshTokenService;
import am.agrotrade.core.service.security.JwtService;
import am.agrotrade.core.service.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepository repository;
    private final JwtService jwtService;

    @Value("${jwt.refresh-expiration}")
    private long refreshExpirationMs;

    @Override
    public RefreshToken createRefreshToken(User user) {
        String token = jwtService.generateRefreshToken(new UserPrincipal(user));

        RefreshToken refreshToken = RefreshToken.builder()
                .token(token)
                .user(user)
                .expiryDate(new Date(System.currentTimeMillis() + refreshExpirationMs))
                .revoked(false)
                .build();

        return repository.save(refreshToken);
    }

    @Override
    public RefreshToken verifyAndRotate(String refreshTokenStr) {
        RefreshToken rt = repository.findByToken(refreshTokenStr)
                .orElseThrow(() -> new InvalidRefreshTokenException("Refresh token not found or invalid"));

        if (rt.isRevoked() || rt.isExpired()) {
            throw new InvalidRefreshTokenException("Refresh token is expired or revoked");
        }

        repository.delete(rt);

        return createRefreshToken(rt.getUser());
    }

    @Override
    public void revokeAllForUser(long userId) {
        repository.deleteByUserId(userId);
    }

    @Override
    public void cleanupExpired() {
        repository.deleteByExpiryDateBefore(new Date());
    }
}
