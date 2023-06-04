package com.sharetreats.repository;

import com.sharetreats.domain.member.Member;
import com.sharetreats.domain.item.ItemGradeA;
import com.sharetreats.domain.item.ItemGradeB;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class MemoryMemberRepositoryTest {

    Member member;
    ItemGradeA itemGradeA;
    ItemGradeB itemGradeB;
    MemberRepository memberRepository;

    private static final int INITIAL_MONEY = 10000;
    private static final int CHARGE_MONEY = 100;

    @BeforeEach
    void setUp() {
        memberRepository = new MemoryMemberRepository();

        member = memberRepository.saveMember();

        itemGradeA = new ItemGradeA("상품A");
        itemGradeB = new ItemGradeB("상품B");
    }

    @Test
    void 회원저장_성공() {
        Member member = memberRepository.saveMember();

        assertThat(member).isNotNull();
        assertThat(member.getMoney()).isEqualTo(10000);
    }

    @Test
    void 상품저장_성공() {
        memberRepository.saveItem(member, itemGradeA);
        memberRepository.saveItem(member, itemGradeB);

        assertThat(member.getRandomBoxItems()).contains(itemGradeA);
        assertThat(member.getRandomBoxItems()).contains(itemGradeB);
    }

    @Test
    void 금액충전_성공() {
        memberRepository.chargeMoney(member, CHARGE_MONEY);

        assertThat(member.getMoney()).isEqualTo(INITIAL_MONEY + CHARGE_MONEY);
    }
}