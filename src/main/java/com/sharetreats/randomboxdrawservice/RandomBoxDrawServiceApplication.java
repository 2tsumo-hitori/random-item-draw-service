package com.sharetreats.randomboxdrawservice;

import com.sharetreats.domain.member.Member;
import com.sharetreats.randomboxdrawservice.support.ActionCallback;
import com.sharetreats.randomboxdrawservice.support.ChargeMoneyAction;
import com.sharetreats.randomboxdrawservice.support.DrawAction;
import com.sharetreats.repository.MemberRepository;
import com.sharetreats.service.ItemDrawService;
import lombok.RequiredArgsConstructor;

import java.util.Scanner;

import static com.sharetreats.DIContainer.*;
import static com.sharetreats.randomboxdrawservice.support.Execute.programExecution;

@RequiredArgsConstructor
public class RandomBoxDrawServiceApplication {
	public static Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {
		MemberRepository memberRepository = getMemberRepository();
		ItemDrawService itemDrawService = getItemDrawService();

		Member member = memberRepository.saveMember();

		ActionCallback drawAction = new DrawAction(itemDrawService, member);
		ActionCallback chargeMoneyAction = new ChargeMoneyAction(memberRepository, member);

		Boolean start = true;

		while(start) {
			System.out.println("뽑기 1번");
			System.out.println("금액 충전 2번");
			System.out.println("프로그램 종료 3번");

			start = programExecution(drawAction, chargeMoneyAction);
		}
	}
}


