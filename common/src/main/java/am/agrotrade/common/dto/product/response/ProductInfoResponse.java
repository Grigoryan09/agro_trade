package am.agrotrade.common.dto.product.response;

import am.agrotrade.common.dto.product.ProductInfoDto;

import java.util.List;

public record ProductInfoResponse(List<ProductInfoDto> productInfoDto) {
}
