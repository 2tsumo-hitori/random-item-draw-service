package com.sharetreats.service.supportservice;

import com.sharetreats.domain.item.Item;
import com.sharetreats.domain.item.ItemGrade;
import com.sharetreats.domain.item.ItemGradeB;
import com.sharetreats.domain.member.Member;
import com.sharetreats.repository.ItemRepository;
import com.sharetreats.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import static com.sharetreats.support.PreConditions.validate;

@RequiredArgsConstructor
public class RandomItemDrawSupportService implements ItemDrawSupportService {
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    private Map<ItemGrade, List<Item>> notExpiredItems;
    private static final String OUT_OF_STOCK = "상품 재고가 부족해 게임을 종료합니다.";

    @Override
    public Map<ItemGrade, List<Item>> getNotExpiredItems(LocalDateTime startTime) {
        notExpiredItems = findNotExpiredItems(startTime);

        validate(isItemsCountZero(notExpiredItems), OUT_OF_STOCK);

        return notExpiredItems;
    }

    @Override
    public ItemDrawSupportService notExpiredItems(LocalDateTime startTime) {
        notExpiredItems = findNotExpiredItems(startTime);

        validate(isItemsCountZero(notExpiredItems), OUT_OF_STOCK);

        return this;
    }

    @Override
    public Map<ItemGrade, List<Item>> sortItems() {
        return new TreeMap<>(this.notExpiredItems);
    }

    @Override
    public boolean isDrawSuccess(Item randomItem, Member member) {
        if (randomItem instanceof ItemGradeB) {
            if (member.isMaxDrawCountOnItemB()) {
                return false;
            }
            member.draw();
        }

        return randomItem.isDrawn();
    }

    @Override
    public String acquireItem(Member member, Item randomItem) {
        memberRepository.saveItem(member, randomItem);

        return randomItem.prizePrint();
    }

    private boolean isItemsCountZero(Map<ItemGrade, List<Item>> items) {
        return items.size() > 0;
    }

    private Map<ItemGrade, List<Item>> findNotExpiredItems(LocalDateTime startTime) {
        return itemRepository.findAll()
                .values()
                .stream()
                .flatMap(List::stream)
                .filter(item -> item.getExpirationDate().isAfter(startTime))
                .collect(Collectors.groupingBy(Item::getGrade));
    }
}
