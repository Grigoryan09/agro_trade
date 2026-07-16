package am.agrotrade.common.dto.product;

import am.agrotrade.common.dto.media.MediaDto;
import am.agrotrade.common.dto.user.SellerInfoDto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record ProductInfoDto(

        long id,
        String name,
        String description,
        BigDecimal price,
        String category,
        String status,
        SellerInfoDto sellerInfoDto,
        LocalDateTime createdAt,
        List<MediaDto> media

) {

    public ProductInfoDto withMedia(List<MediaDto> media) {
        return new ProductInfoDto(id, name, description, price, category, status, sellerInfoDto, createdAt, media);
    }
}
