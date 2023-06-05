package com.sharetreats.randomboxdrawservice.support;

import com.sharetreats.domain.member.Member;
import com.sharetreats.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

import static com.sharetreats.randomboxdrawservice.RandomBoxDrawServiceApplication.scanner;

@RequiredArgsConstructor
public class ChargeMoneyAction implements ActionCallback {
    private final MemberRepository memberRepository;
    private final Member member;

    @Override
    public void execute() {
        System.out.println("충전 할 금액을 입력해주세요.");

        int chargeMoney = Integer.parseInt(scanner.next());

        memberRepository.chargeMoney(member, chargeMoney);

        System.out.println("충전 후 현재 금액은 " + member.getMoney() + "원 입니다.");
    }
}
