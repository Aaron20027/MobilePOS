package com.example.mobilepose;

import com.example.mobilepose.Model.Product;

public class ChildItem {
    private Product product;

    public ChildItem(Product product)
    {
        this.product = product;
    }

    public String getChildItemTitle()
    {
        return product.getProductName();
    }

    public void setChildItemTitle(String childItemTitle)
    {

    }

    public String getChildItemPrice() {
        return product.getProductPrice();
    }

    public void setChildItemPrice(String childItemPrice) {

    }
}