package com.example.mobilepose.Model.Adapters;

import com.example.mobilepose.Model.Category;

import java.util.List;
public interface CategoriesCallback {
    void onProductsFetched(List<Category> categories);
    void onError(Throwable error);
}
