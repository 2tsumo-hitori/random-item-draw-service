package com.sharetreats.domain.member;

import com.sharetreats.domain.item.Item;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

import static com.sharetreats.support.PreConditions.validate;
import static java.util.Objects.*;
import static lombok.AccessLevel.*;

@Getter
@AllArgsConstructor(access = PROTECTED)
@NoArgsConstructor(access = PROTECTED)
public class Member {
    @Setter(PROTECTED)
    private int money = INITIAL_MONEY;

    private int drawCountOnItemGradeB = INITIAL_DRAW_COUNT_ON_ITEM_GRADE_B;

    private List<Item> randomBoxItems = new ArrayList<>();

    private static final int MIN_MONEY = 0;

    private static final int INITIAL_MONEY = 10000;

    private static final int DRAW_MONEY = 100;

    private static final int INITIAL_DRAW_COUNT_ON_ITEM_GRADE_B = 0;

    private static final int MAX_DRAW_COUNT_ON_ITEM_GRADE_B = 3;


    public void chargeMoney(int money) {
        validate(money > MIN_MONEY, "값을 정확하게 입력해주세요.");

        this.money += money;
    }

    public void saveItem(Item randomBoxItem) {
        validate(nonNull(randomBoxItem), "상품이 없습니다.");

        this.randomBoxItems.add(randomBoxItem);
    }

    public void spendMoney() {
        validate(this.money >= DRAW_MONEY, "현재 뽑을 수 있을만큼의 금액을 보유하고 있지 않습니다.");

        this.money -= DRAW_MONEY;
    }

    public void resetDrawCountOnItemB() {
        this.drawCountOnItemGradeB = INITIAL_DRAW_COUNT_ON_ITEM_GRADE_B;
    }

    public void draw() {
        this.drawCountOnItemGradeB++;
    }

    public boolean isMaxDrawCountOnItemB() {
        return this.drawCountOnItemGradeB == MAX_DRAW_COUNT_ON_ITEM_GRADE_B;
    }

    public static Member create() {
        return new Member();
    }
}
