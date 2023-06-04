package com.sharetreats.domain.item;

import java.time.LocalDateTime;

import static com.sharetreats.domain.item.ItemGrade.*;

public class ItemGradeA extends Item {
    public ItemGradeA(String name) {
        super(name);
        this.setGrade(A);
    }

    public ItemGradeA(String name, LocalDateTime expirationDate) {
        super(name);
        this.setGrade(A);
        this.setExpirationDate(expirationDate);
    }
}
