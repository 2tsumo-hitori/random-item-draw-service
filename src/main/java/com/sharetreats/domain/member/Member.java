package com.sharetreats.domain.member;

import com.sharetreats.domain.luckyboxitem.LuckyBoxItem;
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

    private int drawCountOnItemB = INITIAL_DRAW_COUNT_ON_ITEM_B;

    private List<LuckyBoxItem> luckyBoxItems = new ArrayList<>();

    private static final int MIN_MONEY = 0;

    private static final int INITIAL_MONEY = 10000;

    private static final int DRAW_MONEY = 100;

    private static final int INITIAL_DRAW_COUNT_ON_ITEM_B = 0;

    private static final int MAX_DRAW_COUNT_ON_ITEM_B = 3;


    public void chargeMoney(int money) {
        validate(money > MIN_MONEY, "값을 정확하게 입력해주세요.");

        this.money += money;
    }

    public void saveItem(LuckyBoxItem luckyBoxItem) {
        validate(nonNull(luckyBoxItem), "상품이 없습니다.");

        this.luckyBoxItems.add(luckyBoxItem);
    }

    public void spendMoney() {
        validate(this.money >= DRAW_MONEY, "현재 뽑을 수 있을만큼의 금액을 보유하고 있지 않습니다.");

        this.money -= DRAW_MONEY;
    }

    public void resetDrawCountOnItemB() {
        this.drawCountOnItemB = INITIAL_DRAW_COUNT_ON_ITEM_B;
    }

    public void drawCountOnItemB() {
        this.drawCountOnItemB++;
    }

    public boolean isMaxDrawCountOnItemB() {
        return this.drawCountOnItemB == MAX_DRAW_COUNT_ON_ITEM_B;
    }

    public static Member create() {
        return new Member();
    }
}
