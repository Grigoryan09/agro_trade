package am.agrotrade.repository;

import am.agrotrade.model.entity.BankingRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankingRequestRepository extends JpaRepository<BankingRequest, Long> {
}
