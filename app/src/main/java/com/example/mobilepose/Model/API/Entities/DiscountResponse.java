package com.example.mobilepose.Model.API.Entities;

import com.google.gson.annotations.SerializedName;

public class DiscountResponse {
    @SerializedName("id")
    public int id;
    @SerializedName("title")
    public String title;
    @SerializedName("description")
    public String description;
    @SerializedName("discount_type")
    public int discount_type;
    @SerializedName("discount_value")
    public float discount_value;
    @SerializedName("discount_avail")
    public int discount_avail;

}
