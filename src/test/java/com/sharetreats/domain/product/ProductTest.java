package com.sharetreats.domain.product;


import lombok.val;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.params.provider.EnumSource.Mode.EXCLUDE;
import static org.junit.jupiter.params.provider.EnumSource.Mode.INCLUDE;

class ProductTest {
    private Product productA;
    private Product productB;

    @BeforeEach
    void setUp() {
        productA = new ProductA("상품A");
        productB = new ProductB("상품B");
    }

    @Test
    void 제품_생성_성공() {
        val productA = new ProductA("상품A");

        assertThat(productA).isNotNull();
    }

    @ParameterizedTest
    @NullAndEmptySource
    void 제품_생성_실패__상품명이_비어있음(String name) {
        assertThatThrownBy(() -> new ProductA(name)).isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @EnumSource(value = ProductGrade.class, names = {"BadProductType"}, mode = EXCLUDE)
    void 제품_가챠_성공() {
        assertThat(productA.drawingPercent());
        assertThat(productB.drawingPercent());
    }

    @ParameterizedTest
    @EnumSource(value = ProductGrade.class, names = {"BadProductType"}, mode = INCLUDE)
    void 제품_가챠_실패_확률이_100을_넘어감() {
        assertThatThrownBy(() -> productA.drawingPercent()).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> productB.drawingPercent()).isInstanceOf(IllegalArgumentException.class);
    }


    // 클래스 빼기
    enum ProductGrade {
        A(90),
        B(10),
        BadProductType(101);

        private final int percent;
        ProductGrade(int percent) {
            this.percent = percent;
        }
        public int percent() {
            return percent;
        }
    }
}