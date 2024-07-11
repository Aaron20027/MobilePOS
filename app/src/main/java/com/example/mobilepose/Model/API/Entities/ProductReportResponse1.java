package com.example.mobilepose.Model.API.Entities;

import com.google.gson.annotations.SerializedName;

public class ProductReportResponse1 {

    @SerializedName("categoryName")
    public String categoryName;
    @SerializedName("total_amount_sold")
    public int total_amount_sold;
}
