package am.agrotrade.service;

import am.agrotrade.dto.order.request.CreateOrderRequest;
import am.agrotrade.model.entity.Order;

import java.awt.print.Pageable;
import java.util.List;

public interface OrderService {

    void save(CreateOrderRequest orderRequest);

    List<Order> findAll(Pageable pageable);

    List<Order> findAllByManagerId(long managerId, Pageable pageable);

    Order findById(long orderId);
}
