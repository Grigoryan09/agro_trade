package am.agrotrade.core.mapper;

import am.agrotrade.common.dto.order.OrderDetailsDto;
import am.agrotrade.common.dto.order.request.UpdateOrderStatusRequest;
import am.agrotrade.common.dto.product.ProductDetailsDto;
import am.agrotrade.common.dto.user.BuyerDetailsDto;
import am.agrotrade.common.dto.user.ManagerDetailsDto;
import am.agrotrade.common.dto.user.SellerDetailsDto;
import am.agrotrade.core.model.Order;
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
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface OrderMapper {

    @Mapping(target = "buyerDetailsDto", source = "buyer")
    @Mapping(target = "sellerDetailsDto", source = "seller")
    @Mapping(target = "managerDetailsDto", source = "manager")
    @Mapping(target = "productDetailsDto", source = "product")
    OrderDetailsDto toDto(Order order);

    @Mapping(target = "buyerId", source = "id")
    @Mapping(target = "baseUserInfoDto", source = ".")
    BuyerDetailsDto toBuyerDto(User buyer);

    @Mapping(target = "sellerId", source = "id")
    @Mapping(target = "baseUserInfoDto", source = ".")
    SellerDetailsDto toSellerDto(User seller);

    @Mapping(target = "managerId", source = "id")
    @Mapping(target = "baseUserInfoDto", source = ".")
    ManagerDetailsDto toManagerDto(User manager);

    @Mapping(target = "productName", source = "product.name")
    @Mapping(target = "productType", source = "product.category")
    ProductDetailsDto toProductDto(Product product);

    @Mapping(target = "orderStatus", source = "orderStatus")
    void updateOrderFromRequest(UpdateOrderStatusRequest request, @MappingTarget Order order);
}
