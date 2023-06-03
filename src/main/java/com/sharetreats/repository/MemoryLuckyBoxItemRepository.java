package com.sharetreats.repository;

import com.sharetreats.domain.luckyboxitem.LuckyBoxItem;
import com.sharetreats.domain.luckyboxitem.LuckyBoxItemGrade;

import java.util.*;

import static com.sharetreats.support.PreConditions.*;
import static java.util.Objects.*;

public class MemoryLuckyBoxItemRepository implements LuckyBoxItemRepository {
    private static Map<LuckyBoxItemGrade, List<LuckyBoxItem>> LUCKY_BOX_ITEMS_MAP = new HashMap<>();

    @Override
    public boolean save(LuckyBoxItemGrade grade, List<LuckyBoxItem> items) {
        validate(nonNull(items), "상품이 없습니다.");

        LUCKY_BOX_ITEMS_MAP.put(grade, items);

        return true;
    }

    @Override
    public Map<LuckyBoxItemGrade, List<LuckyBoxItem>> findAll() {
        validate(LUCKY_BOX_ITEMS_MAP.size() > 0, "등록된 상품이 없습니다.");

        return LUCKY_BOX_ITEMS_MAP;
    }

    public static void clear() {
        LUCKY_BOX_ITEMS_MAP.clear();
    }
}
