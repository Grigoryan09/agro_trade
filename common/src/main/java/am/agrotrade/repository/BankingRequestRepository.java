package am.agrotrade.repository;

import am.agrotrade.model.BankingRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankingRequestRepository extends JpaRepository<BankingRequest, Long> {
}
