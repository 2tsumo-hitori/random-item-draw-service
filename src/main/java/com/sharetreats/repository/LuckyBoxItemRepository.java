package com.sharetreats.repository;

import com.sharetreats.domain.luckyboxitem.LuckyBoxItem;
import com.sharetreats.domain.luckyboxitem.LuckyBoxItemGrade;

import java.util.List;
import java.util.Map;

public interface LuckyBoxItemRepository {
    boolean save(LuckyBoxItemGrade grade, List<LuckyBoxItem> items);
    Map<LuckyBoxItemGrade, List<LuckyBoxItem>> findAll();
}
