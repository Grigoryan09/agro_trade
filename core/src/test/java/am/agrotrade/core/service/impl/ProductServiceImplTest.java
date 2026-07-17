package am.agrotrade.core.service.impl;

import am.agrotrade.common.dto.media.MediaDto;
import am.agrotrade.common.dto.product.ProductInfoDto;
import am.agrotrade.common.dto.product.request.CreateProductRequest;
import am.agrotrade.common.dto.product.request.UpdateProductRequest;
import am.agrotrade.common.enums.CategoryProduct;
import am.agrotrade.common.enums.EntityType;
import am.agrotrade.common.enums.ProductStatus;
import am.agrotrade.core.exception.ResourceNotFoundException;
import am.agrotrade.core.mapper.ProductMapper;
import am.agrotrade.core.model.Product;
import am.agrotrade.core.model.User;
import am.agrotrade.core.repository.ProductRepository;
import am.agrotrade.core.repository.UserRepository;
import am.agrotrade.core.service.MediaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    private static final long SELLER_ID = 7L;
    private static final long PRODUCT_ID = 42L;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProductMapper productMapper;

    @Mock
    private MediaService mediaService;

    @InjectMocks
    private ProductServiceImpl productService;

    private User seller;
    private List<MediaDto> media;

    @BeforeEach
    void setUp() {
        seller = new User();
        seller.setId(SELLER_ID);

        media = List.of(new MediaDto(1L, "photo.png", "image/png", "products", PRODUCT_ID, "/media/1"));
    }

    private ProductInfoDto dtoWithoutMedia() {
        return new ProductInfoDto(PRODUCT_ID, "Tomatoes", "Fresh",
                BigDecimal.TEN, CategoryProduct.SEEDS.name(), ProductStatus.AVAILABLE.name(), null,
                LocalDateTime.now(), null);
    }

    @Nested
    @DisplayName("create")
    class Create {

        @Test
        @DisplayName("saves the product with AVAILABLE status, the seller set, and returns it enriched with media")
        void create_persistsAndEnrichesWithMedia() {
            CreateProductRequest request = new CreateProductRequest(
                    "Tomatoes", "Fresh", BigDecimal.TEN, CategoryProduct.SEEDS);

            Product mapped = new Product();
            Product saved = new Product();
            saved.setId(PRODUCT_ID);

            ProductInfoDto mappedDto = dtoWithoutMedia();

            when(userRepository.findById(SELLER_ID)).thenReturn(java.util.Optional.of(seller));
            when(productMapper.toEntity(request)).thenReturn(mapped);
            when(productRepository.save(mapped)).thenReturn(saved);
            when(productMapper.toDto(saved)).thenReturn(mappedDto);
            when(mediaService.findAllByEntity(PRODUCT_ID, EntityType.PRODUCT)).thenReturn(media);

            ProductInfoDto result = productService.create(SELLER_ID, request);

            assertThat(result.media()).isEqualTo(media);

            ArgumentCaptor<Product> toSave = ArgumentCaptor.forClass(Product.class);
            verify(productRepository).save(toSave.capture());
            Product captured = toSave.getValue();
            assertThat(captured.getStatus()).isEqualTo(ProductStatus.AVAILABLE);
            assertThat(captured.getSeller()).isSameAs(seller);
            assertThat(captured.getCreatedAt()).isNotNull();
        }

        @Test
        @DisplayName("throws ResourceNotFoundException and never saves when the seller does not exist")
        void create_sellerMissing_throws() {
            CreateProductRequest request = new CreateProductRequest(
                    "Tomatoes", "Fresh", BigDecimal.TEN, CategoryProduct.SEEDS);

            when(userRepository.findById(SELLER_ID)).thenReturn(java.util.Optional.empty());

            assertThatThrownBy(() -> productService.create(SELLER_ID, request))
                    .isInstanceOf(ResourceNotFoundException.class)
                    .hasMessage("User not found");

            verify(productRepository, never()).save(any());
            verifyNoInteractions(mediaService);
        }
    }

    @Nested
    @DisplayName("update")
    class Update {

        @Test
        @DisplayName("applies the mapper to the existing entity and returns it enriched with media")
        void update_appliesChanges() {
            UpdateProductRequest request = new UpdateProductRequest(
                    "New name", "New desc", BigDecimal.ONE);

            Product existing = new Product();
            existing.setId(PRODUCT_ID);

            when(productRepository.findBySellerIdAndProductId(SELLER_ID, PRODUCT_ID))
                    .thenReturn(java.util.Optional.of(existing));
            when(productMapper.toDto(existing)).thenReturn(dtoWithoutMedia());
            when(mediaService.findAllByEntity(PRODUCT_ID, EntityType.PRODUCT)).thenReturn(media);

            ProductInfoDto result = productService.update(SELLER_ID, PRODUCT_ID, request);

            assertThat(result.media()).isEqualTo(media);
            verify(productMapper).updateEntityFromRequest(request, existing);
        }

        @Test
        @DisplayName("throws ResourceNotFoundException when the product is not owned by the seller")
        void update_notFound_throws() {
            UpdateProductRequest request = new UpdateProductRequest(
                    "New name", "New desc", BigDecimal.ONE);

            when(productRepository.findBySellerIdAndProductId(SELLER_ID, PRODUCT_ID))
                    .thenReturn(java.util.Optional.empty());

            assertThatThrownBy(() -> productService.update(SELLER_ID, PRODUCT_ID, request))
                    .isInstanceOf(ResourceNotFoundException.class);

            verify(productMapper, never()).updateEntityFromRequest(any(), any());
        }
    }

    @Nested
    @DisplayName("delete")
    class Delete {

        @Test
        @DisplayName("soft-deletes by setting the status to DELETED instead of removing the row")
        void delete_softDeletes() {
            Product existing = new Product();
            existing.setId(PRODUCT_ID);
            existing.setStatus(ProductStatus.AVAILABLE);

            when(productRepository.findBySellerIdAndProductId(SELLER_ID, PRODUCT_ID))
                    .thenReturn(java.util.Optional.of(existing));

            productService.delete(SELLER_ID, PRODUCT_ID);

            assertThat(existing.getStatus()).isEqualTo(ProductStatus.DELETED);
            verify(productRepository, never()).delete(any());
        }

        @Test
        @DisplayName("throws ResourceNotFoundException when the product is not owned by the seller")
        void delete_notFound_throws() {
            when(productRepository.findBySellerIdAndProductId(SELLER_ID, PRODUCT_ID))
                    .thenReturn(java.util.Optional.empty());

            assertThatThrownBy(() -> productService.delete(SELLER_ID, PRODUCT_ID))
                    .isInstanceOf(ResourceNotFoundException.class);
        }
    }

    @Nested
    @DisplayName("findById")
    class FindById {

        @Test
        @DisplayName("returns the product enriched with media when it exists and is not deleted")
        void findById_returnsDto() {
            Product existing = new Product();
            existing.setId(PRODUCT_ID);
            existing.setStatus(ProductStatus.AVAILABLE);

            when(productRepository.findById(PRODUCT_ID)).thenReturn(java.util.Optional.of(existing));
            when(productMapper.toDto(existing)).thenReturn(dtoWithoutMedia());
            when(mediaService.findAllByEntity(PRODUCT_ID, EntityType.PRODUCT)).thenReturn(media);

            ProductInfoDto result = productService.findById(PRODUCT_ID);

            assertThat(result.media()).isEqualTo(media);
        }

        @Test
        @DisplayName("throws ResourceNotFoundException for a product whose status is DELETED")
        void findById_deleted_throws() {
            Product deleted = new Product();
            deleted.setId(PRODUCT_ID);
            deleted.setStatus(ProductStatus.DELETED);

            when(productRepository.findById(PRODUCT_ID)).thenReturn(java.util.Optional.of(deleted));

            assertThatThrownBy(() -> productService.findById(PRODUCT_ID))
                    .isInstanceOf(ResourceNotFoundException.class);

            verifyNoInteractions(mediaService);
        }

        @Test
        @DisplayName("throws ResourceNotFoundException when the product does not exist")
        void findById_missing_throws() {
            when(productRepository.findById(PRODUCT_ID)).thenReturn(java.util.Optional.empty());

            assertThatThrownBy(() -> productService.findById(PRODUCT_ID))
                    .isInstanceOf(ResourceNotFoundException.class);
        }
    }

    @Nested
    @DisplayName("findAllByStatusNot")
    class FindAll {

        @Test
        @DisplayName("excludes DELETED products and maps every result with its media")
        void findAllByStatusNot_mapsPage() {
            Pageable pageable = PageRequest.of(0, 10);

            Product p1 = new Product();
            p1.setId(1L);
            Product p2 = new Product();
            p2.setId(2L);
            Page<Product> page = new PageImpl<>(List.of(p1, p2));

            when(productRepository.findAllByStatusNot(ProductStatus.DELETED, pageable)).thenReturn(page);
            when(productMapper.toDto(any(Product.class))).thenReturn(dtoWithoutMedia());
            when(mediaService.findAllByEntity(eq(1L), eq(EntityType.PRODUCT))).thenReturn(media);
            when(mediaService.findAllByEntity(eq(2L), eq(EntityType.PRODUCT))).thenReturn(List.of());

            List<ProductInfoDto> result = productService.findAllByStatusNot(pageable);

            assertThat(result).hasSize(2);
            verify(mediaService, times(2)).findAllByEntity(anyLong(), eq(EntityType.PRODUCT));
        }
    }
}