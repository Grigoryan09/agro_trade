package am.agrotrade.core.mapper;

import am.agrotrade.common.dto.media.MediaDto;
import am.agrotrade.common.dto.organization.OrganizationDetailsDto;
import am.agrotrade.common.dto.product.ProductInfoDto;
import am.agrotrade.common.dto.product.request.CreateProductRequest;
import am.agrotrade.common.dto.product.request.UpdateProductRequest;
import am.agrotrade.common.dto.user.SellerInfoDto;
import am.agrotrade.core.model.Product;
import am.agrotrade.core.model.User;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-07-17T01:20:33+0400",
    comments = "version: 1.6.0, compiler: javac, environment: Java 21.0.11.0.11 (Alibaba)"
)
@Component
public class ProductMapperImpl implements ProductMapper {

    @Autowired
    private OrganizationMapper organizationMapper;

    @Override
    public Product toEntity(CreateProductRequest productRequest) {
        if ( productRequest == null ) {
            return null;
        }

        Product product = new Product();

        product.setName( productRequest.name() );
        product.setDescription( productRequest.description() );
        product.setPrice( productRequest.price() );
        product.setCategory( productRequest.category() );

        return product;
    }

    @Override
    public ProductInfoDto toDto(Product product) {
        if ( product == null ) {
            return null;
        }

        SellerInfoDto sellerInfoDto = null;
        long id = 0L;
        String name = null;
        String description = null;
        BigDecimal price = null;
        String category = null;
        String status = null;
        LocalDateTime createdAt = null;

        sellerInfoDto = toSellerDto( product.getSeller() );
        id = product.getId();
        name = product.getName();
        description = product.getDescription();
        price = product.getPrice();
        if ( product.getCategory() != null ) {
            category = product.getCategory().name();
        }
        if ( product.getStatus() != null ) {
            status = product.getStatus().name();
        }
        createdAt = product.getCreatedAt();

        List<MediaDto> media = null;

        ProductInfoDto productInfoDto = new ProductInfoDto( id, name, description, price, category, status, sellerInfoDto, createdAt, media );

        return productInfoDto;
    }

    @Override
    public SellerInfoDto toSellerDto(User seller) {
        if ( seller == null ) {
            return null;
        }

        String sellerId = null;
        String sellerName = null;
        OrganizationDetailsDto organization = null;

        sellerId = String.valueOf( seller.getId() );
        sellerName = seller.getName();
        organization = organizationMapper.toDto( seller.getOrganization() );

        SellerInfoDto sellerInfoDto = new SellerInfoDto( sellerId, sellerName, organization );

        return sellerInfoDto;
    }

    @Override
    public void updateEntityFromRequest(UpdateProductRequest request, Product product) {
        if ( request == null ) {
            return;
        }

        if ( request.name() != null ) {
            product.setName( request.name() );
        }
        if ( request.description() != null ) {
            product.setDescription( request.description() );
        }
        if ( request.price() != null ) {
            product.setPrice( request.price() );
        }
    }
}
