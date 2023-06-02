package com.sharetreats.domain.product;

import static com.sharetreats.domain.product.ProductGrade.*;

public class ProductB extends Product{
    public ProductB(String productName) {
        super(productName);
        this.setGrade(B);
    }
}
