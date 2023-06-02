package com.sharetreats.domain.product;

import static com.sharetreats.domain.product.ProductGrade.*;

public class ProductA extends Product{
    public ProductA(String productName) {
        super(productName);
        this.setGrade(A);
    }
}
