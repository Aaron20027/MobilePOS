package com.example.mobilepose.Model.Adapters;

import com.example.mobilepose.Model.User;

import java.util.List;

public interface UserCallback {
    void onProductsFetched(List<User> users);
    void onError(Throwable error);
}