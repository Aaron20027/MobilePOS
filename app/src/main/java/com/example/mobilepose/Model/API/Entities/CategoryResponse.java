package com.example.mobilepose.Model.API.Entities;

import com.google.gson.annotations.SerializedName;

public class CategoryResponse {
    @SerializedName("categoryId")
    public int categoryId;
    @SerializedName("categoryName")
    public String categoryName;

}
