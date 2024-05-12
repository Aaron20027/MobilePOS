package com.example.mobilepose.Model;

public interface VolleyCallback {
    void onSuccess(String response);
    void onSearchSuccess(String response);
    void onError(String error);
}
