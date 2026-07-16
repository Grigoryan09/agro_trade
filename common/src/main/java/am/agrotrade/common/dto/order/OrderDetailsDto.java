package am.agrotrade.common.dto.order;

import am.agrotrade.common.dto.product.ProductDetailsDto;
import am.agrotrade.common.dto.user.BuyerDetailsDto;
import am.agrotrade.common.dto.user.ManagerDetailsDto;
import am.agrotrade.common.dto.user.SellerDetailsDto;
import am.agrotrade.common.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record OrderDetailsDto(

        long id,
        BuyerDetailsDto buyerDetailsDto,
        SellerDetailsDto sellerDetailsDto,
        ManagerDetailsDto managerDetailsDto,
        ProductDetailsDto productDetailsDto,
        long quantity,
        BigDecimal totalPrice,
        OrderStatus orderStatus,
        long chatId
) {
}
