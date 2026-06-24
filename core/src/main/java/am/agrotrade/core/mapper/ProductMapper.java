package am.agrotrade.core.mapper;

import am.agrotrade.common.dto.product.ProductInfoDto;
import am.agrotrade.common.dto.product.request.CreateProductRequest;
import am.agrotrade.common.dto.product.request.UpdateProductRequest;
import am.agrotrade.common.dto.user.SellerInfoDto;
import am.agrotrade.core.model.Product;
import am.agrotrade.core.model.User;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {OrganizationMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ProductMapper {

    Product toEntity(CreateProductRequest productRequest);


    @Mapping(target = "sellerInfoDto", source = "seller")
    ProductInfoDto toDto(Product product);

    @Mapping(target = "sellerId", source = "seller.id")
    @Mapping(target = "sellerName", source = "seller.name")
    @Mapping(target = "organization", source = "seller.organization")
    SellerInfoDto toSellerDto(User seller);

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "seller", ignore = true)
    void updateEntityFromRequest(UpdateProductRequest request, @MappingTarget Product product);

}
