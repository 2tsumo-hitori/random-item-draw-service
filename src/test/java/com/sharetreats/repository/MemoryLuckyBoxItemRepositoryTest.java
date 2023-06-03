package com.sharetreats.repository;

import com.sharetreats.domain.luckyboxitem.LuckyBoxItem;
import com.sharetreats.domain.luckyboxitem.LuckyBoxItemGradeA;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.sharetreats.domain.luckyboxitem.LuckyBoxItemGrade.A;
import static org.assertj.core.api.Assertions.*;

class MemoryLuckyBoxItemRepositoryTest {
    LuckyBoxItemRepository luckyBoxItemRepository;
    List<LuckyBoxItem> savedItems = new ArrayList<>();

    @BeforeEach
    void setUp() {
        luckyBoxItemRepository = new MemoryLuckyBoxItemRepository();

        savedItems.add(new LuckyBoxItemGradeA("상품A"));
        savedItems.add(new LuckyBoxItemGradeA("상품A"));
    }

    @AfterEach
    void after() {
        if (luckyBoxItemRepository instanceof MemoryLuckyBoxItemRepository) {
            MemoryLuckyBoxItemRepository.clear();
        }
    }

    @Test
    void 상품_저장_성공() {
        assertThat(luckyBoxItemRepository.save(A, savedItems)).isTrue();
    }

    @Test
    void 상품_저장_실패__제품이_널() {
        assertThatThrownBy(() -> luckyBoxItemRepository.save(null, null)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 상품_전체조회_성공() {
        luckyBoxItemRepository.save(A, savedItems);
        assertThat(luckyBoxItemRepository.findAll().size()).isEqualTo(1);
    }

    @Test
    void 상품_전체조회_실패__조회된_결과가_없음() {
        assertThatThrownBy(() -> luckyBoxItemRepository.findAll()).isInstanceOf(IllegalArgumentException.class);
    }
}