package am.agrotrade.core.repository;

import am.agrotrade.common.enums.ProductStatus;
import am.agrotrade.core.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(value = "SELECT * FROM product p WHERE p.seller_id = :sellerId AND p.id = :productId",
            nativeQuery = true)
    Optional<Product> findBySellerIdAndProductId(
            @Param("sellerId") long sellerId,
            @Param("productId") long productId
    );

    @EntityGraph(attributePaths = {"seller", "seller.organization"})
    Page<Product> findAll(Pageable pageable);

    Page<Product> findAllBySellerId(Long sellerId, Pageable pageable);

    Page<Product> findAllBySellerIdAndStatusNot(Long sellerId, ProductStatus status, Pageable pageable);

    @EntityGraph(attributePaths = {"seller", "seller.organization"})
    Page<Product> findAllByStatusNot(ProductStatus status, Pageable pageable);

    boolean existsByIdAndSellerId(Long productId, Long sellerId);
}
