package am.agrotrade.core.mapper;

import am.agrotrade.common.dto.order.OrderDetailsDto;
import am.agrotrade.common.dto.order.request.UpdateOrderStatusRequest;
import am.agrotrade.common.dto.product.ProductDetailsDto;
import am.agrotrade.common.dto.user.BaseUserInfoDto;
import am.agrotrade.common.dto.user.BuyerDetailsDto;
import am.agrotrade.common.dto.user.ManagerDetailsDto;
import am.agrotrade.common.dto.user.SellerDetailsDto;
import am.agrotrade.common.enums.OrderStatus;
import am.agrotrade.core.model.Order;
import am.agrotrade.core.model.Product;
import am.agrotrade.core.model.User;
import java.math.BigDecimal;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-07-17T01:20:33+0400",
    comments = "version: 1.6.0, compiler: javac, environment: Java 21.0.11.0.11 (Alibaba)"
)
@Component
public class OrderMapperImpl implements OrderMapper {

    @Override
    public OrderDetailsDto toDto(Order order) {
        if ( order == null ) {
            return null;
        }

        BuyerDetailsDto buyerDetailsDto = null;
        SellerDetailsDto sellerDetailsDto = null;
        ManagerDetailsDto managerDetailsDto = null;
        ProductDetailsDto productDetailsDto = null;
        long id = 0L;
        long quantity = 0L;
        BigDecimal totalPrice = null;
        OrderStatus orderStatus = null;
        long chatId = 0L;

        buyerDetailsDto = toBuyerDto( order.getBuyer() );
        sellerDetailsDto = toSellerDto( order.getSeller() );
        managerDetailsDto = toManagerDto( order.getManager() );
        productDetailsDto = toProductDto( order.getProduct() );
        id = order.getId();
        quantity = order.getQuantity();
        totalPrice = order.getTotalPrice();
        orderStatus = order.getOrderStatus();
        chatId = order.getChatId();

        OrderDetailsDto orderDetailsDto = new OrderDetailsDto( id, buyerDetailsDto, sellerDetailsDto, managerDetailsDto, productDetailsDto, quantity, totalPrice, orderStatus, chatId );

        return orderDetailsDto;
    }

    @Override
    public BuyerDetailsDto toBuyerDto(User buyer) {
        if ( buyer == null ) {
            return null;
        }

        String buyerId = null;
        BaseUserInfoDto baseUserInfoDto = null;

        buyerId = String.valueOf( buyer.getId() );
        baseUserInfoDto = userToBaseUserInfoDto( buyer );

        BuyerDetailsDto buyerDetailsDto = new BuyerDetailsDto( buyerId, baseUserInfoDto );

        return buyerDetailsDto;
    }

    @Override
    public SellerDetailsDto toSellerDto(User seller) {
        if ( seller == null ) {
            return null;
        }

        String sellerId = null;
        BaseUserInfoDto baseUserInfoDto = null;

        sellerId = String.valueOf( seller.getId() );
        baseUserInfoDto = userToBaseUserInfoDto( seller );

        SellerDetailsDto sellerDetailsDto = new SellerDetailsDto( sellerId, baseUserInfoDto );

        return sellerDetailsDto;
    }

    @Override
    public ManagerDetailsDto toManagerDto(User manager) {
        if ( manager == null ) {
            return null;
        }

        String managerId = null;
        BaseUserInfoDto baseUserInfoDto = null;

        managerId = String.valueOf( manager.getId() );
        baseUserInfoDto = userToBaseUserInfoDto( manager );

        ManagerDetailsDto managerDetailsDto = new ManagerDetailsDto( managerId, baseUserInfoDto );

        return managerDetailsDto;
    }

    @Override
    public ProductDetailsDto toProductDto(Product product) {
        if ( product == null ) {
            return null;
        }

        String productName = null;
        String productType = null;

        productName = product.getName();
        if ( product.getCategory() != null ) {
            productType = product.getCategory().name();
        }

        ProductDetailsDto productDetailsDto = new ProductDetailsDto( productName, productType );

        return productDetailsDto;
    }

    @Override
    public Order toEntity(User buyer, User seller, User manager, Product product, long quantity, BigDecimal totalPrice) {
        if ( buyer == null && seller == null && manager == null && product == null && totalPrice == null ) {
            return null;
        }

        Order.OrderBuilder order = Order.builder();

        order.buyer( buyer );
        order.seller( seller );
        order.manager( manager );
        order.product( product );
        order.quantity( quantity );
        order.totalPrice( totalPrice );
        order.orderStatus( OrderStatus.PENDING );

        return order.build();
    }

    @Override
    public void updateOrderFromRequest(UpdateOrderStatusRequest request, Order order) {
        if ( request == null ) {
            return;
        }

        if ( request.orderStatus() != null ) {
            order.setOrderStatus( request.orderStatus() );
        }
    }

    protected BaseUserInfoDto userToBaseUserInfoDto(User user) {
        if ( user == null ) {
            return null;
        }

        BaseUserInfoDto baseUserInfoDto = new BaseUserInfoDto();

        baseUserInfoDto.setName( user.getName() );
        baseUserInfoDto.setSurname( user.getSurname() );
        baseUserInfoDto.setGender( user.getGender() );
        baseUserInfoDto.setBirthDate( user.getBirthDate() );
        baseUserInfoDto.setAddress( user.getAddress() );
        baseUserInfoDto.setEmail( user.getEmail() );
        baseUserInfoDto.setPhoneNumber( user.getPhoneNumber() );
        baseUserInfoDto.setUsername( user.getUsername() );

        return baseUserInfoDto;
    }
}
