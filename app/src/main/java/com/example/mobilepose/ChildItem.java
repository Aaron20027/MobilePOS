package com.example.mobilepose;

import com.example.mobilepose.Model.Product;

public class ChildItem {
    private Product product;

    public ChildItem(Product product)
    {
        this.product = product;
    }

    public Product getProduct() {
        return product;
    }
}