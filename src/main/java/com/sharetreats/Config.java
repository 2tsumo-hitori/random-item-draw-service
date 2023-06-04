package com.sharetreats;

import com.sharetreats.domain.item.Item;
import com.sharetreats.domain.item.ItemGradeA;
import com.sharetreats.domain.item.ItemGradeB;
import com.sharetreats.repository.MemoryMemberRepository;
import com.sharetreats.repository.MemoryItemRepository;
import com.sharetreats.repository.MemberRepository;
import com.sharetreats.repository.ItemRepository;
import com.sharetreats.service.RandomItemDrawService;
import com.sharetreats.service.supportservice.RandomItemDrawSupportService;
import com.sharetreats.service.ItemDrawService;
import com.sharetreats.service.supportservice.ItemDrawSupportService;
import lombok.val;

import java.util.ArrayList;

import static com.sharetreats.domain.item.ItemGrade.*;

public class Config {
    public MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }

    public ItemRepository itemRepository() {
        return new MemoryItemRepository();
    }

    public ItemDrawSupportService itemDrawSupportService() {
        return new RandomItemDrawSupportService(memberRepository(), itemRepository());
    }

    public ItemDrawService itemDrawService() {
        return new RandomItemDrawService(memberRepository(), itemDrawSupportService());
    }

    private Config() {
        val itemsGradeA = new ArrayList<Item>();
        val itemsGradeB = new ArrayList<Item>();

        itemsGradeA.add(new ItemGradeA("CIDER"));
        itemsGradeA.add(new ItemGradeA("COLA"));
        itemsGradeA.add(new ItemGradeA("JUICE"));
        itemsGradeA.add(new ItemGradeA("CHOCOLATE"));
        itemsGradeA.add(new ItemGradeA("COOKIE"));
        itemsGradeA.add(new ItemGradeA("SNACK"));
        itemsGradeA.add(new ItemGradeA("MILK"));
        itemsGradeA.add(new ItemGradeA("CHEESE"));
        itemsGradeA.add(new ItemGradeA("BREAD"));

        itemsGradeB.add(new ItemGradeB("CHICKEN"));
        itemsGradeB.add(new ItemGradeB("PIZZA"));
        itemsGradeB.add(new ItemGradeB("HAMBURGER"));
        itemsGradeB.add(new ItemGradeB("PASTA"));
        itemsGradeB.add(new ItemGradeB("STEAK"));
        itemsGradeB.add(new ItemGradeB("SUSHI"));
        itemsGradeB.add(new ItemGradeB("BARBEQUE"));
        itemsGradeB.add(new ItemGradeB("CURRY"));
        itemsGradeB.add(new ItemGradeB("RAMEN"));

        itemRepository().save(A, itemsGradeA);
        itemRepository().save(B, itemsGradeB);
    }

    private static Config instance = new Config();

    public static Config getInstance() {
        return instance;
    }
}
