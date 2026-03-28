package am.agrotrade.core.repository;

import am.agrotrade.core.model.RefreshToken;
import am.agrotrade.core.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String> {

    Optional<RefreshToken> findByToken(String token);

    void deleteByToken(String token);

    void deleteByUserId(long userId);

    void deleteByExpiryDateBefore(Date date);

    long user(User user);
}
