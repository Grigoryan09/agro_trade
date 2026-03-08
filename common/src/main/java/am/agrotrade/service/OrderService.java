package am.agrotrade.service;

import am.agrotrade.model.Order;

import java.util.List;
import java.util.Optional;

public interface OrderService {

    void save(Order order);

    void delete(long orderId);

    List<Order> findAll();

    Optional<Order> findById(long orderId);
}
