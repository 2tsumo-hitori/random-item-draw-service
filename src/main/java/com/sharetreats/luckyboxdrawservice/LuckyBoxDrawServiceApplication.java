package com.sharetreats.luckyboxdrawservice;

import com.sharetreats.Config;
import com.sharetreats.repository.MemberRepository;
import com.sharetreats.service.LuckyBoxService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class LuckyBoxDrawServiceApplication {
	public static void main(String[] args) {
		Config config = Config.getInstance();

		Scanner scanner = new Scanner(System.in);

		MemberRepository memberRepository = config.memberRepository();
		LuckyBoxService luckyBoxService = config.luckyBoxService();

		System.out.println("뽑기를 몇 번 할지 입력해주세요.");

		int count = scanner.nextInt();

		List<String> resultPrints = luckyBoxService.draw(memberRepository.saveMember(), count, LocalDateTime.now());

		resultPrints.forEach(System.out::println);
	}
}
