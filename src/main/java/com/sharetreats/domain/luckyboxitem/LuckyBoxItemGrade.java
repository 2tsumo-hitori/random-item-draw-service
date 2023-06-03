package com.sharetreats.domain.luckyboxitem;

public enum LuckyBoxItemGrade {
    A(90),
    B(10);

    protected int percentage;
    LuckyBoxItemGrade(int percentage) {
        this.percentage = percentage;
    }
    public int percentage() {
        return percentage;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }
}
