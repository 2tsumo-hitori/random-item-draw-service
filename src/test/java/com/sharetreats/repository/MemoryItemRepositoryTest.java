package com.sharetreats.repository;

import com.sharetreats.domain.item.Item;
import com.sharetreats.domain.item.ItemGradeA;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.sharetreats.domain.item.ItemGrade.A;
import static org.assertj.core.api.Assertions.*;

class MemoryItemRepositoryTest {
    ItemRepository itemRepository;
    List<Item> savedItems = new ArrayList<>();

    @BeforeEach
    void setUp() {
        itemRepository = new MemoryItemRepository();

        savedItems.add(new ItemGradeA("상품A"));
        savedItems.add(new ItemGradeA("상품A"));
    }

    @AfterEach
    void after() {
        if (itemRepository instanceof MemoryItemRepository) {
            MemoryItemRepository.clear();
        }
    }

    @Test
    void 상품_저장_성공() {
        assertThat(itemRepository.save(A, savedItems)).isTrue();
    }

    @Test
    void 상품_저장_실패__제품이_널() {
        assertThatThrownBy(() -> itemRepository.save(null, null)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 상품_전체조회_성공() {
        itemRepository.save(A, savedItems);
        assertThat(itemRepository.findAll().size()).isEqualTo(1);
    }

    @Test
    void 상품_전체조회_실패__조회된_결과가_없음() {
        assertThatThrownBy(() -> itemRepository.findAll()).isInstanceOf(IllegalArgumentException.class);
    }
}