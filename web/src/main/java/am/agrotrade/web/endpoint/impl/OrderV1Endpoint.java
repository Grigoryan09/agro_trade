package am.agrotrade.web.endpoint.impl;

import am.agrotrade.common.dto.order.request.CreateOrderRequest;
import am.agrotrade.common.dto.order.request.UpdateOrderStatusRequest;
import am.agrotrade.common.dto.order.response.OrderResponse;
import am.agrotrade.core.service.OrderService;
import am.agrotrade.web.endpoint.OrderV1API;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderV1Endpoint implements OrderV1API {

    private final OrderService orderService;

    @Override
    public OrderResponse getAll(Pageable pageable) {
        return new OrderResponse(orderService.findAll(pageable));
    }

    @Override
    public OrderResponse getMyOrders(long managerId, Pageable pageable) {
        return new OrderResponse(orderService.findByManagerId(managerId, pageable));
    }

    @Override
    public OrderResponse getById(long orderId) {
        return new OrderResponse(List.of(orderService.findById(orderId)));
    }

    @Override
    public OrderResponse createOrder(long buyerId, CreateOrderRequest request) {
       return new OrderResponse(List.of(orderService.save(buyerId, request)));
    }

    @Override
    public OrderResponse updateStatus(long id, UpdateOrderStatusRequest request) {
        return new OrderResponse(List.of(orderService.updateStatus(id, request)));
    }

    @Override
    public void delete(long managerId, long id) {
        orderService.delete(managerId, id);
    }
}
