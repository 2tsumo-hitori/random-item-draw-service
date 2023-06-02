package com.sharetreats.repository;

import com.sharetreats.domain.product.Product;
import com.sharetreats.domain.product.ProductA;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.sharetreats.domain.product.ProductGrade.A;
import static org.assertj.core.api.Assertions.*;

class DefaultProductRepositoryTest {
    ProductRepository productRepository;
    List<Product> savedProductList = new ArrayList<>();

    @BeforeEach
    void setUp() {
        productRepository = new DefaultProductRepository();

        savedProductList.add(new ProductA("상품A"));
        savedProductList.add(new ProductA("상품A"));
    }

    @AfterEach
    void after() {
        if (productRepository instanceof DefaultProductRepository) {
            DefaultProductRepository.clear();
        }
    }

    @Test
    void 상품_저장_성공() {
        assertThat(productRepository.save(A, savedProductList)).isTrue();
    }

    @Test
    void 상품_저장_실패__제품이_널() {
        assertThatThrownBy(() -> productRepository.save(null, null)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 상품_전체조회_성공() {
        productRepository.save(A, savedProductList);
        assertThat(productRepository.findAll().size()).isEqualTo(1);
    }

    @Test
    void 상품_전체조회_실패__조회된_결과가_없음() {
        assertThatThrownBy(() -> productRepository.findAll()).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 상품_삭제_성공() {
        productRepository.save(A, savedProductList);

        List<Product> productList = productRepository.findAll().get(A);
        assertThat(productList.size()).isEqualTo(2);

        productRepository.delete(productList.get(0));
        assertThat(productList.size()).isEqualTo(1);
    }
}