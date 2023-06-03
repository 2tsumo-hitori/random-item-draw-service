package com.sharetreats.service;

import com.sharetreats.domain.member.Member;

import java.time.LocalDateTime;
import java.util.List;

public interface LuckyBoxService {
    void charge(Member member, int money);
    List<String> draw(Member member, int count, LocalDateTime now);

}
