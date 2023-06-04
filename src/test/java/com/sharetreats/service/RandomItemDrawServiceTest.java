package com.sharetreats.service;

import com.sharetreats.domain.member.Member;
import com.sharetreats.domain.item.Item;
import com.sharetreats.domain.item.ItemGradeA;
import com.sharetreats.domain.item.ItemGradeB;
import com.sharetreats.repository.MemoryMemberRepository;
import com.sharetreats.repository.MemoryItemRepository;
import com.sharetreats.repository.MemberRepository;
import com.sharetreats.repository.ItemRepository;
import com.sharetreats.service.supportservice.RandomItemDrawSupportService;
import com.sharetreats.service.supportservice.ItemDrawSupportService;
import lombok.val;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.time.LocalDateTime;
import java.util.ArrayList;

import static com.sharetreats.domain.item.ItemGrade.A;
import static com.sharetreats.domain.item.ItemGrade.B;
import static org.assertj.core.api.Assertions.*;

class RandomItemDrawServiceTest {

    MemberRepository memberRepository;
    ItemRepository itemRepository;
    ItemDrawService itemDrawService;
    ItemDrawSupportService itemDrawSupportService;
    Member member;

    private static final int SUCCESS_DRAW_COUNT = 5;
    private static final int FAILURE_DRAW_COUNT = 101;
    private static final int INITIAL_MONEY = 10000;
    private static final int DRAW_MONEY = 100;

    @BeforeEach
    void setUp() {
        memberRepository = new MemoryMemberRepository();
        itemRepository = new MemoryItemRepository();
        itemDrawSupportService = new RandomItemDrawSupportService(memberRepository, itemRepository);
        itemDrawService = new RandomItemDrawService(memberRepository, itemDrawSupportService);

        member = memberRepository.saveMember();

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
    void 럭키박스_뽑기_성공() {
        val successOrFailureMessageBox = itemDrawService.draw(member, SUCCESS_DRAW_COUNT, LocalDateTime.now());

        assertThat(successOrFailureMessageBox.size()).isEqualTo(SUCCESS_DRAW_COUNT);
        assertThat(member.getMoney()).isEqualTo(INITIAL_MONEY - (DRAW_MONEY * SUCCESS_DRAW_COUNT));
    }

    @Test
    void 럭키박스_뽑기_실패__소지금액이_게임에_필요한_금액보다_더_적음() {
        assertThat(member.getMoney()).isEqualTo(INITIAL_MONEY);

        assertThatThrownBy(() -> itemDrawService.draw(member, FAILURE_DRAW_COUNT, LocalDateTime.now()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 럭키박스_뽑기_실패__모든_상품의_유통기한이_지남() {
        MemoryItemRepository.clear();

        val luckyBoxItemsGradeA = new ArrayList<Item>();
        val luckyBoxItemsGradeB = new ArrayList<Item>();

        for (int i=0; i<2; i++) {
            luckyBoxItemsGradeA.add(new ItemGradeA("A" + i, LocalDateTime.now().minusYears(1)));
        }

        for (int i=0; i<2; i++) {
            luckyBoxItemsGradeB.add(new ItemGradeB("B" + i, LocalDateTime.now().minusYears(1)));
        }

        itemRepository.save(A, luckyBoxItemsGradeA);
        itemRepository.save(B, luckyBoxItemsGradeB);

        assertThatThrownBy(() -> itemDrawService.draw(member, SUCCESS_DRAW_COUNT, LocalDateTime.now()))
                .isInstanceOf(IllegalArgumentException.class);
    }
}