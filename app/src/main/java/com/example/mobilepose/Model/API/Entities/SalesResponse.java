package com.example.mobilepose.Model.API.Entities;

import com.google.gson.annotations.SerializedName;

public class SalesResponse {
    @SerializedName("transactionCount")
    public int transactionCount;
    @SerializedName("transactionSales")
    public float transactionSales;
    @SerializedName("avgSales")
    public float avgSales;

}
