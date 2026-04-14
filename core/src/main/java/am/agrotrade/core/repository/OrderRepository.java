package am.agrotrade.core.repository;

import am.agrotrade.core.model.Order;
import am.agrotrade.core.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {


    @Query(value = """
    SELECT * FROM "order" o
    WHERE o.manager_id = :managerId
    AND o.id = :orderId
    """, nativeQuery = true)
    Optional<Order> findByManagerIdAndOrderId(
            @Param("managerId") long managerId,
            @Param("orderId") long orderId
    );

    Page<Order> findAllByManagerId(Long managerId, Pageable pageable);
}
