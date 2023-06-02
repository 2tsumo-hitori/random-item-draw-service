package com.sharetreats.domain.product;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Random;

import static com.sharetreats.support.PreConditions.*;
import static java.util.Objects.*;
import static lombok.AccessLevel.*;

@Getter
public abstract class Product {
    private String name;

    @Setter(PROTECTED)
    private ProductGrade grade;

    private LocalDateTime expirationDate = LocalDateTime.now()
            .plusDays(RANDOM.nextInt(MAX_EXPIRE_MINUTE) - MIN_EXPIRE_MINUTE);

    private static final int MAX_PERCENT = 100;

    private static final Random RANDOM = new Random();

    private static final int MAX_EXPIRE_MINUTE = 21;

    private static final int MIN_EXPIRE_MINUTE = 10;

    protected Product(String name) {
        validate(nonNull(name), "제품명이 비어있습니다.");
        validate(!name.isBlank(), "제품명이 비어있습니다.");

        this.name = name;
    }

    public boolean drawingPercent() {
        return RANDOM.nextInt(MAX_PERCENT) < this.grade.percent();
    }
}
