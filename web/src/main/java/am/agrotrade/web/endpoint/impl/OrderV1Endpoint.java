package am.agrotrade.web.endpoint.impl;

import am.agrotrade.common.dto.order.request.CreateOrderRequest;
import am.agrotrade.common.dto.order.request.UpdateOrderStatusRequest;
import am.agrotrade.common.dto.order.response.OrderResponse;
import am.agrotrade.core.service.OrderService;
import am.agrotrade.web.endpoint.OrderV1API;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderV1Endpoint implements OrderV1API {

    private final OrderService orderService;

    @Override
    public Page<OrderResponse> getAll(Pageable pageable) {
        return orderService.findAll(pageable).map(OrderResponse::new);
    }

    @Override
    public Page<OrderResponse> getMyOrders(long managerId, Pageable pageable) {
        return orderService.findByManagerId(managerId, pageable).map(OrderResponse::new);
    }

    @Override
    public OrderResponse getById(long orderId) {
        return new OrderResponse(orderService.findById(orderId));
    }

    @Override
    public OrderResponse createOrder(long buyerId, CreateOrderRequest request) {
        return new OrderResponse(orderService.save(buyerId, request));
    }

    @Override
    public OrderResponse updateStatus(long id, UpdateOrderStatusRequest request) {
        return new OrderResponse(orderService.updateStatus(id, request));
    }

    @Override
    public void delete(long managerId, long id) {
        orderService.delete(managerId, id);
    }
}
