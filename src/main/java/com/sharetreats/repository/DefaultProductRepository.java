package com.sharetreats.repository;

import com.sharetreats.domain.product.Product;
import com.sharetreats.domain.product.ProductGrade;

import java.util.*;

import static com.sharetreats.support.PreConditions.*;
import static java.util.Objects.*;

public class DefaultProductRepository implements ProductRepository {
    private static Map<ProductGrade, List<Product>> PRODUCT_MAP = new HashMap<>();

    @Override
    public boolean save(ProductGrade grade, List<Product> products) {
        validate(nonNull(products), "상품이 없습니다.");

        PRODUCT_MAP.put(grade, products);

        return true;
    }

    @Override
    public Map<ProductGrade, List<Product>> findAll() {
        validate(PRODUCT_MAP.size() > 0, "등록된 상품이 없습니다.");

        return PRODUCT_MAP;
    }

    @Override
    public void delete(Product product) {
        PRODUCT_MAP.get(product.getGrade()).remove(product);
    }

    public static void clear() {
        PRODUCT_MAP.clear();
    }
}
