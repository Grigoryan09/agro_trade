package am.agrotrade.common.dto.order;

import am.agrotrade.common.dto.ChatDetailDto;
import am.agrotrade.common.dto.product.ProductDetailsDto;
import am.agrotrade.common.dto.user.BuyerDetailsDto;
import am.agrotrade.common.dto.user.ManagerDetailsDto;
import am.agrotrade.common.dto.user.SellerDetailsDto;
import am.agrotrade.common.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record OrderDetailsDto(

        BuyerDetailsDto buyerDetailsDto,
        SellerDetailsDto sellerDetailsDto,
        ManagerDetailsDto managerDetailsDto,
        ProductDetailsDto productDetailsDto,
        ChatDetailDto chatDetailDto,
        long quantity,
        BigDecimal totalPrice,
        OrderStatus orderStatus
) {
}
