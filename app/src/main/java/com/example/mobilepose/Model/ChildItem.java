package com.example.mobilepose.Model;

import com.example.mobilepose.Model.Coupon;
import com.example.mobilepose.Model.Product;

import java.util.List;

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