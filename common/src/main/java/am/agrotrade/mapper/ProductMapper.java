package am.agrotrade.mapper;

import am.agrotrade.dto.product.request.CreateProductRequest;
import am.agrotrade.dto.product.response.ProductInfoDto;
import am.agrotrade.model.entity.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    Product toEntity(CreateProductRequest productRequest);

    ProductInfoDto toResponse(Product product);
}
