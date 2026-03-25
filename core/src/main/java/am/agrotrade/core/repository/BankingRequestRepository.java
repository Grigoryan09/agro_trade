package am.agrotrade.core.repository;

import am.agrotrade.core.model.BankingRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankingRequestRepository extends JpaRepository<BankingRequest, Long> {
}
