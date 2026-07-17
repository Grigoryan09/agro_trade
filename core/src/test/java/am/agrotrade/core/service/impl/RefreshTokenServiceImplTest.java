package am.agrotrade.core.service.impl;

import am.agrotrade.core.exception.InvalidRefreshTokenException;
import am.agrotrade.core.model.RefreshToken;
import am.agrotrade.core.model.User;
import am.agrotrade.core.repository.RefreshTokenRepository;
import am.agrotrade.core.security.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RefreshTokenServiceImplTest {

    @Mock
    private RefreshTokenRepository repository;
    @Mock
    private JwtService jwtService;

    @InjectMocks
    private RefreshTokenServiceImpl refreshTokenService;

    private User user;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(refreshTokenService, "refreshExpirationMs", 60_000L);
        user = new User();
        user.setId(1L);
    }

    @Test
    void createRefreshToken_buildsAndPersistsToken() {
        when(jwtService.generateRefreshToken(any())).thenReturn("token-value");
        when(repository.save(any(RefreshToken.class))).thenAnswer(inv -> inv.getArgument(0));

        RefreshToken result = refreshTokenService.createRefreshToken(user);

        ArgumentCaptor<RefreshToken> captor = ArgumentCaptor.forClass(RefreshToken.class);
        verify(repository).save(captor.capture());
        RefreshToken saved = captor.getValue();
        assertThat(saved.getToken()).isEqualTo("token-value");
        assertThat(saved.getUser()).isSameAs(user);
        assertThat(saved.isRevoked()).isFalse();
        assertThat(saved.getExpiryDate()).isAfter(new Date());
        assertThat(result).isSameAs(saved);
    }

    @Test
    void verifyAndRotate_tokenNotFound_throws() {
        when(repository.findByToken("missing")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> refreshTokenService.verifyAndRotate("missing"))
                .isInstanceOf(InvalidRefreshTokenException.class);
    }

    @Test
    void verifyAndRotate_revokedToken_throws() {
        RefreshToken rt = RefreshToken.builder()
                .token("t").user(user).revoked(true)
                .expiryDate(new Date(System.currentTimeMillis() + 60_000L))
                .build();
        when(repository.findByToken("t")).thenReturn(Optional.of(rt));

        assertThatThrownBy(() -> refreshTokenService.verifyAndRotate("t"))
                .isInstanceOf(InvalidRefreshTokenException.class);

        verify(repository, never()).delete(any());
    }

    @Test
    void verifyAndRotate_expiredToken_throws() {
        RefreshToken rt = RefreshToken.builder()
                .token("t").user(user).revoked(false)
                .expiryDate(new Date(System.currentTimeMillis() - 1000L))
                .build();
        when(repository.findByToken("t")).thenReturn(Optional.of(rt));

        assertThatThrownBy(() -> refreshTokenService.verifyAndRotate("t"))
                .isInstanceOf(InvalidRefreshTokenException.class);
    }

    @Test
    void verifyAndRotate_valid_deletesOldAndCreatesNew() {
        RefreshToken rt = RefreshToken.builder()
                .token("old").user(user).revoked(false)
                .expiryDate(new Date(System.currentTimeMillis() + 60_000L))
                .build();
        when(repository.findByToken("old")).thenReturn(Optional.of(rt));
        when(jwtService.generateRefreshToken(any())).thenReturn("new-token");
        when(repository.save(any(RefreshToken.class))).thenAnswer(inv -> inv.getArgument(0));

        RefreshToken result = refreshTokenService.verifyAndRotate("old");

        verify(repository).delete(rt);
        assertThat(result.getToken()).isEqualTo("new-token");
    }

    @Test
    void revokeAllForUser_delegatesToRepository() {
        refreshTokenService.revokeAllForUser(1L);

        verify(repository).deleteByUserId(1L);
    }

    @Test
    void cleanupExpired_deletesExpiredTokens() {
        refreshTokenService.cleanupExpired();

        verify(repository).deleteByExpiryDateBefore(any(Date.class));
    }
}
