package com.sharetreats.service;

import com.sharetreats.domain.member.Member;
import com.sharetreats.domain.luckyboxitem.LuckyBoxItem;
import com.sharetreats.domain.luckyboxitem.LuckyBoxItemGradeA;
import com.sharetreats.domain.luckyboxitem.LuckyBoxItemGradeB;
import com.sharetreats.repository.MemoryMemberRepository;
import com.sharetreats.repository.MemoryLuckyBoxItemRepository;
import com.sharetreats.repository.MemberRepository;
import com.sharetreats.repository.LuckyBoxItemRepository;
import lombok.val;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.stream.Collectors;

import static com.sharetreats.domain.luckyboxitem.LuckyBoxItemGrade.A;
import static com.sharetreats.domain.luckyboxitem.LuckyBoxItemGrade.B;
import static org.assertj.core.api.Assertions.*;

class DefaultLuckyBoxServiceTest {

    MemberRepository memberRepository;
    LuckyBoxItemRepository luckyBoxItemRepository;
    LuckyBoxService luckyBoxService;
    Member member;

    private static final int SUCCESS_DRAW_COUNT = 5;
    private static final int MAX_DRAW_COUNT = 100;
    private static final int FAILURE_DRAW_COUNT = 101;
    private static final int INITIAL_MONEY = 10000;
    private static final int DRAW_MONEY = 100;
    private static final int MAX_DRAW_COUNT_ON_ITEM_B = 3;
    private static final int CHARGE_MONEY = 1000;
    private static final int FAILURE_CHARGE_MONEY = -100;

    @BeforeEach
    void setUp() {
        memberRepository = new MemoryMemberRepository();
        luckyBoxItemRepository = new MemoryLuckyBoxItemRepository();
        luckyBoxService = new DefaultLuckyBoxService(memberRepository, luckyBoxItemRepository);

        member = memberRepository.saveMember();

        val luckyBoxItemsGradeA = new ArrayList<LuckyBoxItem>();
        val luckyBoxItemsGradeB = new ArrayList<LuckyBoxItem>();

        for (int i=0; i<10; i++) {
            luckyBoxItemsGradeA.add(new LuckyBoxItemGradeA("A" + i));
        }

        for (int i=0; i<10; i++) {
            luckyBoxItemsGradeB.add(new LuckyBoxItemGradeB("B" + i));
        }

        luckyBoxItemRepository.save(A, luckyBoxItemsGradeA);
        luckyBoxItemRepository.save(B, luckyBoxItemsGradeB);
    }

    @AfterEach
    void after() {
        if (luckyBoxItemRepository instanceof MemoryLuckyBoxItemRepository) {
            MemoryLuckyBoxItemRepository.clear();
        }
    }

    @Test
    void 럭키박스_뽑기_성공() {
        val successOrFailureMessageBox = luckyBoxService.draw(member, SUCCESS_DRAW_COUNT, LocalDateTime.now());

        assertThat(successOrFailureMessageBox.size()).isEqualTo(SUCCESS_DRAW_COUNT);
        assertThat(member.getMoney()).isEqualTo(INITIAL_MONEY - (DRAW_MONEY * SUCCESS_DRAW_COUNT));
    }

    @Test
    void 럭키박스_뽑기_성공__B상품_은_최대_세개까지_가능() {
        MemoryLuckyBoxItemRepository.clear();

        A.setPercentage(0);
        B.setPercentage(100);

        val luckyBoxItemsGradeA = new ArrayList<LuckyBoxItem>();
        val luckyBoxItemsGradeB = new ArrayList<LuckyBoxItem>();

        for (int i=0; i<10; i++) {
            luckyBoxItemsGradeA.add(new LuckyBoxItemGradeA("A" + i));
        }

        for (int i=0; i<10; i++) {
            luckyBoxItemsGradeB.add(new LuckyBoxItemGradeB("B" + i));
        }

        luckyBoxItemRepository.save(A, luckyBoxItemsGradeA);
        luckyBoxItemRepository.save(B, luckyBoxItemsGradeB);

        luckyBoxService.draw(member, MAX_DRAW_COUNT, LocalDateTime.now());

        val itemCount = member.getLuckyBoxItems()
                .stream()
                .filter(luckyBoxItem -> luckyBoxItem.getGrade() == B)
                .collect(Collectors.toList())
                .size();

        assertThat(itemCount).isEqualTo(MAX_DRAW_COUNT_ON_ITEM_B);
    }

    @Test
    void 럭키박스_뽑기_성공__항상_A상품_을_먼저_확인() {
        A.setPercentage(100);
        B.setPercentage(100);

        val luckyBoxItemsGradeA = new ArrayList<LuckyBoxItem>();
        val luckyBoxItemsGradeB = new ArrayList<LuckyBoxItem>();

        for (int i=0; i<10; i++) {
            luckyBoxItemsGradeA.add(new LuckyBoxItemGradeA("A" + i));
        }

        for (int i=0; i<10; i++) {
            luckyBoxItemsGradeB.add(new LuckyBoxItemGradeB("B" + i));
        }

        luckyBoxService.draw(member, MAX_DRAW_COUNT, LocalDateTime.now());

        val itemCount = member.getLuckyBoxItems()
                .stream()
                .filter(luckyBoxItem -> luckyBoxItem.getGrade() == A)
                .collect(Collectors.toList())
                .size();

        assertThat(itemCount).isEqualTo(MAX_DRAW_COUNT);
    }

    @Test
    void 럭키박스_뽑기_실패__소지금액이_게임에_필요한_금액보다_더_적음() {
        assertThat(member.getMoney()).isEqualTo(INITIAL_MONEY);

        assertThatThrownBy(() -> luckyBoxService.draw(member, FAILURE_DRAW_COUNT, LocalDateTime.now()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 럭키박스_뽑기_실패__모든_상품의_유통기한이_지남() {
        MemoryLuckyBoxItemRepository.clear();

        val luckyBoxItemsGradeA = new ArrayList<LuckyBoxItem>();
        val luckyBoxItemsGradeB = new ArrayList<LuckyBoxItem>();

        for (int i=0; i<2; i++) {
            luckyBoxItemsGradeA.add(new LuckyBoxItemGradeA("A" + i, LocalDateTime.now().minusYears(1)));
        }

        for (int i=0; i<2; i++) {
            luckyBoxItemsGradeB.add(new LuckyBoxItemGradeB("B" + i, LocalDateTime.now().minusYears(1)));
        }

        luckyBoxItemRepository.save(A, luckyBoxItemsGradeA);
        luckyBoxItemRepository.save(B, luckyBoxItemsGradeB);

        assertThatThrownBy(() -> luckyBoxService.draw(member, SUCCESS_DRAW_COUNT, LocalDateTime.now()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 금액_충전_성공() {
        memberRepository.chargeMoney(member, CHARGE_MONEY);

        assertThat(member.getMoney()).isEqualTo(INITIAL_MONEY + CHARGE_MONEY);
    }

    @Test
    void 금액_충전_실패__충전할_금액이_0원_미만() {
        assertThatThrownBy(() -> memberRepository.chargeMoney(member, FAILURE_CHARGE_MONEY));
    }
}