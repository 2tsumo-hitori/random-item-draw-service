package com.sharetreats.repository;

import com.sharetreats.domain.member.Member;
import com.sharetreats.domain.item.Item;

public interface MemberRepository {
    Member saveMember();
    void saveItem(Member member, Item item);
    void resetDrawCount(Member member);
    void spendMoney(Member member);
    void chargeMoney(Member member, int money);
}
