package am.agrotrade.core.repository;

import am.agrotrade.core.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {


    Optional<Order> findByIdAndManagerId(Long orderId, Long managerId);

    Page<Order> findAllByManagerId(Long managerId, Pageable pageable);
}
