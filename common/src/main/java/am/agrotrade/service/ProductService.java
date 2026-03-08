package am.agrotrade.service;

import am.agrotrade.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    void save(Product product);

    void delete(long productId);

    List<Product> findAll();

    Optional<Product> findById(long productId);
}
