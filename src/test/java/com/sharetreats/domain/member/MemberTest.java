package com.sharetreats.domain.member;

import com.sharetreats.domain.luckyboxitem.LuckyBoxItem;
import com.sharetreats.domain.luckyboxitem.LuckyBoxItemGradeA;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;


class MemberTest {

    private Member member;
    private LuckyBoxItem luckyBoxItem;
    private static int MIN_MONEY = 0;

    @BeforeEach
    void setUp() {
        member = Member.create();
        luckyBoxItem = new LuckyBoxItemGradeA("상품A");
    }

    @Test
    void 금액_충전_성공() {
        member.chargeMoney(100);
        assertThat(member.getMoney()).isEqualTo(10100);
    }

    @Test
    void 금액_충전_실패__0원보다_작은_금액_충전() {
        assertThatThrownBy(() -> member.chargeMoney(MIN_MONEY - 100)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 상품_저장_성공() {
        member.saveItem(luckyBoxItem);
        assertThat(member.getLuckyBoxItems().contains(luckyBoxItem));
    }

    @Test
    void 상품_저장_실패__상품이_널() {
        assertThatThrownBy(() -> member.saveItem(null)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 상품_뽑기_성공() {
        member.spendMoney();
        assertThat(member.getMoney()).isEqualTo(9900);
    }

    @Test
    void 상품_뽑기_실패__금액이_부족() {
        member.setMoney(MIN_MONEY);
        assertThatThrownBy(() -> member.spendMoney()).isInstanceOf(IllegalArgumentException.class);
    }
}