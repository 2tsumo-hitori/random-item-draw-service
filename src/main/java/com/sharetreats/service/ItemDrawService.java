package com.sharetreats.service;

import com.sharetreats.domain.member.Member;

import java.time.LocalDateTime;
import java.util.List;

public interface ItemDrawService {
    List<String> draw(Member member, int count, LocalDateTime now);
}
