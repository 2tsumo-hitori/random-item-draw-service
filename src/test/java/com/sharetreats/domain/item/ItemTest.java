package com.sharetreats.domain.item;


import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.params.provider.EnumSource.Mode.EXCLUDE;

class ItemTest {
    private Item itemGradeA;
    private Item itemGradeB;

    @BeforeEach
    void setUp() {
        itemGradeA = new ItemGradeA("상품A");
        itemGradeB = new ItemGradeB("상품B");
    }

    @Test
    void 제품_생성_성공() {
        val itemGradeA = new ItemGradeA("상품A");

        assertThat(itemGradeA).isNotNull();
    }

    @ParameterizedTest
    @NullAndEmptySource
    void 제품_생성_실패__상품명이_비어있음(String name) {
        assertThatThrownBy(() -> new ItemGradeA(name)).isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @EnumSource(value = ItemGrade.class, names = {"BadItemType"}, mode = EXCLUDE)
    void 제품_가챠_성공() {
        assertThat(itemGradeA.isDraw());
        assertThat(itemGradeB.isDraw());
    }

    enum ItemGrade {
        A(90),
        B(10),
        BadItemType(101);

        private final int percentage;
        ItemGrade(int percentage) {
            this.percentage = percentage;
        }
        public int percent() {
            return percentage;
        }
    }
}