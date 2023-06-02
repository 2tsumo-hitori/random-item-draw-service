package com.sharetreats.service;

import com.sharetreats.domain.member.Member;
import com.sharetreats.domain.product.Product;
import com.sharetreats.domain.product.ProductB;
import com.sharetreats.domain.product.ProductGrade;
import com.sharetreats.repository.MemberRepository;
import com.sharetreats.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class DefaultPachinkoService implements PachinkoService{

    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;

    @Override
    public void draw(Member member, int count, LocalDateTime startTime) {
        member.setPickCount(0);

        val products = getNotExpiredProducts(startTime);
        System.out.println(products);

        val productsSize = getPresentProductsSize(products);
        System.out.println(productsSize);

        count = coinGachaWithProductLimit(member, count, productsSize);

        for (int i = 0; i < count; i ++) {
            for (ProductGrade grade : ProductGrade.values()) {
                val productList = products.get(grade);

                val randomProduct = productList.get(new Random().nextInt(productList.size()));

                if (isProduct(randomProduct, member)) {
                    getResult(member, randomProduct);
                } else {
                    System.out.println("꽝");
                }
                break;
            }
        }
    }

    private static int getPresentProductsSize(Map<ProductGrade, List<Product>> products) {
        return products.values()
                .stream()
                .flatMap(List::stream)
                .collect(Collectors.toList()).size();
    }

    private int coinGachaWithProductLimit(Member member, int count, int productsSize) {
        if (productsSize < count) {
            member.drawProduct(productsSize);
            count = productsSize;
        } else {
            member.drawProduct(count);
        }
        return count;
    }

    private boolean isProduct(Product randomProduct, Member member) {
        if (randomProduct instanceof ProductB)
            if (member.isMaxPickCount()) {
                return false;
            }
            member.addPickCount();

        return randomProduct.drawingPercent();
    }

    private Map<ProductGrade, List<Product>> getNotExpiredProducts(LocalDateTime startTime) {
        return productRepository.findAll()
                .values()
                .stream()
                .flatMap(List::stream)
                .filter(product -> product.getExpirationDate().isBefore(startTime))
                .collect(Collectors.groupingBy(Product::getGrade));
    }

    private void getResult(Member member, Product randomProduct) {
        memberRepository.saveProduct(member, randomProduct);

        productRepository.delete(randomProduct);

        System.out.println(randomProduct.getName() +
                ", " + randomProduct.getGrade() +
                ", " + randomProduct.getExpirationDate() +
                "을 뽑으셨습니다.");
    }

    @Override
    public void charge(Member member, int money) {
        member.chargeMoney(money);
    }
}
