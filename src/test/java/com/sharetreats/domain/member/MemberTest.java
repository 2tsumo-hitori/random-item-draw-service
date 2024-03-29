package com.sharetreats.domain.member;

import com.sharetreats.domain.item.Item;
import com.sharetreats.domain.item.ItemGradeA;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;


class MemberTest {

    private Member member;
    private Item item;
    private static int MIN_MONEY = 0;
    private static int DRAW_MONEY = 100;
    private static int INITIAL_MONEY = 10000;
    private static int DRAW_COUNT = 3;
    private static int FAILED_DRAW_COUNT = 1;

    @BeforeEach
    void setUp() {
        member = Member.create();
        item = new ItemGradeA("상품A");
    }

    @Test
    void 금액_충전_성공() {
        member.chargeMoney(DRAW_MONEY);
        assertThat(member.getMoney()).isEqualTo(INITIAL_MONEY + DRAW_MONEY);
    }

    @Test
    void 금액_충전_실패__0원_이하_금액_충전() {
        assertThatThrownBy(() -> member.chargeMoney(MIN_MONEY - 1)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 상품_저장_성공() {
        member.saveItem(item);
        assertThat(member.getRandomBoxItems().contains(item));
    }

    @Test
    void 상품_저장_실패__상품이_널() {
        assertThatThrownBy(() -> member.saveItem(null)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 상품_뽑기_성공() {
        member.spendMoney(DRAW_COUNT);
        assertThat(member.getMoney()).isEqualTo(INITIAL_MONEY - (DRAW_MONEY * DRAW_COUNT));
    }

    @Test
    void 상품_뽑기_실패__횟수가_1_이하() {
        assertThatThrownBy(() -> member.spendMoney(FAILED_DRAW_COUNT)).isInstanceOf(IllegalArgumentException.class);
    }
}