package com.sharetreats.service;

import com.sharetreats.domain.member.Member;

import java.time.LocalDateTime;

public interface PachinkoService {
    void draw(Member member, int count, LocalDateTime now);

    void charge(Member member, int money);
}
