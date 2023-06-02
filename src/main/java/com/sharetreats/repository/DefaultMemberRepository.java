package com.sharetreats.repository;

import com.sharetreats.domain.member.Member;
import com.sharetreats.domain.product.Product;

public class DefaultMemberRepository implements MemberRepository {

    @Override
    public Member saveMember() {
        return Member.create();
    }

    @Override
    public void saveProduct(Member member, Product product) {
        member.saveProduct(product);
    }
}
