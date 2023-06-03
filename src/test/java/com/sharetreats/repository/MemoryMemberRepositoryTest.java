package com.sharetreats.repository;

import com.sharetreats.domain.member.Member;
import com.sharetreats.domain.luckyboxitem.LuckyBoxItemGradeA;
import com.sharetreats.domain.luckyboxitem.LuckyBoxItemGradeB;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class MemoryMemberRepositoryTest {

    Member member;
    LuckyBoxItemGradeA luckyBoxItemGradeA;
    LuckyBoxItemGradeB luckyBoxItemGradeB;
    MemberRepository memberRepository;

    @BeforeEach
    void setUp() {
        memberRepository = new MemoryMemberRepository();
        member = memberRepository.saveMember();
        luckyBoxItemGradeA = new LuckyBoxItemGradeA("상품A");
        luckyBoxItemGradeB = new LuckyBoxItemGradeB("상품B");
    }

    @Test
    void 회원저장_성공() {
        Member member = memberRepository.saveMember();
        assertThat(member).isNotNull();
        assertThat(member.getMoney()).isEqualTo(10000);
    }

    @Test
    void 상품저장_성공() {
        memberRepository.saveItem(member, luckyBoxItemGradeA);
        memberRepository.saveItem(member, luckyBoxItemGradeB);
        assertThat(member.getLuckyBoxItems()).contains(luckyBoxItemGradeA);
        assertThat(member.getLuckyBoxItems()).contains(luckyBoxItemGradeB);
    }
}