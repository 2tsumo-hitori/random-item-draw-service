package com.sharetreats;

import com.sharetreats.domain.luckyboxitem.LuckyBoxItem;
import com.sharetreats.domain.luckyboxitem.LuckyBoxItemGradeA;
import com.sharetreats.domain.luckyboxitem.LuckyBoxItemGradeB;
import com.sharetreats.repository.MemoryMemberRepository;
import com.sharetreats.repository.MemoryLuckyBoxItemRepository;
import com.sharetreats.repository.MemberRepository;
import com.sharetreats.repository.LuckyBoxItemRepository;
import com.sharetreats.service.DefaultLuckyBoxService;
import com.sharetreats.service.LuckyBoxService;
import lombok.val;

import java.util.ArrayList;

import static com.sharetreats.domain.luckyboxitem.LuckyBoxItemGrade.*;

public class Config {
    public MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }

    public LuckyBoxItemRepository luckyBoxItemRepository() {
        return new MemoryLuckyBoxItemRepository();
    }

    public LuckyBoxService luckyBoxService() {
        return new DefaultLuckyBoxService(memberRepository(), luckyBoxItemRepository());
    }

    private Config() {
        val luckyBoxItemsGradeA = new ArrayList<LuckyBoxItem>();
        val luckyBoxItemsGradeB = new ArrayList<LuckyBoxItem>();

        for (int i=0; i<10; i++) {
            luckyBoxItemsGradeA.add(new LuckyBoxItemGradeA("A" + i));
        }
        for (int i=0; i<10; i++) {
            luckyBoxItemsGradeB.add(new LuckyBoxItemGradeB("B" + i));
        }

        luckyBoxItemRepository().save(A, luckyBoxItemsGradeA);
        luckyBoxItemRepository().save(B, luckyBoxItemsGradeB);
    }

    private static Config instance = new Config();

    public static Config getInstance() {
        return instance;
    }
}
