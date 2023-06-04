package com.sharetreats.service.supportservice;

import com.sharetreats.domain.item.Item;
import com.sharetreats.domain.item.ItemGrade;
import com.sharetreats.domain.member.Member;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface ItemDrawSupportService {
    Map<ItemGrade, List<Item>> getNotExpiredItems(LocalDateTime startTime);

    ItemDrawSupportService notExpiredItems(LocalDateTime startTime);

    Map<ItemGrade, List<Item>> sortItems();

    boolean isDrawSuccess(Item randomItem, Member member);

    String acquireItem(Member member, Item randomItem);
}
