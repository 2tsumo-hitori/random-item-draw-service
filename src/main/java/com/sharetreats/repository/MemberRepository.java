package com.sharetreats.repository;

import com.sharetreats.domain.member.Member;
import com.sharetreats.domain.luckyboxitem.LuckyBoxItem;

public interface MemberRepository {
    Member saveMember();
    void saveItem(Member member, LuckyBoxItem item);
    void resetDrawCount(Member member);
    void spendMoney(Member member);
    void chargeMoney(Member member, int money);
}
