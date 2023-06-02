package com.sharetreats.repository;

import com.sharetreats.domain.member.Member;
import com.sharetreats.domain.product.ProductA;
import com.sharetreats.domain.product.ProductB;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DefaultMemberRepositoryTest {

    Member member;
    ProductA productA;
    ProductB productB;
    MemberRepository memberRepository;

    @BeforeEach
    void setUp() {
        memberRepository = new DefaultMemberRepository();
        member = memberRepository.saveMember();
        productA = new ProductA("상품A");
        productB = new ProductB("상품B");
    }

    @Test
    void 회원저장_성공() {
        Member member = memberRepository.saveMember();
        Assertions.assertThat(member).isNotNull();
        Assertions.assertThat(member.getMoney()).isEqualTo(10000);
    }

    @Test
    void 상품저장_성공() {
        memberRepository.saveProduct(member, productA);
        memberRepository.saveProduct(member, productB);
        Assertions.assertThat(member.getProductList()).contains(productA);
        Assertions.assertThat(member.getProductList()).contains(productB);
    }
}