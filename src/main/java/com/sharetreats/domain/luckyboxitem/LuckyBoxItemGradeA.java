package com.sharetreats.domain.luckyboxitem;

import java.time.LocalDateTime;

import static com.sharetreats.domain.luckyboxitem.LuckyBoxItemGrade.*;

public class LuckyBoxItemGradeA extends LuckyBoxItem {
    public LuckyBoxItemGradeA(String name) {
        super(name);
        this.setGrade(A);
    }

    public LuckyBoxItemGradeA(String name, LocalDateTime expirationDate) {
        super(name);
        this.setGrade(A);
        this.setExpirationDate(expirationDate);
    }
}
