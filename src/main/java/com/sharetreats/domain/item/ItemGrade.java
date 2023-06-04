package com.sharetreats.domain.item;

public enum ItemGrade {
    A(90),
    B(10);

    protected int percentage;
    ItemGrade(int percentage) {
        this.percentage = percentage;
    }
    public int percentage() {
        return percentage;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }
}
