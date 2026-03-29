package am.agrotrade.core.repository;

import am.agrotrade.core.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
