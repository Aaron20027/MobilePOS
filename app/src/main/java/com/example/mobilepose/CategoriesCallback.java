package com.example.mobilepose;

import com.example.mobilepose.Model.Coupon;

import java.util.List;
public interface CategoriesCallback {
    void onProductsFetched(List<Category> categories);
    void onError(Throwable error);
}
