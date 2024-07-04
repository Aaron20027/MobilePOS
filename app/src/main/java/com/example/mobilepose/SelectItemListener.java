package com.example.mobilepose;

import com.example.mobilepose.ChildItem;
import com.example.mobilepose.Model.Coupon;
import com.example.mobilepose.Model.Product;
import com.example.mobilepose.Model.User;

import java.util.List;

public interface SelectItemListener {

    void onItemClick(Product product, List<Product> products);

}

