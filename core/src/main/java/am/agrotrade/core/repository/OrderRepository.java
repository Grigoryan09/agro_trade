package am.agrotrade.core.repository;

import am.agrotrade.common.enums.OrderStatus;
import am.agrotrade.core.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {


    Optional<Order> findByIdAndManagerId(Long orderId, Long managerId);

    @Query("SELECT o FROM Order o WHERE o.chatId = :chatId")
    Optional<Order> findByChatId(@Param("chatId") long chatId);

    Page<Order> findAllByManagerId(Long managerId, Pageable pageable);

    Page<Order> findAllByOrderStatus(OrderStatus orderStatus, Pageable pageable);
}
