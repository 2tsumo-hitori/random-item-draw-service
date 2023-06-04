package com.sharetreats.repository;

import com.sharetreats.domain.item.Item;
import com.sharetreats.domain.item.ItemGrade;

import java.util.List;
import java.util.Map;

public interface ItemRepository {
    boolean save(ItemGrade grade, List<Item> items);
    Map<ItemGrade, List<Item>> findAll();
}
