package am.agrotrade.common.dto.order.response;

import am.agrotrade.common.dto.order.OrderDetailsDto;

import java.util.List;

public record OrderResponse(List<OrderDetailsDto> orderDetailsDto) {
}
