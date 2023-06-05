package com.sharetreats.randomboxdrawservice.support;

import com.sharetreats.Config;
import com.sharetreats.domain.member.Member;
import lombok.RequiredArgsConstructor;


import static com.sharetreats.randomboxdrawservice.RandomBoxDrawServiceApplication.scanner;

@RequiredArgsConstructor
public class ChargeMoneyAction implements ActionCallback {
    private final Config config;
    private final Member member;

    @Override
    public void execute() {
        System.out.println("충전 할 금액을 입력해주세요.");

        int chargeMoney = scanner.nextInt();

        config.memberRepository().chargeMoney(member, chargeMoney);

        System.out.println("충전 후 현재 금액은 " + member.getMoney() + "원 입니다.");
    }
}
