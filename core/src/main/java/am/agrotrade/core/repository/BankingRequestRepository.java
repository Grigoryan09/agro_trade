package am.agrotrade.core.repository;

import am.agrotrade.core.model.BankingRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BankingRequestRepository extends JpaRepository<BankingRequest, Long> {

    List<BankingRequest> findByUserId(long userId, Pageable pageable);
}
