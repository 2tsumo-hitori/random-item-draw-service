package com.sharetreats.domain.luckyboxitem;


import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.params.provider.EnumSource.Mode.EXCLUDE;

class LuckyBoxItemTest {
    private LuckyBoxItem luckyBoxItemGradeA;
    private LuckyBoxItem luckyBoxItemGradeB;

    @BeforeEach
    void setUp() {
        luckyBoxItemGradeA = new LuckyBoxItemGradeA("상품A");
        luckyBoxItemGradeB = new LuckyBoxItemGradeB("상품B");
    }

    @Test
    void 제품_생성_성공() {
        val luckyBoxItemGradeA = new LuckyBoxItemGradeA("상품A");

        assertThat(luckyBoxItemGradeA).isNotNull();
    }

    @ParameterizedTest
    @NullAndEmptySource
    void 제품_생성_실패__상품명이_비어있음(String name) {
        assertThatThrownBy(() -> new LuckyBoxItemGradeA(name)).isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @EnumSource(value = LuckyBoxItemGrade.class, names = {"BadItemType"}, mode = EXCLUDE)
    void 제품_가챠_성공() {
        assertThat(luckyBoxItemGradeA.drawLuckyBox());
        assertThat(luckyBoxItemGradeB.drawLuckyBox());
    }

    enum LuckyBoxItemGrade {
        A(90),
        B(10),
        BadItemType(101);

        private final int percent;
        LuckyBoxItemGrade(int percent) {
            this.percent = percent;
        }
        public int percent() {
            return percent;
        }
    }
}