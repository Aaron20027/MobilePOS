package com.example.mobilepose.Model.Adapters;

public interface VolleyCallback {
    void onSuccess(String response);
    void onSearchSuccess(String response);
    void onError(String error);
}
