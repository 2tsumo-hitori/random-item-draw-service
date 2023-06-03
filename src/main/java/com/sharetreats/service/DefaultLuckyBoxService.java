package com.sharetreats.service;

import com.sharetreats.domain.member.Member;
import com.sharetreats.domain.luckyboxitem.LuckyBoxItem;
import com.sharetreats.domain.luckyboxitem.LuckyBoxItemGradeB;
import com.sharetreats.domain.luckyboxitem.LuckyBoxItemGrade;
import com.sharetreats.repository.MemberRepository;
import com.sharetreats.repository.LuckyBoxItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import static com.sharetreats.support.PreConditions.*;

@RequiredArgsConstructor
public class DefaultLuckyBoxService implements LuckyBoxService {

    private final MemberRepository memberRepository;
    private final LuckyBoxItemRepository luckyBoxItemRepository;
    private static final String FAILED_DRAW_MESSAGE = "꽝";
    private static final String OUT_OF_STOCK = "상품 재고가 부족해 게임을 종료합니다.";

    @Override
    public void charge(Member member, int money) {
        memberRepository.chargeMoney(member, money);
    }

    @Override
    public List<String> draw(Member member, int count, LocalDateTime startTime) {
        memberRepository.resetDrawCount(member);

        val resultPrints = new ArrayList<String>();

        val notExpiredItems = getNotExpiredItems(startTime);

        for (int i = 0; i < count; i ++) {
            memberRepository.spendMoney(member);

            boolean isDrawSuccess = false;

            for (LuckyBoxItemGrade grade : LuckyBoxItemGrade.values()) {
                val items = notExpiredItems.get(grade);

                validate(isItemsCountZero(notExpiredItems), OUT_OF_STOCK);

                val randomItem = items.get(new Random().nextInt(items.size()));

                if (isDrawSuccess(randomItem, member)) {
                    resultPrints.add(acquireItem(member, randomItem));
                    isDrawSuccess = true;
                    break;
                }
            }
            if (!isDrawSuccess)
                resultPrints.add(FAILED_DRAW_MESSAGE);
        }

        return resultPrints;
    }

    private boolean isItemsCountZero(Map<LuckyBoxItemGrade, List<LuckyBoxItem>> items) {
        return items.size() > 0;
    }

    private boolean isDrawSuccess(LuckyBoxItem randomItem, Member member) {
        if (randomItem instanceof LuckyBoxItemGradeB) {
            if (member.isMaxDrawCountOnItemB()) {
                return false;
            }
            member.drawCountOnItemB();
        }

        return randomItem.drawLuckyBox();
    }

    private Map<LuckyBoxItemGrade, List<LuckyBoxItem>> getNotExpiredItems(LocalDateTime startTime) {
        return luckyBoxItemRepository.findAll()
                .values()
                .stream()
                .flatMap(List::stream)
                .filter(item -> item.getExpirationDate().isAfter(startTime))
                .collect(Collectors.groupingBy(LuckyBoxItem::getGrade));
    }

    private String acquireItem(Member member, LuckyBoxItem randomItem) {
        memberRepository.saveItem(member, randomItem);

        return randomItem.prizePrint();
    }
}
