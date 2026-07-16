package am.agrotrade.common.dto.document;

import am.agrotrade.common.dto.product.ProductDetailsDto;

public record OrderDocumentGenerateRequest(

        long orderId,
        String createdAt,

        BuyerInfoDto buyerInfoDto,
        SellerInfoDto sellerInfoDto,
        ManagerInfoDto managerInfoDto,
        ProductDetailsDto productDto,

        long quantity,
        String totalPrice,

        long chatId,
        long senderUserId
) {
}
