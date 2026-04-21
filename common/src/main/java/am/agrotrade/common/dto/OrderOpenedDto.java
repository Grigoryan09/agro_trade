package am.agrotrade.common.dto;

import lombok.Builder;

@Builder
public record OrderOpenedDto(

        String orderUrl,
        String productName) {
}