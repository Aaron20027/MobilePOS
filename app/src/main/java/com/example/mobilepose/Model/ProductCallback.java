package com.example.mobilepose.Model;

import android.content.Context;

import java.util.List;

public interface ProductCallback {
    void onProductsFetched(List<Product> products);
    void onError(Throwable error);
}
