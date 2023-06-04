package com.sharetreats.domain.item;

import java.time.LocalDateTime;

import static com.sharetreats.domain.item.ItemGrade.*;

public class ItemGradeB extends Item {
    public ItemGradeB(String name) {
        super(name);
        this.setGrade(B);
    }

    public ItemGradeB(String name, LocalDateTime expirationDate) {
        super(name);
        this.setGrade(B);
        this.setExpirationDate(expirationDate);
    }
}
