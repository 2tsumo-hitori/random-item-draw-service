package com.sharetreats.service;

import com.sharetreats.domain.member.Member;
import com.sharetreats.domain.item.ItemGrade;
import com.sharetreats.repository.MemberRepository;
import com.sharetreats.service.supportservice.ItemDrawSupportService;
import lombok.RequiredArgsConstructor;
import lombok.val;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


@RequiredArgsConstructor
public class RandomItemDrawService implements ItemDrawService {

    private final MemberRepository memberRepository;

    private final ItemDrawSupportService itemDrawSupportService;

    private static final String FAILED_DRAW_MESSAGE = "꽝";

    @Override
    public List<String> draw(Member member, int count, LocalDateTime startTime) {
        memberRepository.resetDrawCount(member);

        val drawResults = new ArrayList<String>();

        val sortedNotExpiredItems = itemDrawSupportService
                .notExpiredItems(startTime)
                .sortItems();

        for (int i = 0; i < count; i ++) {
            memberRepository.spendMoney(member);

            boolean isDrawSuccess = false;

            for (ItemGrade grade : ItemGrade.values()) {
                val items = sortedNotExpiredItems.get(grade);

                val randomItem = items.get(new Random().nextInt(items.size()));

                if (itemDrawSupportService.isDrawSuccess(randomItem, member)) {
                    drawResults.add(itemDrawSupportService.acquireItem(member, randomItem));

                    isDrawSuccess = true;

                    break;
                }
            }
            if (!isDrawSuccess)
                drawResults.add(FAILED_DRAW_MESSAGE);
        }

        return drawResults;
    }
}
