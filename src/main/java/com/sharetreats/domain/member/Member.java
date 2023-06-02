package com.sharetreats.domain.member;

import com.sharetreats.domain.product.Product;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

import static com.sharetreats.support.PreConditions.validate;
import static java.util.Objects.*;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
    private int money = INITIAL_MONEY;

    @Setter
    private int pickCount = INITIAL_PICK_COUNT;

    private List<Product> productList = new ArrayList<>();

    private static final int MIN_MONEY = 0;

    private static final int INITIAL_MONEY = 10000;

    private static final int DRAW_MONEY = 100;

    private static final int INITIAL_PICK_COUNT = 0;

    private static int MAX_PICK_COUNT = 3;


    public void chargeMoney(int money) {
        validate(money > MIN_MONEY, "값을 정확하게 입력해주세요.");

        this.money += money;
    }

    public void saveProduct(Product product) {
        validate(nonNull(product), "상품이 없습니다.");

        this.productList.add(product);
    }

    public void drawProduct(int count) {
        validate(this.money > count * DRAW_MONEY, "현재 뽑을 수 있을만큼의 금액을 보유하고 있지 않습니다.");

        this.money -= count * DRAW_MONEY;
    }

    public void addPickCount() {
        this.pickCount++;
    }

    public boolean isMaxPickCount() {
        return this.pickCount == MAX_PICK_COUNT;
    }

    public static Member create() {
        return new Member();
    }
}
