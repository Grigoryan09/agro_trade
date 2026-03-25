package am.agrotrade.core.mapper;

import am.agrotrade.core.model.Product;
import am.agrotrade.common.dto.product.request.CreateProductRequest;
import am.agrotrade.common.dto.product.response.ProductInfoDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductMapper {

    Product toEntity(CreateProductRequest productRequest);

    ProductInfoDto toResponse(Product product);
}
