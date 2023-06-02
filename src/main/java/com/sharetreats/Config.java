package com.sharetreats;

import com.sharetreats.domain.product.Product;
import com.sharetreats.domain.product.ProductA;
import com.sharetreats.domain.product.ProductB;
import com.sharetreats.repository.DefaultMemberRepository;
import com.sharetreats.repository.DefaultProductRepository;
import com.sharetreats.repository.MemberRepository;
import com.sharetreats.repository.ProductRepository;
import com.sharetreats.service.DefaultPachinkoService;

import java.util.ArrayList;
import java.util.List;

import static com.sharetreats.domain.product.ProductGrade.*;

public class Config {
    public MemberRepository memberRepository() {
        return new DefaultMemberRepository();
    }

    public ProductRepository productRepository() {
        return new DefaultProductRepository();
    }

    public Config() {
        DefaultPachinkoService pachinkoService = new DefaultPachinkoService(memberRepository(), productRepository());

        List<Product> productListA = new ArrayList<>();
        List<Product> productListB = new ArrayList<>();
        for (int i=0; i<10; i++) {
            productListA.add(new ProductA("A" + i));
        }
        for (int i=0; i<10; i++) {
            productListB.add(new ProductB("B" + i));
        }
        productRepository().save(A, productListA);
        productRepository().save(B, productListB);
    }
}
