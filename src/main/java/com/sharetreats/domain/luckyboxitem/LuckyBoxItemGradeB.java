package com.sharetreats.domain.luckyboxitem;

import java.time.LocalDateTime;

import static com.sharetreats.domain.luckyboxitem.LuckyBoxItemGrade.*;

public class LuckyBoxItemGradeB extends LuckyBoxItem {
    public LuckyBoxItemGradeB(String name) {
        super(name);
        this.setGrade(B);
    }

    public LuckyBoxItemGradeB(String name, LocalDateTime expirationDate) {
        super(name);
        this.setGrade(B);
        this.setExpirationDate(expirationDate);
    }
}
