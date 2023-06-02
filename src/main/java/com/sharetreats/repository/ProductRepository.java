package com.sharetreats.repository;

import com.sharetreats.domain.product.Product;
import com.sharetreats.domain.product.ProductGrade;

import java.util.List;
import java.util.Map;

public interface ProductRepository {
    boolean save(ProductGrade grade, List<Product> product);

    Map<ProductGrade, List<Product>> findAll();

    void delete(Product product);
}
