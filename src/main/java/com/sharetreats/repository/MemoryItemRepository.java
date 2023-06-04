package com.sharetreats.repository;

import com.sharetreats.domain.item.Item;
import com.sharetreats.domain.item.ItemGrade;

import java.util.*;

import static com.sharetreats.support.PreConditions.*;
import static java.util.Objects.*;

public class MemoryItemRepository implements ItemRepository {
    private static Map<ItemGrade, List<Item>> RANDOM_BOX_ITEMS_MAP = new HashMap<>();

    @Override
    public boolean save(ItemGrade grade, List<Item> items) {
        validate(nonNull(items), "상품이 없습니다.");

        RANDOM_BOX_ITEMS_MAP.put(grade, items);

        return true;
    }

    @Override
    public Map<ItemGrade, List<Item>> findAll() {
        validate(RANDOM_BOX_ITEMS_MAP.size() > 0, "등록된 상품이 없습니다.");

        return RANDOM_BOX_ITEMS_MAP;
    }

    public static void clear() {
        RANDOM_BOX_ITEMS_MAP.clear();
    }
}
