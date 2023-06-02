package com.sharetreats.service;

import com.sharetreats.domain.member.Member;
import com.sharetreats.domain.product.Product;
import com.sharetreats.domain.product.ProductA;
import com.sharetreats.domain.product.ProductB;
import com.sharetreats.repository.DefaultMemberRepository;
import com.sharetreats.repository.DefaultProductRepository;
import com.sharetreats.repository.MemberRepository;
import com.sharetreats.repository.ProductRepository;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.time.LocalDateTime;
import java.util.ArrayList;

import static com.sharetreats.domain.product.ProductGrade.A;
import static com.sharetreats.domain.product.ProductGrade.B;

class DefaultPachinkoServiceTest {

    MemberRepository memberRepository;
    ProductRepository productRepository;
    PachinkoService pachinkoService;
    Member member;

    @BeforeEach
    void setUp() {
        memberRepository = new DefaultMemberRepository();
        productRepository = new DefaultProductRepository();
        pachinkoService = new DefaultPachinkoService(memberRepository, productRepository);

        member = memberRepository.saveMember();

        val productListA = new ArrayList<Product>();
        val productListB = new ArrayList<Product>();

        for (int i=0; i<10; i++) {
            productListA.add(new ProductA("A" + i));
        }

        for (int i=0; i<10; i++) {
            productListB.add(new ProductB("B" + i));
        }

        productRepository.save(A, productListA);
        productRepository.save(B, productListB);
    }

    @Test
    void draw() {
        pachinkoService.draw(member, 20, LocalDateTime.now());

        System.out.println(member.getMoney());
    }

    @Test
    void charge() {
    }
}