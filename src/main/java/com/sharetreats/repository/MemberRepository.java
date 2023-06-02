package com.sharetreats.repository;

import com.sharetreats.domain.member.Member;
import com.sharetreats.domain.product.Product;

public interface MemberRepository {
    Member saveMember();
    void saveProduct(Member member, Product product);
}
