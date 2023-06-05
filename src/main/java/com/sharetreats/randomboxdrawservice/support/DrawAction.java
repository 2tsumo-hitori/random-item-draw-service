package com.sharetreats.randomboxdrawservice.support;

import com.sharetreats.domain.member.Member;
import com.sharetreats.service.ItemDrawService;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

import static com.sharetreats.randomboxdrawservice.RandomBoxDrawServiceApplication.scanner;

@RequiredArgsConstructor
public class DrawAction implements ActionCallback {
    private final ItemDrawService itemDrawService;
    private final Member member;

    @Override
    public void execute() {
        System.out.println("뽑기 횟수를 입력해주세요. 현재 잔액은 " + member.getMoney() + "원 입니다.");

        int count = Integer.parseInt(scanner.next());

        List<String> resultPrints = itemDrawService.draw(member, count, LocalDateTime.now());

        resultPrints.forEach(System.out::println);
    }
}
