package am.agrotrade.core.repository;

import am.agrotrade.core.model.Passport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PassportRepository extends JpaRepository<Passport, Long> {
}
