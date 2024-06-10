package com.example.mobilepose.Model.API.Entities;

import com.google.gson.annotations.SerializedName;

public class ResponseBase<ResponseType> {
    @SerializedName("success")
    public boolean success;
    @SerializedName("message")
    public String message;
    @SerializedName("data")
    public ResponseType data;
}
