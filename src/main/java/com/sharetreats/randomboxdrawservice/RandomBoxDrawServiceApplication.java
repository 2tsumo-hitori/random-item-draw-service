package com.sharetreats.randomboxdrawservice;

import com.sharetreats.Config;
import com.sharetreats.domain.member.Member;
import com.sharetreats.randomboxdrawservice.support.ActionCallback;
import com.sharetreats.randomboxdrawservice.support.ChargeMoneyAction;
import com.sharetreats.randomboxdrawservice.support.DrawAction;

import java.util.Scanner;

import static com.sharetreats.randomboxdrawservice.support.Execute.*;

public class RandomBoxDrawServiceApplication {
	public static Scanner scanner = new Scanner(System.in);
	public static void main(String[] args) {
		Config config = Config.getInstance();

		Member member = config.memberRepository().saveMember();

		ActionCallback drawAction = new DrawAction(config, member);
		ActionCallback chargeMoneyAction = new ChargeMoneyAction(config, member);

		while(true) {
			System.out.println("뽑기 1번");
			System.out.println("금액 충전 2번");
			System.out.println("프로그램 종료 3번");

			switch (scanner.nextInt()) {
				case 1 :
					execute(drawAction);
					break;
				case 2:
					execute(chargeMoneyAction);
					break;
				case 3:
					return;
			}
		}
	}
}


