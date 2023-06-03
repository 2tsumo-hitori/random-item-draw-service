package com.sharetreats.repository;

import com.sharetreats.domain.member.Member;
import com.sharetreats.domain.luckyboxitem.LuckyBoxItem;

public class MemoryMemberRepository implements MemberRepository {
    @Override
    public Member saveMember() {
        return Member.create();
    }

    @Override
    public void saveItem(Member member, LuckyBoxItem luckyBoxItem) {
        member.saveItem(luckyBoxItem);
    }

    @Override
    public void resetDrawCount(Member member) {
        member.resetDrawCountOnItemB();
    }

    @Override
    public void spendMoney(Member member) {
        member.spendMoney();
    }

    @Override
    public void chargeMoney(Member member, int money) {
        member.chargeMoney(money);
    }
}
