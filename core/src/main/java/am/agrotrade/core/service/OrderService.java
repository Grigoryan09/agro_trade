package am.agrotrade.core.service;

import am.agrotrade.common.dto.order.OrderDetailsDto;
import am.agrotrade.common.dto.order.request.CreateOrderRequest;
import am.agrotrade.common.dto.order.request.UpdateOrderStatusRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {

    OrderDetailsDto save(long buyerId, CreateOrderRequest request);

    OrderDetailsDto updateStatus(long managerId, UpdateOrderStatusRequest request);

    void delete(long managerId, long orderId);

    Page<OrderDetailsDto> findAll(Pageable pageable);

    OrderDetailsDto findById(long orderId);

    Page<OrderDetailsDto> findByManagerId(long managerId, Pageable pageable);
}
