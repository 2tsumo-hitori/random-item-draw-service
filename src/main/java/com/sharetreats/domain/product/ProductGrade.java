package com.sharetreats.domain.product;

public enum ProductGrade {
    A(90),
    B(10);

    private final int percent;
    ProductGrade(int percent) {
        this.percent = percent;
    }
    public int percent() {
        return percent;
    }
}
