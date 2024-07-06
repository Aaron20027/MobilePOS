package com.example.mobilepose.Model.Callbacks;

import android.content.Context;

import com.example.mobilepose.Model.Product;

import java.util.List;

public interface ProductCallback {
    void onProductsFetched(List<Product> products);
    void onError(Throwable error);
}
