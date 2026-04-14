package am.agrotrade.web.endpoint;

import am.agrotrade.common.dto.order.request.CreateOrderRequest;
import am.agrotrade.common.dto.order.request.UpdateOrderStatusRequest;
import am.agrotrade.common.dto.order.response.OrderResponse;
import am.agrotrade.web.infrastructure.annotation.CurrentUserId;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/agro-trade-service/api/v1/order")
public interface OrderV1API {

    @GetMapping
    Page<OrderResponse> getAll(Pageable pageable);

    @GetMapping("/my")
    Page<OrderResponse> getMyOrders(
            @CurrentUserId long managerId,
            Pageable pageable);


    @GetMapping("/{id}")
    OrderResponse getById(@PathVariable long id);

    @PostMapping
    OrderResponse createOrder(
            @CurrentUserId long managerId,
            @Valid @RequestBody CreateOrderRequest request
            );

    @PutMapping("/{id}")
    OrderResponse updateStatus(
            @PathVariable long id,
            @Valid @RequestBody UpdateOrderStatusRequest request);

    @DeleteMapping("/{id}")
    void delete(
            @CurrentUserId long managerId,
            @PathVariable long id
    );
}
