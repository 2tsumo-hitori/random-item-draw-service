package com.sharetreats.service.supportservice;

import com.sharetreats.domain.item.Item;
import com.sharetreats.domain.item.ItemGrade;
import com.sharetreats.domain.item.ItemGradeA;
import com.sharetreats.domain.item.ItemGradeB;
import com.sharetreats.domain.member.Member;
import com.sharetreats.repository.ItemRepository;
import com.sharetreats.repository.MemberRepository;
import com.sharetreats.repository.MemoryItemRepository;
import com.sharetreats.repository.MemoryMemberRepository;
import lombok.val;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.sharetreats.domain.item.ItemGrade.*;
import static org.assertj.core.api.Assertions.assertThat;

class RandomItemDrawSupportServiceTest {

    ItemRepository itemRepository;
    MemberRepository memberRepository;
    ItemDrawSupportService itemDrawSupportService;

    Member member;

    private static final int MAX_DRAW_COUNT = 100;
    private static final int MAX_DRAW_COUNT_ON_ITEM_GRADE_B = 3;
    private static final String FAILED_DRAW_MESSAGE = "꽝";
    private static final String RANDOM_BOX_ITEM_GRADE_A = "A";
    private static final String RANDOM_BOX_ITEM_GRADE_B = "B";

    @BeforeEach
    void setUp() {
        memberRepository = new MemoryMemberRepository();
        itemRepository = new MemoryItemRepository();
        itemDrawSupportService = new RandomItemDrawSupportService(memberRepository, itemRepository);

        member = memberRepository.saveMember();

        A.setPercentage(50);
        B.setPercentage(100);

        val luckyBoxItemsGradeA = new ArrayList<Item>();
        val luckyBoxItemsGradeB = new ArrayList<Item>();

        for (int i=0; i<10; i++) {
            luckyBoxItemsGradeA.add(new ItemGradeA("A" + i));
        }

        for (int i=0; i<10; i++) {
            luckyBoxItemsGradeB.add(new ItemGradeB("B" + i));
        }

        itemRepository.save(A, luckyBoxItemsGradeA);
        itemRepository.save(B, luckyBoxItemsGradeB);
    }

    @AfterEach
    void after() {
        if (itemRepository instanceof MemoryItemRepository) {
            MemoryItemRepository.clear();
        }
    }

    @Test
    void 유통기한이_끝난_상품들이_상품목록에_있는지_체크_성공() {
        LocalDateTime startTime = LocalDateTime.now();

        val notExpiredItems = itemDrawSupportService.getNotExpiredItems(startTime);

        val expiredItemsSize = notExpiredItems.values()
                .stream()
                .flatMap(List::stream)
                .filter(luckyBoxItem -> luckyBoxItem.getExpirationDate().isBefore(startTime))
                .collect(Collectors.toList())
                .size();

        assertThat(expiredItemsSize).isZero();
    }

    @Test
    void 항상_A상품_을_먼저_체크_성공() {
        LocalDateTime startTime = LocalDateTime.now();

        val sortedItems = itemDrawSupportService
                .notExpiredItems(startTime)
                .sortItems();

        val firstKey = sortedItems.keySet().iterator().next();

        assertThat(firstKey).isEqualTo(A);
    }

    @Test
    void B상품_이_최대_세개까지만_뽑히는지_체크_성공() {
        for (int i = 0; i < 3; i++) {
            member.draw();
        }
        val 상품B = new ItemGradeB("상품B");

        assertThat(itemDrawSupportService.isDrawSuccess(상품B, member)).isFalse();
    }

    @Test
    void B상품_을_세개_뽑은_이후_A상품_만_뽑는지_체크_성공() {
        var allItems = itemRepository.findAll();

        val 상품_리스트 = new ArrayList<String>();

        for (int i = 0; i < MAX_DRAW_COUNT; i ++) {
            boolean isDrawSuccess = false;
            for (ItemGrade grade : ItemGrade.values()) {
                val items = allItems.get(grade);

                val randomItem = items.get(new Random().nextInt(items.size()));

                if (itemDrawSupportService.isDrawSuccess(randomItem, member)) {
                    상품_리스트.add(randomItem.getGrade().toString());

                    isDrawSuccess = true;

                    break;
                }
            }
            if (!isDrawSuccess)
                상품_리스트.add(FAILED_DRAW_MESSAGE);
        }

        assertThat(상품_리스트
                    .stream()
                    .filter(상품 -> 상품.equals(RANDOM_BOX_ITEM_GRADE_A) || 상품.equals(FAILED_DRAW_MESSAGE))
                    .count())
                .isEqualTo(MAX_DRAW_COUNT - MAX_DRAW_COUNT_ON_ITEM_GRADE_B);

        assertThat(상품_리스트
                    .stream()
                    .filter(상품 -> 상품.equals(RANDOM_BOX_ITEM_GRADE_B))
                    .count())
                .isEqualTo(MAX_DRAW_COUNT_ON_ITEM_GRADE_B);
    }

    @Test
    void 뽑기_성공_후_상품이_회원에게_전달되는지_체크_성공() {
        val 상품A = new ItemGradeA("상품A");

        itemDrawSupportService.acquireItem(member, 상품A);

        assertThat(member.getRandomBoxItems()).contains(상품A);
    }
}