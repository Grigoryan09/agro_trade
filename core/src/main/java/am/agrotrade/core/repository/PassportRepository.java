package am.agrotrade.core.repository;

import am.agrotrade.core.model.Passport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PassportRepository extends JpaRepository<Passport, Long> {

    boolean existsByUserId(long userId);

    void deleteByUserId(long userId);

    Optional<Passport> findByUserId(long userId);
}
